import java.util.ArrayList;

public class Reservation {
    private double price;
    private long reserveTicketNum;
    private ArrayList<String> seats;
    private int movieId;



    public Reservation(double price, long reserveTicketNum, ArrayList<String> seats, int movieId) {
        this.price = price;
        this.reserveTicketNum = reserveTicketNum;
        this.seats = seats;
        this.movieId = movieId;
    }

    // for testing
    public Reservation(long ticketNum) {
        this.reserveTicketNum = ticketNum;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getReserveTicketNum() {
        return this.reserveTicketNum;
    }

    public void setReserveTicketNum(long reserveTicketNum) {
        this.reserveTicketNum = reserveTicketNum;
    }

    public ArrayList<String> getSeats() {
        return this.seats;
    }

    public void setSeats(ArrayList<String> seats) {
        this.seats = seats;
    }
    

    public int getMovieId() {
        return this.movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    
    public String toString() {
        return reserveTicketNum + "";
    }   
}
