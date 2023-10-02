import java.time.LocalDateTime;
import java.util.Scanner;

public class Movie {
    int seatNum;
    boolean isSeatOccupied;
    LocalDateTime ShowingDate = LocalDateTime.now();
    boolean isPremier;
    LocalDateTime timeStart = LocalDateTime.now();
    String movieTitle;
    double movieTimeDuration;
    int seats[][];
    public int getSeatNum() {
        return seatNum;
    }
    public void setSeatNum(int seatNum) {
        this.seatNum = seatNum;
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
    
    public Movie(boolean isSeatOccupied) {
        this.isSeatOccupied = isSeatOccupied;
    }

    

    
}
