import java.time.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Iterator;
import java.util.Map;

public class BookingSystem {
    private Scanner sc;
    private HashMap<Integer, Movie> movies;
    private double DISCOUNT = 0.20;
    private ArrayList<Reservation> reservations;
    private FileContentHandler fHandler;

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

        //title screen
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
            // if movie1 is chosen
        break;

        case 2:
            // if movie2 is chosen
        break;

        case 3:
            // if movie3 is chosen
        break;

        case 4:
            // if movie4 is chosen
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

        // set up reservations
        for (Reservation r : reservations) {
            prepareReservationsFromCSV(r);
            System.out.println(r);
        }

        // fHandler.deleteReservation(1234820);

        // for testing
        // movies.get(1).displaySeatAvailability();
        reserveSeat(new ArrayList<String>(Arrays.asList("A1", "H1", "B3")), 1, 0);
        // movies.get(1).displaySeatAvailability();
        System.out.println(movies.get(1).isSeatOccupied("A1"));
        System.out.println(movies.get(1).isSeatOccupied("A2"));

        reserveSeat(new ArrayList<String>(Arrays.asList("A2", "A3")), 1, 0);
        // movies.get(1).displaySeatAvailability();

        reserveSeat(new ArrayList<String>(Arrays.asList("A4", "A1")), 1, 0);
        System.out.println("--------------------------------------");

        movies.get(1).displaySeatAvailability();

        cancelReservation(1234820);

        movies.get(1).displaySeatAvailability();
        cancelReservation(1234829);
        
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
}
