import java.io.FileNotFoundException;
import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.DataFormatException;

public class BookingSystem {
    private Scanner sc;
    private HashMap<Integer, Movie> movies;
    private double DISCOUNT = 0.20;
    private ArrayList<Reservation> reservations;
    private FileContentHandler fHandler;
    private LocalDate DATE_TODAY = LocalDate.now();
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

        // File Reader
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
                        ArrayList<String> seats;

                        do {

                            // System.out.println(movies.get(movieId).getMovieInfo());
                            movie.displaySeatAvailability();
                            String seatChoice = sc.nextLine();
                            seats = checkSeats(seatChoice, movieId);
                        } while (seats.size() == 0);

                        // Prompt for senior/PWD input and calculate totalAmount
                        int seniorCount = 0;
                        if (!movie.isPremiere()) {
                            seniorCount = scInput(seats.size());
                        }

                        // if transaction not cancelled from senior count input
                        if (seniorCount != -999) {
                            // double totalAmount = calculateAmount(seats.size(), movie.isPremiere());
                            checkoutScreen(seniorCount, movieId, seats.size(), seats);

                        }
                    }

                    break;

                case 5:
                    System.out.println("Enter the ticket number: ");
                    int ticketNum = getIntInput();
                    cancelReservation(ticketNum);
                    break;

                // Exit
                case 0:
                    System.out.println("\nThank you come again!");
                    return;

                // Invalid Input
                default:
                    System.out.println("\nINVALID INPUT");
                    System.out.println("ENTERED INPUT MUST BE WITHIN THE CHOICES ONLY\n");

            }

        } while (choice != 0);

    }

    // ArrayList for movies in cinema
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

    // Check if seat is occupied
    public ArrayList<String> checkSeats(String seats, int movieId) {
        ArrayList<String> seatNums = new ArrayList<>();

        // Split the input string by comma and optional whitespace
        String[] seatCodes = seats.toUpperCase().split(",\\s*");

        // Add the individual seat codes to the ArrayList
        seatNums.addAll(Arrays.asList(seatCodes));

        Movie movie = movies.get(movieId);

        boolean errFlag = false;
        // store seat numbers that are already occupied
        ArrayList<String> occupied = new ArrayList<>();
        // store seat numbers that are duplicated in the inputted seats
        Set<String> temp = new HashSet<>();
        ArrayList<String> duplicates = new ArrayList<>();

        // check whether a seat is occupied
        // reserves available seat
        for (String seat : seatNums) {
            try {
                if (movie.isSeatOccupied(seat)) {
                    occupied.add(seat);
                    errFlag = true;
                }

                // duplicate seat code
                if (!temp.add(seat)) {
                    duplicates.add(seat);
                    errFlag = true;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                errFlag = true;

                // return new ArrayList<String>();
            }
        }

        if (!errFlag) {
            return seatNums;
        }

        System.out.println();
        if (occupied.size() > 0) {
            System.out.println("Seats are already reserved: " + occupied);
        }

        if (duplicates.size() > 0) {
            System.out.println("Entered duplicate seats: " + duplicates);
        }

        return new ArrayList<String>();

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
        double price = calculateAmount(seatNums.size(), movie.getIsPremiere());

        Reservation newRes = new Reservation(reserveTicketNum, date, cinemaNum, timeStart, seatNums, price, movieId);
        reservations.add(newRes);

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

    public double calculateAmount(int seatNums, boolean isPremier) {
        double price;

        // Calculate price if premier or not
        if (isPremier) {
            price = seatNums * 500;
        } else {
            // price = (seatNums - senior) * 350 + ((350 - (350 * DISCOUNT)) * senior);
            price = seatNums * 350;

        }
        return price;
    }

    // Used for every Integer Input
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
                System.out.println("[" + (i + 1) + "] " + movies.get(ids.get(i)).getTimeStart()
                        + (movies.get(ids.get(i)).isPremiere() ? " - Premiere Showing" : ""));
            }
            System.out.println("[0] CANCEL TRANSACTION");

            // choose timeslot
            System.out.print("Choose time: ");
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

    // Display Receipt of the transaction
    public void displayReceipt(int seniorCount, double totalAmount, int movieId, int numSeats,
            ArrayList<String> seats, double discountAmount) {

        // Display of Receipt
        System.out.println(
                "*************************************** Cinema World ***************************************");
        System.out.println("*\t Transaction Reference Number:                 " + reserveTicketNum);
        System.out.println("*\t Movie Title:                                  " + movies.get(movieId).getMovieTitle());
        System.out.println("*\t Cinema Number:                                " + movies.get(movieId).getCinemaNum());
        System.out
                .println("*\t Date:                                         " + movies.get(movieId).getShowingDate());
        System.out.println("*\t Time:                                         " + movies.get(movieId).getTimeStart());
        System.out.println("*\t Number of Seat/s:                             " + numSeats);
        System.out.println("*\t Seats Reserved:                               " + seats);
        if (movies.get(movieId).isPremiere() == false && seniorCount > 0) {
            System.out.println("*\t Subtotal:                                     Php " + totalAmount);
            System.out.println("*\t Discount Amount:                              Php " + discountAmount);
        }
        System.out.println("*\t Total Amount:                                 Php " + (totalAmount - discountAmount));
        System.out.println();
        System.out.println();
        System.out.println();

    }

    // inputting process for senior citizens
    public int scInput(int numSeats) {
        int quantity = 0;
        String hasCard;

        do {
            System.out.print("\nDo you have a Senior Citizen or PWD Card? (Yes/No): ");
            hasCard = sc.nextLine().trim();

            if (hasCard.equalsIgnoreCase("yes")) {

                // If they have a card, prompt for the quantity and card ID
                while (true) {
                    System.out.print("\nQuantity of Senior Citizens / PWDs: ");
                    quantity = getIntInput();

                    if (quantity > numSeats || quantity < 0) {
                        System.out.println("\nInvalid input. Please input a number between 0 - " + numSeats + ".");
                    } else {
                        break;
                    }
                }

                for (int i = 0; i < quantity; i++) {
                    System.out.printf("%s (%d):", "\nPlease input Senior Citizen Card / PWD Card ID Number", i + 1);
                    sc.nextLine().trim();
                }
            }
        } while (!hasCard.equalsIgnoreCase("yes") && !hasCard.equalsIgnoreCase("no"));

        int checkoutChoice;
        do {
            // Proceed to checkout for regular customers
            System.out.println("[1] Proceed to Checkout>>> ");
            System.out.println("[2] Cancel Transaction<<<");
            System.out.println("----------------------------------------");
            checkoutChoice = getIntInput();
            switch (checkoutChoice) {
                case 1:
                    break;
                case 2:
                    System.out
                            .println(
                                    "\nYou have aborted your transaction. We look forward to transacting with you soon!");
                    System.out.println();
                    // System.exit(0);
                    return -999;

                default:
                    System.out.println("\nINVALID INPUT");
                    System.out.println("ENTERED INPUT MUST BE WITHIN THE CHOICES ONLY\n");
            }
        } while (checkoutChoice != 1 && checkoutChoice != 2);
        return quantity; // No senior citizens/PWDs

    }

    // Checkout Screen where the transaction is summarized
    public void checkoutScreen(int seniorCount, int movieId, int numSeats,
            ArrayList<String> seats) {

        double totalAmount = calculateAmount(seats.size(), movies.get(movieId).getIsPremiere());
        double discountAmount = (350 * DISCOUNT) * seniorCount;

        // Display checkout options
        int checkoutChoice;

        do {
            System.out
                    .println(
                            "\n*************************************** CHECKOUT ***************************************");
            System.out.println("*\tMovie Title:                       " + movies.get(movieId).getMovieTitle());
            System.out.println("*\tCinema Number:                     " + movies.get(movieId).getCinemaNum());
            System.out
                    .println("*\tDate and Time:                     " + movies.get(movieId).getShowingDate()
                            + movies.get(movieId).getTimeStart());
            System.out.println("*\tNumber of Seats:                   " + numSeats);
            System.out.println("*\tSeats Reserved:                    " + seats);
            if (movies.get(movieId).isPremiere() == false && seniorCount > 0) {
                System.out.println("*\tSubtotal:                          Php " + totalAmount);
                System.out.println("*\tDiscount Amount:                   Php " + discountAmount);
            }

            System.out.println("*\tTotal Amount:                      Php " + (totalAmount - discountAmount));
            System.out.println("\n[1] Confirm and Pay>>> \n[2] Cancel Transaction<<<"); // \n[1] Make Another
                                                                                        // Transaction?

            System.out.println();
            System.out.println("******************************************************************************");
            System.out.println();

            // Prompt for checkout choice
            System.out.print("Enter choice: ");
            checkoutChoice = getIntInput();

            switch (checkoutChoice) {
                case 1:

                    // Once the checkout is confirmed it reserve the seat using reserveSeat()
                    reserveSeat(seats, movieId, seniorCount);

                    // Confirmation of the Transaction
                    System.out.println("\nPayment successful. Thank you for booking with us! Here's your receipt:");
                    System.out.println();

                    // Displays the receipt
                    displayReceipt(seniorCount, totalAmount, movieId, numSeats, seats, discountAmount);

                    break;
                case 2:
                    System.out.println(
                            "\nYou have aborted your transaction. We look forward to transacting with you soon!");
                    System.out.println();
                    break;

                default:
                    System.out.println("\nINVALID INPUT");
                    System.out.println("ENTERED INPUT MUST BE WITHIN THE CHOICES ONLY\n");
            }
        } while (checkoutChoice != 1 && checkoutChoice != 2);
    }

}
