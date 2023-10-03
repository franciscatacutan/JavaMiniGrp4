import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class Movie {
    private Date showingDate;
    private int cinemaNum;
    private Date timeStart;
    private boolean isPremiere;
    private String movieTitle;
    private double movieLength;
    private String[][] seats; // 2D array to represent seats
    private static final int rows = 8;
    private static final int SEATS_PER_ROW = 5;
    private ArrayList<Date> showtimes; // List to store movie showtimes
    private Map<Date, List<String>> seatReservations = new HashMap<>();

    // Static list to store all movies to adjust showtimes
    private static ArrayList<Movie> movies = new ArrayList<>();

    public Movie(Date showingDate, int cinemaNum, Date timeStart, boolean isPremiere, String movieTitle, double movieLength) {
        this.showingDate = showingDate;
        this.cinemaNum = cinemaNum;
        this.timeStart = timeStart;
        this.isPremiere = isPremiere;
        this.movieTitle = movieTitle;
        this.movieLength = movieLength;
        this.seats = new String[rows][SEATS_PER_ROW];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                seats[i][j] = String.format("[%c%d]", 'A' + i, j + 1);
            }
        }

        this.showtimes = new ArrayList<>();
        // Adding default showtimes (you can modify these as needed)
        this.showtimes.add(timeStart);
        this.showtimes.add(new Date(timeStart.getTime() + 2 * 60 * 60 * 1000)); // 2 hours later
        this.showtimes.add(new Date(timeStart.getTime() + 4 * 60 * 60 * 1000)); // 4 hours later
        this.showtimes.add(new Date(timeStart.getTime() + 6 * 60 * 60 * 1000)); // 6 hours later


        // Adjust showtimes to avoid conflicts with other movies
        for (Movie movie : movies) {
            if (movie != this) {
                adjustShowtimes(movie.getShowtimes());
            }
        }

        // Add this movie to the static list of movies
        movies.add(this);
    }

    // Adjust showtimes to avoid conflicts with other movies
    private void adjustShowtimes(ArrayList<Date> otherShowtimes) {
        for (Date otherShowtime : otherShowtimes) {
            long timeDifference = otherShowtime.getTime() - timeStart.getTime();
            if (timeDifference > 0) {
                // Adjust the showtime to be later than the other movie
                Date adjustedShowtime = new Date(otherShowtime.getTime() + 60 * 1000); // 1 minute later
                showtimes.add(adjustedShowtime);
            }
        }
    }

    public String getMovieInfo() {
        return "Movie Title: " + movieTitle +
               "\nShowing Date: " + showingDate +
               "\nCinema Number: " + cinemaNum +
               "\nStart Time: " + timeStart +
               "\nIs Premiere: " + isPremiere +
               "\nMovie Length: " + movieLength + " hours";
    }

    public boolean isPremiere() {
        return isPremiere;
    }

    public String[][] getSeats() {
        return seats;
    }

    public void setSeats(String[][] seats) {
        this.seats = seats;
    }

    public void setSeatOccupancy(List<String> list) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                String seatCode = String.format("%c%d", 'A' + i, j + 1);
                if (list.contains(seatCode)) {
                    seats[i][j] = "[X]"; // Seat is occupied
                } else {
                    seats[i][j] = "[" + seatCode + "]"; // Seat label with seat code
                }
            }
        }
    }

    public void reserveSeats(Date selectedShowtime, List<String> seatsToReserve) {
        // Add the reserved seats to the corresponding time slot in the map
        if (!seatReservations.containsKey(selectedShowtime)) {
            seatReservations.put(selectedShowtime, new ArrayList<>());
        }
        seatReservations.get(selectedShowtime).addAll(seatsToReserve);
    }

    public void displaySeatAvailability(Date selectedShowtime) {
        // Display seat availability for the selected time slot

        List<String> reservedSeats = seatReservations.getOrDefault(selectedShowtime, new ArrayList<>());

        System.out.println("\t\t\t\t\t\t\t\t\t\t\t\tSCREEN");

        for (int i = 0; i < rows; i++) {
            System.out.print("|");
            for (int j = 0; j < SEATS_PER_ROW; j++) {
                String seatCode = String.format("[%c%d]", 'A' + i, j + 1);
                if (reservedSeats.contains(seatCode)) {
                    System.out.print("\t[X]"); // Seat is occupied
                } else {
                    System.out.print("\t[" + seats[i][j] + "]");
                }
            }
            System.out.println();
        }

        System.out.println("\nLegend: [LN] = Available Seat  ,  [X] = Seat Occupied");
    }

    // Getters and setters for showtimes
    public ArrayList<Date> getShowtimes() {
        return showtimes;
    }

    public void setShowtimes(ArrayList<Date> showtimes) {
        this.showtimes = showtimes;
    }

    public int getCinemaNum() {
        return cinemaNum;
    }

    public Date getShowingDate() {
        return showingDate;
    }

    public double getMovieLength() {
        return movieLength;
    }

    public void addShowtime(Date showingDate2) {
    }
}
