import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class BookingSystem {
    private static ArrayList<Movie> movies;
    private static Scanner scanner = new Scanner(System.in);

    public BookingSystem() {
        this.movies = new ArrayList<>();
    }

    public static void main(String[] args) {
        BookingSystem bookingSystem = new BookingSystem();
        // Sample movie data

        while (true) {
            displayMainMenu();
            int choice = getUserChoice();
        }
    }

    private static void displayMainMenu() {
        //variables
        BookingSystem bookingSystem = new BookingSystem();

        int choice = 0;
        Date showingDate = parseDate("2023-06-01 12:45");
        Movie movie1 = new Movie(showingDate, 1, showingDate, false, "Kung Fu Panda 2", 1.75);
        Movie movie2 = new Movie(showingDate, 2, showingDate, true, "Avengers: Endgame", 3.0);
        Movie movie3 = new Movie(showingDate, 1, showingDate, true, "Avatar: The Way of Water", 3.15);
        Movie movie4 = new Movie(showingDate, 2, showingDate, false, "A Quiet Place", 1.30);

        bookingSystem.movies.add(movie1);
        bookingSystem.movies.add(movie2);
        bookingSystem.movies.add(movie3);
        bookingSystem.movies.add(movie4);

        do {
            // title screen
            System.out.println("***************NOW SHOWING*****************");
            System.out.println("\t [1] " + movie1.getMovieTitle());
            System.out.println("\t [2] " + movie2.getMovieTitle());
            System.out.println("\t [3] " + movie3.getMovieTitle());
            System.out.println("\t [4] " + movie4.getMovieTitle());
            System.out.println("\t [5] Cancel Reservation          ");
            System.out.println("\t [0] Exit                        ");
            System.out.println("*******************************************");

            // enter movie choice
            reserveSeat();

            // process after choosing Movie
            switch (choice) {
                case 1:
                    
                    break;

                case 2:

                    break;

                case 3:

                    break;

                case 4:

                    break;

                case 5:

                    break;

                case 0:
                    System.exit(0);
                    System.out.println("Thank you come again!");
                    break;

                default:
                    System.out.println("\nINVALID INPUT");
                    System.out.println("ENTERED INPUT MUST BE WITHIN THE CHOICES ONLY\n");

            }

        } while (choice != 0);
    }

    private static int getUserChoice() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public static void reserveSeat() {
    
        System.out.print("Choose Movie: ");
        int selectedMovieIndex = scanner.nextInt();
        if (selectedMovieIndex >= 1 && selectedMovieIndex <= movies.size()) {
            Movie selectedMovie = movies.get(selectedMovieIndex - 1);
    
            // Display the selected movie title and cinema number
            System.out.println("\n" + selectedMovie.getMovieInfo());
            System.out.println("Cinema Number: " + selectedMovie.getCinemaNum());
    
            // Display available showtimes for the selected movie
            ArrayList<Date> showtimes = selectedMovie.getShowtimes();
            for (int i = 0; i < showtimes.size(); i++) {
                System.out.println("[" + (i + 1) + "] " + new SimpleDateFormat("h:mm a").format(showtimes.get(i)));
            }
    
            System.out.print("Choose Time Slot: ");
            int selectedTimeSlot = scanner.nextInt();
    
            if (selectedTimeSlot >= 1 && selectedTimeSlot <= showtimes.size()) {
                Date selectedShowtime = showtimes.get(selectedTimeSlot - 1);
    
                // Display the selected time slot
                System.out.println("Time Slot: " + new SimpleDateFormat("h:mm a").format(selectedShowtime));
    
                // Display seat availability and occupancy for the selected movie and time slot
                selectedMovie.displaySeatAvailability(selectedShowtime);
    
                // Prompt staff to input seats to reserve
                System.out.print("\nInput Seats to Reserve for this Transaction (e.g., A1, A2, A3): ");
                scanner.nextLine(); // Consume the newline
                String selectedSeatsInput = scanner.nextLine().trim();
                String[] selectedSeats = selectedSeatsInput.split(", ");
    
                // Update seat occupancy for the selected time slot
                selectedMovie.setSeatOccupancy((List<String>) Arrays.asList(selectedSeats));
    
                // Ask if they have a Senior Citizen or PWD Card
                System.out.print("Do you have a Senior Citizen or PWD Card? (Yes/No): ");
                String hasCard = scanner.nextLine().trim();
    
                if (hasCard.equalsIgnoreCase("yes")) {
                    // If they have a card, prompt for the quantity and card ID
                    System.out.print("Quantity Senior Citizens / PWDs: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    System.out.print("Please input Senior Citizen Card / PWD Card ID Number: ");
                    String cardId = scanner.nextLine().trim();
    
                    // Proceed to checkout or cancel
                    System.out.print("[1] Proceed to Checkout>>> ");
                    int checkoutChoice = scanner.nextInt();
                    if (checkoutChoice == 1) {
                        // Implement checkout logic here
                        // You have all the necessary information to complete the reservation
                    } else {
                        System.out.println("Transaction canceled.");
                    }
                } else {
                    // Proceed to checkout or cancel without a card
                    System.out.print("[1] Proceed to Checkout>>> ");
                    int checkoutChoice = scanner.nextInt();
                    if (checkoutChoice == 1) {
                        // Implement checkout logic here
                        // You have all the necessary information to complete the reservation
                    } else {
                        System.out.println("Transaction canceled.");
                    }
                }
            } else {
                System.out.println("Invalid time slot choice. Please select a valid option.");
            }
        } else {
            System.out.println("Invalid movie choice. Please select a valid option.");
        }
    }
    

    // Helper method to parse dates
    private static Date parseDate(String dateStr) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return dateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}