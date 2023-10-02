import java.util.ArrayList;
import java.util.Scanner;

public class BookingSystem {
    private static ArrayList<Movie> cinemaList = new ArrayList<>();
    private static final double DISCOUNT = 0.2; // 20% discount for senior citizens
    private static ArrayList<Reservation> reservations = new ArrayList<>();
    private static long reservationTicketCounter = 1000; // Initial reservation ticket number
    private static String selectedTimeSlot; // Added to store the selected time slot

    public static void main(String[] args) {
        // Initialize cinemaList with movies
        initializeMovies();

        Scanner scanner = new Scanner(System.in);
        int choice;

        while (true) {
            System.out.println("***********NOW SHOWING*************\n");
            displayMovieSchedule();
            System.out.println("[1] Display Seat");
            System.out.println("[2] Reserve Seat");
            System.out.println("[3] Movie Schedule");
            System.out.println("[4] Exit");
            System.out.print("Choose Option: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Implement display seat option
                    displaySeatAvailability();
                    break;
                case 2:
                    // Implement reserve seat option
                    //reserveSeats();
                    break;
                case 3:
                    // Implement movie schedule option
                    displayMovieSchedule();
                    break;
                case 4:
                    System.out.println("Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please choose a valid option.");
            }
        }
    }

    private static void initializeMovies() {
        // Sample movie 1
        String[][] seats1 = new String[8][5]; // 8 rows with 5 seats per row for cinema 1
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                char seatLetter = (char) ('A' + row);
                String seatCode = seatLetter + String.valueOf(col + 1);
                seats1[row][col] = seatCode;
            }
        }
        ArrayList<String> showtimes1 = new ArrayList<>();
        showtimes1.add("12:30");
        showtimes1.add("15:00");
        showtimes1.add("17:30");

        Movie movie1 = new Movie("2021-06-01", 1, showtimes1, false, "Movie 1", 2.0, seats1);

        // Sample movie 2
        String[][] seats2 = new String[8][5]; // 8 rows with 5 seats per row for cinema 2
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 5; col++) {
                char seatLetter = (char) ('A' + row);
                String seatCode = seatLetter + String.valueOf(col + 1);
                seats2[row][col] = seatCode;
            }
        }
        ArrayList<String> showtimes2 = new ArrayList<>();
        showtimes2.add("13:00");
        showtimes2.add("15:30");
        showtimes2.add("18:00");

        Movie movie2 = new Movie("2021-06-01", 2, showtimes2, true, "Movie 2 (Premiere)", 2.5, seats2);

        cinemaList.add(movie1);
        cinemaList.add(movie2);
    }

    private static void displayMovieSchedule() {
        // Display available movies and showtimes
        System.out.println("***********NOW SHOWING*************\n");
        for (int i = 0; i < cinemaList.size(); i++) {
            Movie movie = cinemaList.get(i);
            System.out.println("[" + (i + 1) + "] " + movie.getMovieTitle());
            ArrayList<String> showtimes = movie.getShowtimes();
            System.out.println(showtimes);
        }
        System.out.println();
    }

    private static void displaySeatAvailability() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Choose a movie (1-" + cinemaList.size() + "): ");
        int movieChoice = scanner.nextInt();

        if (movieChoice >= 1 && movieChoice <= cinemaList.size()) {
            Movie selectedMovie = cinemaList.get(movieChoice - 1);
            selectedTimeSlot = displayTimeSlots(selectedMovie);

            // Display seat availability for the selected movie and time slot
            displaySeats(selectedMovie);
        } else {
            System.out.println("Invalid movie choice.");
        }
    }

    private static String displayTimeSlots(Movie movie) {
        ArrayList<String> showtimes = movie.getShowtimes();
        System.out.println("Choose a time slot:");

        for (int i = 0; i < showtimes.size(); i++) {
            System.out.println("[" + (i + 1) + "] " + showtimes.get(i));
        }

        Scanner scanner = new Scanner(System.in);
        int timeChoice = scanner.nextInt();

        if (timeChoice >= 1 && timeChoice <= showtimes.size()) {
            return showtimes.get(timeChoice - 1);
        } else {
            System.out.println("Invalid time slot choice.");
            return null;
        }
    }

    private static void displaySeats(Movie movie) {
        String[][] seats = movie.getSeats();

        // Display seat availability and occupancy
        System.out.println("Cinema Number: " + movie.getCinemaNum());
        System.out.println("Date and Time of Screening: " + movie.getShowingDate());
        System.out.println("Time Slot: " + selectedTimeSlot);
        System.out.println("Seat Availability and Occupancy:");
        System.out.println("                        SCREEN");

        for (int row = 0; row < seats.length; row++) {
            System.out.print("|");
            for (int col = 0; col < seats[row].length; col++) {
                if (seats[row][col].equals("LN")) {
                    // Available seat
                    System.out.print("\t[" + (char) ('A' + row) + (col + 1) + "]");
                } else if (seats[row][col].equals("X")) {
                    // Seat occupied
                    System.out.print("\t[X]");
                } else {
                    // Other (e.g., aisle)
                    System.out.print("\t" + seats[row][col]);
                }
            }
            System.out.println();
        }

    }

    // Implement other methods here

    private static double calculateAmount(int numSeats, int seniorCount) {
        // Calculate the total reservation amount based on seat count and senior count
        // Implement according to your project requirements
        return 0.0; // Placeholder value, replace with actual calculation
    }

    private static void cancelSeatReservation(long ticketNum) {
        // Cancel a reservation based on the ticket number
        // Implement according to your project requirements
    }
}
