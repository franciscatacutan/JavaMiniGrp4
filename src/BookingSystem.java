import java.io.FileNotFoundException;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.DataFormatException;

public class BookingSystem {
    private Scanner sc;
    private HashMap<Integer, Movie> movies;
    private double DISCOUNT = 0.20;
    private ArrayList<Reservation> reservations;
    private FileContentHandler fHandler;
    private LocalDate DATE_TODAY = LocalDate.of(2021, 6, 1);
    private int reserveTicketNum;

    public static void main(String[] args) {
        BookingSystem bs;
        try {
            bs = new BookingSystem();
            bs.startProgram();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.exit(0);
    }

    // Main method
    public void startProgram() throws FileNotFoundException, DataFormatException {
        fHandler = new FileContentHandler();
        reservations = fHandler.readReservationFile();
        movies = fHandler.readMovieFile();
        sc = new Scanner(System.in);
        int choice = 0;

        // set up reservations
        for (Reservation r : reservations) {
            prepareReservationsFromCSV(r);
        }

        ArrayList<ArrayList<Integer>> showing = getShowing();

        // title screen
        do {
            int i = 0;
            // title screen (screen1)
            System.out.println("***************NOW SHOWING*****************");
            for (i = 0; i < showing.size(); i++) {
                if (showing.get(i).size() > 0) {
                    System.out.printf("* [%d] %s%n", i + 1, movies.get(showing.get(i).get(0)).getMovieTitle());
                } else {
                    System.out.printf("* [%d] %s%n", i + 1, " --- NO MOVIE --- ");

                }
            }

            System.out.println("* [" + 5 + "] Cancel Reservation\n" +
                    "* [" + 0 + "] Exit\n" +
                    "*******************************************");

            // enter movie choice
            System.out.print("Choose Movie: ");
            choice = getIntInput();

            // process after choosing Movie
            switch (choice) {
                case 1:
                case 2:
                case 3:
                case 4:

                    // transition to time slot method
                    if (showing.get(choice - 1).size() == 0) {
                        System.out.println("There's no Movie scheduled for cinema " + choice + "\n");
                        break;
                    }
                    int movieId = selectTimeSlot(showing.get(choice - 1));

                    // if transaction not cancelled from time slot input
                    if (movieId != -999) {
                        // to insert loop here

                        Movie movie = movies.get(movieId);
                        // System.out.println(movies.get(movieId).getMovieInfo());
                        movie.displaySeatAvailability();
                        String seatChoice = sc.nextLine();
                        ArrayList<String> seats = checkSeats(seatChoice, movieId);

                        // seats are not available/ invalid seat code
                        if (seats.size() == 0) {
                            break;
                        }

                        // Prompt for senior/PWD input and calculate totalAmount
                        int sCount = 0;
                        if (!movie.isPremiere()) {
                            sCount = scInput(seats.size());
                        }

                        // if transaction not cancelled from senior count input
                        if (sCount != -999) {
                            double totalAmount = calculateAmount(seats.size(), sCount, movie.isPremiere());
                            reserveSeat(seats, movieId, sCount);
                            processCustomerCheckout(sCount, movie, totalAmount, seats.size(), seats);
                        }
                    }

                    break;

                case 5:
                    System.out.println("Enter the ticket number: ");
                    int ticketNum = getIntInput();
                    cancelReservation(ticketNum);
                    break;

                case 0:
                    System.out.println("\nThank you come again!");
                    return;

                default:
                    System.out.println("\nINVALID INPUT");
                    System.out.println("ENTERED INPUT MUST BE WITHIN THE CHOICES ONLY\n");

            }

        } while (choice != 0);

    }

    public ArrayList<ArrayList<Integer>> getShowing() {
        ArrayList<ArrayList<Integer>> list = new ArrayList<>();
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());
        list.add(new ArrayList<>());

        for (Map.Entry<Integer, Movie> el : movies.entrySet()) {
            Movie m = el.getValue();
            if (m.getShowingDate().equals(DATE_TODAY)) {
                list.get(m.getCinemaNum() - 1).add(el.getKey());
            }
        }

        return list;

    }

