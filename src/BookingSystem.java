import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

public class BookingSystem {
    private Scanner sc;
    private HashMap<Integer, Movie> movies;
    private double DISCOUNT = 0.20;
    private ArrayList<Reservation> reservations;
    private FileContentHandler fHandler;
    private LocalDate DATE_TODAY = LocalDate.of(2021, 6, 1);

    public static void main(String[] args) {
        BookingSystem bs;
        try {
            bs = new BookingSystem();
            bs.startProgram();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method
    public void startProgram() {
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
            // title screen (screen1)
            System.out.println("***************NOW SHOWING*****************");
            System.out.println("*\t [1] Shrek                        *");
            System.out.println("*\t [2] Kim Possible The Movie       *");
            System.out.println("*\t [3] Fantastic 4                  *");
            System.out.println("*\t [4] A Man Called Otto            *");
            System.out.println("*\t [5] Cancel Reservation           *");
            System.out.println("*\t [0] Exit                         *");
            System.out.println("*******************************************");

            // enter movie choice
            System.out.print("Choose Movie: ");
            choice = getIntInput();

            // process after choosing Movie
            switch (choice) {
                case 1:
                    // transition to time slot method
                    selectTimeSlot();
                    movies.get(1).displaySeatAvailability();
                    break;

                case 2:
                    // transition to time slot method
                    selectTimeSlot();
                    movies.get(2).displaySeatAvailability();
                    break;

                case 3:
                    // transition to time slot method
                    selectTimeSlot();
                    movies.get(3).displaySeatAvailability();
                    break;

                case 4:
                    // transition to time slot method
                    selectTimeSlot();
                    movies.get(4).displaySeatAvailability();
                    break;

                case 5:
                    // when cancelling reservation
                    break;

                case 0:
                    System.out.println("\nThank you come again!");
                    System.exit(0);
                    break;

                default:
                    System.out.println("\nINVALID INPUT");
                    System.out.println("ENTERED INPUT MUST BE WITHIN THE CHOICES ONLY\n");

            }

        } while (choice == 0);

        // for (int i = 0; i < showing.size(); i++) {
        // System.out.println("cinmea " + i);
        // for (int ids : showing.get(i)) {
        // System.out.println(ids);
        // }
        // }
        // fHandler.deleteReservation(1234820);

        // for testing
        // movies.get(1).displaySeatAvailability();
        reserveSeat(new ArrayList<String>(Arrays.asList("A1", "H1", "B3")), 1, 0);
        // movies.get(1).displaySeatAvailability();
        System.out.println(movies.get(1).isSeatOccupied("A1"));
        System.out.println(movies.get(1).isSeatOccupied("A2"));

        reserveSeat(new ArrayList<String>(Arrays.asList("A2", "A3")), 1, 0);
        // movies.get(1).displaySeatAvailability();
        // cancelReservation(1234829);

    }

    // public int[] getShowing() {
    // int[] showing = new int[4];
    // int cinemaNum = 1;

    // while (cinemaNum < 5) {
    // for (Map.Entry<Integer, Movie> el : movies.entrySet()) {
    // Movie m = el.getValue();
    // if (m.getShowingDate().equals(DATE_TODAY) && m.getCinemaNum() == cinemaNum) {
    // showing[cinemaNum - 1] = el.getKey();
    // cinemaNum++;
    // break;
    // }
    // }
    // }

    // return showing;
    // }

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

        /*
         * reserveSeat(new ArrayList<String>(Arrays.asList("A4", "A1")), 1, 0);
         * System.out.println("--------------------------------------");
         * 
         * movies.get(1).displaySeatAvailability();
         * 
         * cancelReservation(1234820);
         * 
         * movies.get(1).displaySeatAvailability();
         * cancelReservation(1234829);
         */

        // cancelReservation(1234829);

    }

    // set the movieId field of Reservation objects retrieved from the csv file
    // also set occupied seats on related Movie object
    public void prepareReservationsFromCSV(Reservation reservation) {
        for (Map.Entry<Integer, Movie> m : movies.entrySet()) {
            if (m.getValue().isMovie(reservation.getDate(), reservation.getTime(), reservation.getCinemaNum())) {
                reservation.setMovieId(m.getKey());
                m.getValue().setSeatOccupied(reservation.getSeats());
                return;
            }
        }
    }

