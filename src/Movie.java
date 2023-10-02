import java.util.ArrayList;

public class Movie {
    
    private String showingDate;
    private int cinemaNum;
    private ArrayList<String> showtimes;
    private boolean isPremiere;
    private String movieTitle;
    private double movieLength;
    private String[][] seats;

    // Constructor
    public Movie(String showingDate, int cinemaNum, ArrayList<String> showtimes, boolean isPremiere, String movieTitle, double movieLength, String[][] seats) {
        this.showingDate = showingDate;
        this.cinemaNum = cinemaNum;
        this.showtimes = showtimes;
        this.isPremiere = isPremiere;
        this.movieTitle = movieTitle;
        this.movieLength = movieLength;
        this.seats = seats;
    }
    public String getShowingDate() {
        return showingDate;
    }
    public int getCinemaNum() {
        return cinemaNum;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setSeats(String[][] seats) {
        this.seats = seats;
    }

    public String[][] getSeats() {
        return seats;
    }

    public boolean isSeatOccupied(String seatNum) {
        int row = seatNum.charAt(0) - 'A'; // Convert seat letter to row index
        int col = Integer.parseInt(seatNum.substring(1)) - 1; // Convert seat number to column index
        return seats[row][col].equals("Occupied");
    }

    public void getMovieInfo() {
        System.out.println("Showing Date: " + showingDate);
        System.out.println("Cinema Number: " + cinemaNum);
        System.out.println("Is Premiere: " + isPremiere);
        System.out.println("Movie Title: " + movieTitle);
        System.out.println("Movie Length: " + movieLength + " hours");
    }

    public ArrayList<String> getShowtimes() {
        return showtimes;
    }

    public boolean isPremiere() {
        return isPremiere;
    }
}