    // set the movieId field of Reservation objects retrieved from the csv file
    // also set occupied seats on related Movie object
    public void prepareReservationsFromCSV(Reservation reservation) {
        for (Map.Entry<Integer, Movie> m : movies.entrySet()) {
            if (m.getValue().isMovie(reservation.getDate(), reservation.getTime(), reservation.getCinemaNum())) {
                reservation.setMovieId(m.getKey());
                m.getValue().setSeatsOccupied(reservation.getSeats());
                return;
            }
        }
    }

    public ArrayList<String> checkSeats(String seats, int movieId) {
        ArrayList<String> seatNums = new ArrayList<>();

        // Split the input string by comma and optional whitespace
        String[] seatCodes = seats.toUpperCase().split(",\\s*");

        // Add the individual seat codes to the ArrayList
        seatNums.addAll(Arrays.asList(seatCodes));

        Movie movie = movies.get(movieId);

        // check whether a seat is occupied
        // reserves available seat
        for (String seat : seatNums) {
            try {
                if (movie.isSeatOccupied(seat)) {
                    System.out.println("Seat " + seat + " is already Occupied");
                    return new ArrayList<String>();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ArrayList<String>();
            }
        }

        return seatNums;
    }

    // create a new reservation
    public boolean reserveSeat(ArrayList<String> seatNums, int movieId, int seniorCount) {

        Movie movie = movies.get(movieId);

        movie.setSeatsOccupied(seatNums);

        // new reservation
        reserveTicketNum = (reservations.size() == 0) ? 1234820
                : (reservations.get(reservations.size() - 1).getReserveTicketNum() + 1);
        LocalDate date = movie.getShowingDate();
        int cinemaNum = movie.getCinemaNum();
        LocalTime timeStart = movie.getTimeStart();
        double price = calculateAmount(seatNums.size(), seniorCount, movie.getIsPremiere());

        Reservation newRes = new Reservation(reserveTicketNum, date, cinemaNum, timeStart, seatNums, price, movieId);

        // records reservation in csv
        fHandler.reservationFileWrite_toCSV(newRes);

        return true;
    }

    // cancel a reservation
    public void cancelReservation(int ticketNum) {
        boolean isExist = false;
        // create iterator to check reservations
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();

            // if inputed ticket is found in the reservations it is deleted in the array
            if (reservation.getReserveTicketNum() == ticketNum) {
                isExist = true;
                movies.get(reservation.getMovieId()).setSeatsAvailable(reservation.getSeats());
                fHandler.deleteReservation(reservation.getReserveTicketNum());
                iterator.remove();
            }
        }

        if (!isExist) {
            System.out.println("Ticket number " + ticketNum + " does not exist.");
        }
        // update Reservations.csv
    }

    public double calculateAmount(int seatNums, int senior, boolean isPremier) {
        double price;

        // Calculate price if premier or not
        if (isPremier) {
            price = seatNums * 500;
            System.out.println(price);

        } else {
            price = (seatNums - senior) * 350 + ((350 - (350 * DISCOUNT)) * senior);
            System.out.println(price);

        }

        return price;
    }

    public int getIntInput() {
        int num;

        // loops if the inputted value is an integer
        while (true) {
            try {
                num = Integer.parseInt(sc.nextLine());
                break; // Exit the loop if parsing is successful
            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
                System.out.print("Enter again: " + "\n");
            }
        }
        return num;
    }

    // 2nd screen for choosing time slots
    public int selectTimeSlot(ArrayList<Integer> ids) {
        Movie mInfo = movies.get(ids.get(0));
        int time_slot;

        while (true) {
            // display movie title
            System.out.println("\t\n*************** " + mInfo.getMovieTitle() + " ***************\n");

            // display cinema number
            System.out.println("Cinema Number: " + mInfo.getCinemaNum());

            // display timeslots
            System.out.println();
            for (int i = 0; i < ids.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + movies.get(ids.get(i)).getTimeStart());
            }
            System.out.println("[0] CANCEL TRANSACTION");

            // choose timeslot
            System.out.print("\nChoose time: ");
            time_slot = getIntInput();

            if (time_slot > ids.size() || time_slot < 0) {
                System.out.println("Invalid Input. Please try again.");
            } else {
                break;
            }
        }

