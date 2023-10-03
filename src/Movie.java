import java.time.*;
import java.util.List;

public class Movie {
    private int id;
    private int cinemaNum;
    private boolean isSeatOccupied;
    private LocalDate showingDate;
    private boolean isPremiere;
    private LocalTime timeStart;
    private String movieTitle;
    private double movieTimeDuration;
    private int seatRows = 8;
    private int seatCols = 5;
    private String [][] seats;


    // based on csv
    public Movie(int id, LocalDate ShowingDate, int cinemaNum, LocalTime timeStart, boolean isPremier, String movieTitle, double movieTimeDuration) {
        this.id = id;
        this.showingDate = ShowingDate;
        this.isPremiere = isPremier;
        this.cinemaNum = cinemaNum;
        this.timeStart = timeStart;
        this.movieTitle = movieTitle;
        this.movieTimeDuration = movieTimeDuration;
        
    }

    public int getId() {
        return id;
    }

    public void setCinemaNum(int cinemaNum) {
        this.cinemaNum = cinemaNum;
    }

    public int getCinemaNum() {
        return cinemaNum;
    }

    public boolean isSeatOccupied(int row, int col) {
        if (row >= 0 && row < seatRows && col >= 0 && col < seatCols) {
            return seats[row][col];
        } else {
            return false; // Invalid seat position
        }
    }

    public void setSeatOccupied(List<String> list) {
        for (int i = 0; i < seatRows; i++) {
            for (int j = 0; j < seatCols; j++) {
                String seatCode = String.format("%c%d", 'A' + i, j + 1);
                if (list.contains(seatCode)) {
                    seats[i][j] = "[X]"; // Seat is occupied
                } else {
                    seats[i][j] = "[" + seatCode + "]"; // Seat label with seat code
                }
            }
        }
    }

    public LocalDate getShowingDate() {
        return showingDate;
    }

    public void setShowingDate(LocalDate showingDate) {
        showingDate = showingDate;
    }

    public boolean isPremiere() {
        return isPremiere;
    }

    public void setPremiere(boolean isPremier) {
        this.isPremiere = isPremier;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public double getMovieTimeDuration() {
        return movieTimeDuration;
    }

    public void setMovieTimeDuration(double movieTimeDuration) {
        this.movieTimeDuration = movieTimeDuration;
    }

    public boolean[][] getSeats() {
        return seats;
    }

    public void setSeats(boolean[][] seats) {
        this.seats = seats;
    }

    public String getMovieInfo() {
        return "Movie Title: " + movieTitle +
               "\nShowing Date: " + showingDate +
               "\nCinema Number: " + cinemaNum +
               "\nStart Time: " + timeStart +
               "\nIs Premiere: " + isPremiere +
               "\nMovie Length: " + movieTimeDuration + " hours";
    }

}