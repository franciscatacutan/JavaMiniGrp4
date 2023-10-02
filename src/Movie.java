import java.time.LocalDateTime;

public class Movie {
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
        this.seats = seats;
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

    public boolean isSeatOccupied() {
        return isSeatOccupied;
    }

    public void setSeatOccupied(boolean isSeatOccupied) {
        this.isSeatOccupied = isSeatOccupied;
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

    public int[][] getSeats() {
        return seats;
    }

    public void setSeats(int[][] seats) {
        this.seats = seats;
    }

}
