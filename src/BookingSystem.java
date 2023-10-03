import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

public class BookingSystem {
    private Scanner sc;
    private ArrayList<Movie> movies;
    private double DISCOUNT = 0.20;
    private ArrayList<Reservation> reservations;

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
        FileContentHandler fHandler = new FileContentHandler();
        reservations = fHandler.readReservationFile();
        sc = new Scanner(System.in);
        int choice = 0;

        // read movies.csv
        // movies = fHandler.readMovieFile();


        do {
            // title screen
            System.out.println("***************NOW SHOWING*****************");
            System.out.println("*\t  [1] Shrek                       *");
            System.out.println("*\t  [2] Kim Possible The Movie      *");
            System.out.println("*\t  [3] Fantastic 4                 *");
            System.out.println("*\t  [4] A Man Called Otto           *");
            System.out.println("*\t  [5] Cancel Reservation          *");
            System.out.println("*\t  [0] Exit                        *");
            System.out.println("*******************************************");

            // enter movie choice
            System.out.print("Choose Movie: ");
            choice = getIntInput();

            // process after choosing Movie
            switch (choice) {
                case 1:

                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:

                    break;

                case 0:
                    System.exit(0);
                    break;

                default:
                    System.out.println("\nINVALID INPUT");
                    System.out.println("ENTERED INPUT MUST BE WITHIN THE CHOICES ONLY\n");

            }

        } while (choice != 0);

        // test cancel reservation
        reservations.add(new Reservation(1231231));
        System.out.println("Enter ticket num");
        long t = sc.nextLong();
        sc.nextLine();
        cancelReservation(t);

        System.out.println(reservations.isEmpty());

    }

    public boolean reserveSeat(String seatNum, Movie movie) {

        // call is seat available method

        // get new ticket number
        long tNum = reservations.get(reservations.size() - 1).getReserveTicketNum() + 1;
        // test reservation add

        // write to reservation.csv

        return true;
    }

    public void cancelReservation(long ticketNum) {
        reservations.add(new Reservation(1));
        reservations.add(new Reservation(2));
        reservations.add(new Reservation(3));
        reservations.add(new Reservation(4));
        reservations.add(new Reservation(5));

        // create iterator to check reservations
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();

            // if inputed ticket is found in the reservations it is deleted in the array
            if (reservation.getReserveTicketNum() == ticketNum) {
                iterator.remove();
            }
        }

        System.out.println(reservations);
    }

    public double calculateAmount(int seatNums, int senior, boolean isPremier) {
        double price;

        // Calculate price if premier or not
        if (isPremier) {
            price = seatNums * 500;

        } else {
            price = (seatNums - senior) * 350 + ((350 - (350 * DISCOUNT)) * senior);
        }

        return price;
    }

    public void seatAvailable(Movie movie) {
    }

    public int getIntInput() {
        int num = sc.nextInt();
        sc.nextLine();
        return num;
    }
}
