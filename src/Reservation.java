import java.time.*;
import java.util.ArrayList;

public class Reservation {
    private double price;
    private int reserveTicketNum;
    private ArrayList<String> seats;
    private int movieId;
    private LocalDate date;
    private int cinemaNum;
    private LocalTime time;

    public Reservation(int reserveTicketNum, LocalDate date, int cinemaNum, LocalTime time, ArrayList<String> seats,
            double price) {
        this.reserveTicketNum = reserveTicketNum;
        this.date = date;
        this.cinemaNum = cinemaNum;
        this.time = time;
        this.price = price;
        this.seats = seats;

    }

    public Reservation(int reserveTicketNum, LocalDate date, int cinemaNum, LocalTime time, ArrayList<String> seats,
            double price, int movieId) {
        this.reserveTicketNum = reserveTicketNum;
        this.date = date;
        this.cinemaNum = cinemaNum;
        this.time = time;
        this.price = price;
        this.seats = seats;
        this.movieId = movieId;

    }


    // for testing
    public Reservation(int ticketNum) {
        this.reserveTicketNum = ticketNum;
    }

    public double getPrice() {
        return this.price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getReserveTicketNum() {
        return this.reserveTicketNum;
    }

    public void setReserveTicketNum(int reserveTicketNum) {
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

    @Override
    public String toString() {
        return "{" +
                " price='" + getPrice() + "'" +
                ", reserveTicketNum='" + getReserveTicketNum() + "'" +
                ", seats='" + getSeats() + "'" +
                ", movieId='" + getMovieId() + "'" +
                ", date='" + getDate() + "'" +
                ", cinemaNum='" + getCinemaNum() + "'" +
                ", time='" + getTime() + "'" +
                "}";
    }

    public LocalDate getDate() {
        return this.date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCinemaNum() {
        return this.cinemaNum;
    }

    public void setCinemaNum(int cinemaNum) {
        this.cinemaNum = cinemaNum;
    }

    public LocalTime getTime() {
        return this.time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

}