    // create a new reservation
    public boolean reserveSeat(ArrayList<String> seatNums, int movieId, int seniorCount) {
        Movie movie = movies.get(movieId);

        // check whether a seat is occupied
        // reserves available seat
        for (String seat : seatNums) {
            if (movie.isSeatOccupied(seat)) {
                System.out.println("Seat " + seat + " is already Occupied");
                return false;
            }
        }
        movie.setSeatOccupied(seatNums);

        // new reservation
        int reserveTicketNum = (reservations.size() == 0) ? 1234820
                : (reservations.get(reservations.size() - 1).getReserveTicketNum() + 1);
        LocalDate date = movie.getShowingDate();
        int cinemaNum = movie.getCinemaNum();
        LocalTime timeStart = movie.getTimeStart();
        double price = calculateAmount(seatNums.size(), seniorCount, movie.getIsPremiere());

        Reservation newRes = new Reservation(reserveTicketNum, date, cinemaNum, timeStart, seatNums, price, movieId);
        System.out.println(newRes);

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
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }

    // 2nd screen for choosing time slots
    public void selectTimeSlot() {
        int time_slot;

        // display movie title
        System.out.println("\t\n*************** <INSERT MOVIE TITLE HERE>"
                /* insert movie title object here */ + " ***************\n");

        // display cinema number
        System.out.println("Cinema Number: <cinema_num>");

        // display timeslots
        System.out.println();
        for (int i = 0; i < 5; i++) {
            System.out.println("{Display time slot here}");
        }

        // choose timeslot
        System.out.print("\nChoose time: ");
        time_slot = getIntInput();

        System.out.println();
    }

    // Display the Summary for Regular Tickets - Screen 5A
    public void displaySummaryRegular() {
        System.out.println("***************SUMMARY*****************");
        System.out.println("*\\t Movie Title:                         *"); // insert syntax to display movie title
        System.out.println("*\\t Cinema Number:                       *"); // insert cinema number
        System.out.println("*\\t Date:                                *"); // insert date
        System.out.println("*\\t Time of Screening:                   *"); // insert time
        System.out.println("*\\t Number of Tickets:                   *"); // insert number of tickets
        System.out.println("*\\t Seats Reserved:                      *"); // insert what seats have been reserved
        System.out.println("*\\t Subtotal                             *"); // insert subtotal: The Subtotal is the
                                                                           // initial total without the discount
        System.out.println("*\\t Discount Total:                      *"); // insert discount total: The discountTotal
                                                                           // is the amount deducted from the tickets as
                                                                           // a discount
        System.out.println("*\\t Total Amount:                        *"); // insert insert total amount: The
                                                                           // TotalAmount is the final price (subtotal -
                                                                           // discountTotal)
        System.out.println("\n*\\t [1] Confirm \t [2] Back \t [3] Cancel   \t*"); // insert scanner and syntax to
                                                                                  // receive next instruction
        // if choice is:
        // 1 == proceed to Screen6A: the screen for Receipt of Regular Tickets
        // 2 == return to previous page Screen3: the screen for reserving seats
        // 3 == reset / return to main page / welcome page
    }

    public void displaySummaryPremier() {
        System.out.println("****************SUMMARY**************");
        System.out.println("Movie Title: " + movies.get(1));
        System.out.println("Cinema Number: ");
        System.out.println("Date: ");
        System.out.println("Time: ");
        System.out.println("Number of Ticket/s: ");
        System.out.println("Seats Reserved: ");
        System.out.println("Total Amount: ");
    }

    public void displayRegularReceipt() {
        System.out.println("/t/t/t/t/t/t/tCinema World/t/t/t/t/t");
        System.out.println("Transaction Reference Number: ");
        System.out.println("Movie Title: ");
        System.out.println("Cinema Number: ");
        System.out.println("Date: ");
        System.out.println("Time: ");
        System.out.println("Number of Ticket/s: ");
        System.out.println("Number of Discounted Tickets: ");
        System.out.println("Seats Reserved: ");
        System.out.println("Subtotal: ");
        System.out.println("Discounted Amount: ");
        System.out.println("Total Amount: ");
        System.out.println("********END*********");

    }

    public void displayPremierReceipt() {
        System.out.println("/t/t/t/t/t/t/tCinema World/t/t/t/t/t");
        System.out.println("Transaction Reference Number: ");
        System.out.println("Movie Title: ");
        System.out.println("Cinema Number: ");
        System.out.println("Date: ");
        System.out.println("Time: ");
        System.out.println("Number of Ticket/s: ");
        System.out.println("Seats Reserved: ");
        System.out.println("Total Amount: ");
        System.out.println("********END*********");

    }

}
