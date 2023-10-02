import java.util.ArrayList;

public class BookingSystem {
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


    }

    public String reserveSeat(String seatNum, Movie movie) {



        return "reserve";
    }

    public void cancelReservation(){}

    public double calculateAmount(int seatNums, int senior) {
        double price = 0;
        return price;
    }

    public void seatAvailable(Movie movie) {
    }
}