        if (time_slot != 0) {
            return ids.get(time_slot - 1);
        }
        return -999;
    }

    // Display the Summary for Regular Tickets - Screen 5A - to remove. start
    /* public void displaySummaryRegular(int movieId, int numSeats, double calculateAmount, double totalAmount) {
        System.out.println("***************SUMMARY*****************");
        System.out.println("*\t Movie Title:                         *" + movies.get(movieId).getMovieTitle()); // insert
                                                                                                                // syntax
                                                                                                                // to
                                                                                                                // display
                                                                                                                // movie
                                                                                                                // title
        System.out.println("*\t Cinema Number:                       *" + movies.get(movieId).getCinemaNum()); // insert
                                                                                                               // cinema
                                                                                                               // number
        System.out.println("*\t Date:                                *" + movies.get(movieId).getShowingDate()); // insert
                                                                                                                 // date
        System.out.println("*\t Time of Screening:                   *"); // insert time
        System.out.println("*\t Number of Seat/s:                   *" + numSeats); // insert number of tickets
        System.out.println("*\t Seats Reserved:                      *" + numSeats); // insert what seats have been
                                                                                     // reserved
        System.out.println("*\t Subtotal                             *"); // insert subtotal: The Subtotal is the
                                                                          // initial total without the discount
        System.out.println("*\t Discount Total:                      *" + calculateAmount); // insert discount total:
                                                                                            // The discountTotal is
        // the amount deducted from the tickets as a
        // discount
        System.out.println("*\t Total Amount:                        *" + totalAmount); // insert insert total amount:
                                                                                        // The TotalAmount
        // is the final price (subtotal -
        // discountTotal)
        System.out.println("\n*\t [1] Confirm \t [2] Back \t [3] Cancel   \t*"); // insert scanner and syntax to receive
                                                                                 // next instruction which is to select
                                                                                  */// which screen to go next
        // if choice is:
        // 1 == proceed to Screen6A: the screen for Receipt of Regular Tickets
        // 2 == return to previous page Screen3: the screen for reserving seats
        // 3 == reset / return to main page / welcome page
    //}
    // Display the Summary for Regular Tickets - Screen 5A - to remove. end

    /*public void displaySummaryPremier(int movieId, int numSeats, double totalAmount) {
        System.out.println("****************SUMMARY**************");
        System.out.println("\\t Movie Title:                         *" + movies.get(movieId).getMovieTitle()); // insert
                                                                                                                // syntax
                                                                                                                // to
                                                                                                                // display
                                                                                                                // movie
                                                                                                                // title
        System.out.println("\\t Cinema Number:                       *" + movies.get(movieId).getCinemaNum()); // insert
                                                                                                               // cinema
                                                                                                               // number
        System.out.println("\\t Date:                                *" + movies.get(movieId).getShowingDate()); // insert
                                                                                                                 // date
        System.out.println("\\t Time:                                *" + movies.get(movieId).getTimeStart()); // insert
                                                                                                               // time
        System.out.println("\\t Number of Seat/s:                  *" + numSeats); // insert number of tickets
        System.out.println("\\t Seats Reserved:                      *" + numSeats); // insert what seats have been
                                                                                     // reserved
        System.out.println("\\t Total Amount:                        *" + totalAmount); // insert insert total amount:
        System.out.println("\n*\\t [1] Confirm \t [2] Back \t [3] Cancel   \t*"); // insert scanner and syntax to
                                                                                  // receive next instruction
        // if choice is:
        // 1 == proceed to Screen6A: the screen for Receipt of Regular Tickets
        // 2 == return to previous page Screen3: the screen for reserving seats
        // 3 == reset / return to main page / welcome page
    }*/

    //
    public void displayReceipt(int movieId, String hasCard, String calculateAmount, int numSeats, double totalAmount) {
        System.out.println("***************Cinema World***************");
        System.out.println("*\t Transaction Reference Number:                 *" + reserveTicketNum);
        System.out.println("*\t Movie Title:                                  *" + movies.get(movieId).getMovieTitle());
        System.out.println("*\t Cinema Number:                                *" + movies.get(movieId).getCinemaNum());
        System.out
                .println("*\t Date:                                         *" + movies.get(movieId).getShowingDate());
        System.out.println("*\t Time:                                         *" + movies.get(movieId).getTimeStart());
        System.out.println("*\t Number of Seat/s:                           *" + numSeats);
        if (hasCard.equals("Yes")) {
            System.out.println("*\t Discounted Amount:                     *" + calculateAmount);
            // System.out.println("*\t Subtotal: *"+);
        }
        System.out.println("*\t Seats Reserved:                               *" + numSeats);
        System.out.println("*\t Total Amount:                                 *" + totalAmount);
        System.out.println("\n\n [1] Make Another Transaction?");
        System.out.println("[2] Exit");
        // insert scanner to scan value for whether customer wants to make another
        // transaction or to exit the whole transaction.
    }

    // inputting process for senior citizens
    public int scInput(int numSeats) {
        System.out.print("Do you have a Senior Citizen or PWD Card? (Yes/No): ");
        String hasCard = sc.nextLine().trim();
        int quantity = 0;

        if (hasCard.equalsIgnoreCase("yes")) {
            // If they have a card, prompt for the quantity and card ID

            while (true) {
                System.out.print("Quantity of Senior Citizens / PWDs: ");
                quantity = getIntInput();

                if (quantity > numSeats || quantity < 0) {
                    System.out.println("Invalid input. Please input a number between 0 - " + numSeats + ".");
                } else {
                    break;
                }
            }

            for (int i = 0; i < quantity; i++) {
                System.out.printf("%s (%d):", "Please input Senior Citizen Card / PWD Card ID Number", i + 1);
                sc.nextLine().trim();
            }

            // Proceed to checkout for senior citizens/PWDs
            System.out.println("[1] Proceed to Checkout>>> ");
            System.out.println("[2] Cancel Transaction<<<");
            System.out.println("----------------------------------------");
            int checkoutChoice = getIntInput();
            if (checkoutChoice == 1) {
                return quantity;
                // Implement checkout logic for senior citizens/PWDs here
                // You have all the necessary information to complete the reservation
            } else {
                System.out
                        .println("\nYou have aborted your transaction. We look forward to transacting with you soon!");
                System.out.println();
                System.exit(0);
                return -999;
            }
        } else {
            // Proceed to checkout for regular customers
            System.out.println("[1] Proceed to Checkout>>> ");
            System.out.println("[2] Cancel Transaction<<<");
            System.out.println("----------------------------------------");
            int checkoutChoice = getIntInput();
            if (checkoutChoice == 1) {
                return 0; // No senior citizens/PWDs
                // Implement checkout logic for regular customers here
                // You have all the necessary information to complete the reservation
            } else {
                System.out
                        .println("\nYou have aborted your transaction. We look forward to transacting with you soon!");
                System.out.println();
                System.exit(0);
                return -999;
            }
        }
    }

    // Final method for Summary
    public void checkoutScreen(int seniorCount, double totalAmount, Movie movie, int numSeats,
            ArrayList<String> seats) {
        // Display checkout options
        
        System.out.println("\n***************CHECKOUT***************");
        System.out.println("Movie Title: " + movie.getMovieTitle());
        System.out.println("Cinema Number: " + movie.getCinemaNum());
        System.out.println("Date and Time: " + movie.getShowingDate() + movie.getTimeStart());
        System.out.println("Number of Seats: " + numSeats);
        System.out.println("Seats Reserved: " + seats);
        if (movie.isPremiere() == false) {
            System.out.println("Subtotal: " + totalAmount);
            System.out.println("Discount Amount: ");
        }
        System.out.println("Total Amount: Php" + totalAmount);
        System.out.println("\n[1] Confirm and Pay>>> \t[2] Cancel Transaction<<<");
        System.out.println("***************************************");
        System.out.println();

        // Prompt for checkout choice
        System.out.print("Enter choice: ");
        int checkoutChoice = getIntInput();

        switch (checkoutChoice) {
            case 1:
                // Implement receipt here
                // You can add payment processing, receipt generation, etc.
                System.out.println("Payment successful! Thank you for booking with us!"); // temporary. insert code for
                                                                                          // receipt
                System.out.println();
                System.out.println();

                // System.exit(0);
                break;
            case 2:
                System.out
                        .println("\nYou have aborted your transaction. We look forward to transacting with you soon!");
                System.out.println();
                // System.exit(0);
                break;
        }
    }

    public void processCustomerCheckout(int seniorCount, Movie movie, double totalAmount, int numSeats,
            ArrayList<String> seats) {
        // Display checkout screen
        checkoutScreen(seniorCount, totalAmount, movie, numSeats, seats);

        // You can add more logic here, such as updating reservation records, etc.
    }

}
