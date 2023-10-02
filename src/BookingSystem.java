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



        


        // test cancel reservation
        reservations.add(new Reservation(1231231));
        System.out.println("Enter ticket num");
        long t = sc.nextLong();
        sc.nextLine();
        cancelReservation(t);

        System.out.println(reservations.isEmpty());



    }

    public String reserveSeat(String seatNum, Movie movie) {



        return "reserve";
    }

    public void cancelReservation(long ticketNum) {
        Iterator<Reservation> iterator = reservations.iterator();
        while (iterator.hasNext()) {
            Reservation element = iterator.next();
            if (element.equals(ticketNum)) {
                iterator.remove();
            }
        }

    }

    public double calculateAmount(int seatNums, int senior, boolean isPremier) {
        double price;
        // double premierPrice;

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
