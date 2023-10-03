import java.time.*;
import java.util.ArrayList;
import java.util.List;

public class Movie {
    private int cinemaNum;
    private LocalDate showingDate;
    private boolean isPremiere;
    private LocalTime timeStart;
    private String movieTitle;
    private double movieTimeDuration;
    private int seatRows = 8;
    private int seatCols = 5;
    private String[][] seats;

    // based on csv
    public Movie(LocalDate ShowingDate, int cinemaNum, LocalTime timeStart, boolean isPremier,
            String movieTitle, double movieTimeDuration) {
        this.showingDate = ShowingDate;
        this.isPremiere = isPremier;
        this.cinemaNum = cinemaNum;
        this.timeStart = timeStart;
        this.movieTitle = movieTitle;
        this.movieTimeDuration = movieTimeDuration;
        seats = initializezSeats();

    }

   

    // fix this function so that it does not go through all array elements
    private String[][] initializezSeats() {
        String[][] seatPlan = new String[seatRows][seatCols];
        for (int i = 0; i < seatRows; i++) {
            for (int j = 0; j < seatCols; j++) {
                String seatCode = String.format("%c%d", 'A' + i, j + 1);
                seatPlan[i][j] = "[" + seatCode + "]";
            }
        }
        return seatPlan;

    }

    public void displaySeatAvailability() {
        for (int i = 0; i < seatRows; i++) {
            for (int j = 0; j < seatCols; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println(); // Move to the next line after each row
        }
    }


    public void setCinemaNum(int cinemaNum) {
        this.cinemaNum = cinemaNum;
    }

    public int getCinemaNum() {
        return cinemaNum;
    }

    // fix this function so that it does not go through all array elements
    public boolean isSeatOccupied(String seatCode) {
        for (int i = 0; i < seatRows; i++) {
            for (int j = 0; j < seatCols; j++) {
                if (seats[i][j].equals("[" + seatCode + "]")) {
                    return false;
                }
            }
        }

        return true;
    }

    public void setSeatOccupied(ArrayList<String> list) {
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


    public boolean isIsPremiere() {
        return this.isPremiere;
    }

    public boolean getIsPremiere() {
        return this.isPremiere;
    }

    public void setIsPremiere(boolean isPremiere) {
        this.isPremiere = isPremiere;
    }

    public int getSeatRows() {
        return this.seatRows;
    }

    public void setSeatRows(int seatRows) {
        this.seatRows = seatRows;
    }

    public int getSeatCols() {
        return this.seatCols;
    }

    public void setSeatCols(int seatCols) {
        this.seatCols = seatCols;
    }

    public String[][] getSeats() {
        return this.seats;
    }

    public void setSeats(String[][] seats) {
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



    public boolean isMovie(LocalDate date, LocalTime time, int cinemaNum2) {
        
        return false;
    }

}