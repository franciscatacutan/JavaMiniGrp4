import java.time.LocalDateTime;

public class Movie {
    private int seatNum;
    private boolean isSeatOccupied;
    private LocalDateTime ShowingDate;
    private boolean isPremier;
    private LocalDateTime timeStart;
    private String movieTitle;
    private double movieTimeDuration;
    private int seatRow = 8;
    private int seatCols = 5;
    private  boolean [][] seats;


    public Movie(int seatNum, boolean isSeatOccupied, LocalDateTime ShowingDate, boolean isPremier, LocalDateTime timeStart, String movieTitle, double movieTimeDuration, int seats[][]) {
       // this.seatNum = seatNum;
        //this.isSeatOccupied = isSeatOccupied;
    int id;
    int cinemaNum;
    boolean isSeatOccupied;
    LocalDateTime ShowingDate;
    boolean isPremier;
    LocalDateTime timeStart;
    String movieTitle;
    double movieTimeDuration;
    int seats[][];

    public Movie(int id, int cinemaNum, boolean isSeatOccupied, LocalDateTime ShowingDate, boolean isPremier,
            LocalDateTime timeStart, String movieTitle, double movieTimeDuration, int seats[][]) {
        this.isSeatOccupied = isSeatOccupied;
        this.ShowingDate = ShowingDate;
        this.isPremier = isPremier;
        this.timeStart = timeStart;
        this.movieTitle = movieTitle;
        this.movieTimeDuration = movieTimeDuration;
        this.seats = new boolean[seatRow][seatCols];
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

    public LocalDateTime getShowingDate() {
        return ShowingDate;
    }

    public void setShowingDate(LocalDateTime showingDate) {
        ShowingDate = showingDate;
    }

    public boolean isPremier() {
        return isPremier;
    }

    public void setPremier(boolean isPremier) {
        this.isPremier = isPremier;
    }

    public LocalDateTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalDateTime timeStart) {
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
