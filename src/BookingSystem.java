import java.util.ArrayList;
import java.util.Scanner;

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
        
        // Test price
        System.out.println("Seat nums: ");
        int sNums = getIntInput();
        System.out.println("Num of Seniors:");
        int sen = getIntInput();
        System.out.println("Is premier y/n: ");
        String isPrem = sc.nextLine();
        System.out.println(calculateAmount(sNums, sen, isPrem.equals("y") ? true : false));      
        
    }

    public boolean reserveSeat(String seatNum, Movie movie) {
        // call is seat available method

        // get new ticket number
        long tNum = reservations.get(reservations.size()-1).getReserTicketNum()+1;
        // test reservation add
        reservations.add(new Reservation(calculateAmount(0, 0, false), tNum, new ArrayList<String>(), movie.getId()));

        // write to reservation.csv

        return true;
    }

    public void cancelReservation(){}

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
