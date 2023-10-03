import java.time.*;

public class Movie {
    private int id;
    private int cinemaNum;
    private boolean isSeatOccupied;
    private LocalDate showingDate;
    private boolean isPremier;
    private LocalTime timeStart;
    private String movieTitle;
    private double movieTimeDuration;
    private int seatRow = 8;
    private int seatCols = 5;
    private  boolean [][] seats;


    // based on csv
    public Movie(LocalDate ShowingDate, int cinemaNum, LocalTime timeStart, boolean isPremier, String movieTitle, double movieTimeDuration) {
       // this.seatNum = seatNum;
        //this.isSeatOccupied = isSeatOccupied;
        this.showingDate = ShowingDate;
        this.isPremier = isPremier;
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
        if (row >= 0 && row < seatRow && col >= 0 && col < seatCols) {
            return seats[row][col];
        } else {
            return false; // Invalid seat position
        }
    }

    public void setSeatOccupied(int row, int col, boolean isOccupied) {
        if (row >= 0 && row < seatRow && col >= 0 && col < seatCols) {
            seats[row][col] = isOccupied;
            for (row = 0; row < seatRow; row++) {
                for (col = 0; col < seatCols; col++) {
                    if (seats[row][col]) {
                        System.out.print(" "); // "O" for available
                    } else {
                        System.out.print("X "); // "X" for occupied
                    }
                }
                System.out.println(); // Move to the next row
            }
        }
    }

    public LocalDate getShowingDate() {
        return showingDate;
    }

    public void setShowingDate(LocalDateTime showingDate) {
        showingDate = showingDate;
    }

    public boolean isPremier() {
        return isPremier;
    }

    public void setPremier(boolean isPremier) {
        this.isPremier = isPremier;
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

}