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
        movies = new ArrayList<>();
        reservations = new ArrayList<>();
        sc = new Scanner(System.in);

        //title screen
        System.out.println("***************NOW SHOWING*****************");
        System.out.println("*\t  [1] Shrek                       *");
        System.out.println("*\t  [2] Kim Possible The Movie      *");
        System.out.println("*\t  [3] Fantastic 4                 *");
        System.out.println("*\t  [4] A Man Called Otto           *");
        System.out.println("*******************************************");

        //enter movie choice
        System.out.print("Choose Movie: ");
        getIntInput();

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
        reservations.add(new Reservation(calculateAmount(0, 0, false), tNum, new ArrayList<String>(), movie.getId()));

        // write to reservation.csv

        return true;
    }

    public void cancelReservation(long ticketNum) {
        reservations.add(new Reservation(1));
        reservations.add(new Reservation(2));
        reservations.add(new Reservation(3));
        reservations.add(new Reservation(4));
        reservations.add(new Reservation(5));

        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation reservation = iterator.next();
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
