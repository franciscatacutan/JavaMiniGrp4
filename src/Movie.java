import java.time.*;
import java.util.ArrayList;

public class Movie {
    private int cinemaNum;
    private LocalDate showingDate;
    private boolean isPremiere;
    private LocalTime timeStart;
    private String movieTitle;
    private double movieTimeDuration;
    private int SEAT_ROWS = 8;
    private int SEAT_COLS = 5;
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

    private String[][] initializezSeats() {
        String[][] seatPlan = new String[SEAT_ROWS][SEAT_COLS];
        for (int i = 0; i < SEAT_ROWS; i++) {
            for (int j = 0; j < SEAT_COLS; j++) {
                String seatCode = String.format("%c%d", 'A' + i, j + 1);
                seatPlan[i][j] = "[" + seatCode + "]";
            }
        }
        return seatPlan;

    }

    public void displaySeatAvailability() {
        for (int i = 0; i < SEAT_ROWS; i++) {
            for (int j = 0; j < SEAT_COLS; j++) {
                System.out.print(seats[i][j] + " ");
            }
            System.out.println(); // Move to the next line after each row
        }
    }

    public boolean isSeatOccupied(String seatCode) {
        int[] i = seatCodeToIndexes(seatCode);

        if (seats[i[0]][i[1]].equals("[XX]")) {
            return true;
        }
        return false;
    }

    public void setSeatOccupied(ArrayList<String> list) {
        for (String s : list) {
            int[] i = seatCodeToIndexes(s);
            seats[i[0]][i[1]] = "[XX]";
        }
    }

    public void setSeatsAvailable(ArrayList<String> list) {
        for (String s : list) {
            int[] i = seatCodeToIndexes(s);
            seats[i[0]][i[1]] = "[" + s + "]";
        }
    }

    public int[] seatCodeToIndexes(String s) {
        char code = s.charAt(0);
        int col = Integer.parseInt(s.substring(1));

        return new int[] { code - 'A', col - 1 };
    }

    public boolean isMovie(LocalDate date, LocalTime time, int cinemaNum) {

        if (showingDate.equals(date) && timeStart.equals(time) && this.cinemaNum == cinemaNum) {
            return true;
        }

        return false;
    }

    public void setCinemaNum(int cinemaNum) {
        this.cinemaNum = cinemaNum;
    }

    public int getCinemaNum() {
        return cinemaNum;
    }

    public LocalDate getShowingDate() {
        return showingDate;
    }

    public void setShowingDate(LocalDate showingDate) {
        this.showingDate = showingDate;
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

    public boolean getIsPremiere() {
        return this.isPremiere;
    }

    public void setIsPremiere(boolean isPremiere) {
        this.isPremiere = isPremiere;
    }

    public int getSEAT_ROWS() {
        return this.SEAT_ROWS;
    }

    public void setSEAT_ROWS(int seatRows) {
        this.SEAT_ROWS = seatRows;
    }

    public int getSEAT_COLS() {
        return this.SEAT_COLS;
    }

    public void setSEAT_COLS(int seatCols) {
        this.SEAT_COLS = seatCols;
    }

    public String[][] getSeats() {
        return this.seats;
    }

    public void setSeats(String[][] seats) {
        this.seats = seats;
    }

    public String getMovieInfo() {
        return "*************** " + movieTitle + " *****************" +
                "\nCinema Number: " + cinemaNum +        
                "\nShowing Date: " + showingDate +
                "\nStart Time: " + timeStart +
                "\nIs Premiere: " + isPremiere +
                "\nMovie Length: " + movieTimeDuration + " hours";
    }

}