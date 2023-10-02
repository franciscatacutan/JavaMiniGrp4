import java.util.ArrayList;

public class Reservation {
    private double price;
    private long reserTicketNum;
    private ArrayList<String> seats;
    private int movieId;



    public Reservation(double price, long reserTicketNum, ArrayList<String> seats, int movieId) {
        this.price = price;
        this.reserTicketNum = reserTicketNum;
        this.seats = seats;
        this.movieId = movieId;
    }

    // for testing
    public Reservation(long ticketNum) {
        this.reserTicketNum = ticketNum;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getReserTicketNum() {
        return this.reserTicketNum;
    }

    public void setReserTicketNum(long reserTicketNum) {
        this.reserTicketNum = reserTicketNum;
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


    
}
