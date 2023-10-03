import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Reservation {
    private double pricePerSeat; // Price per seat based on movie type
    private ArrayList<String> seats;
    private String movieTitle;
    private int cinemaNumber;
    private Date showingDate;
    private String time;
    private int numberOfTickets;
    private int numberOfDiscountedTickets;
    private boolean isPremiere; // Add this field

    public Reservation(String movieTitle, int cinemaNumber, Date showtime, boolean isPremiere) {
        this.movieTitle = movieTitle;
        this.cinemaNumber = cinemaNumber;
        this.showingDate = showtime;
        this.time = new SimpleDateFormat("h:mm a").format(showtime);
        this.seats = new ArrayList<>();
        this.isPremiere = isPremiere; // Set the isPremiere field

        // Set the price per seat based on whether it's a premiere movie
        if (isPremiere) {
            this.pricePerSeat = 500.0;
        } else {
            this.pricePerSeat = 350.0;
        }
    }

    public Reservation(String movieTitle2, int cinemaNum, Date showtime) {
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setCinemaNumber(int cinemaNumber) {
        this.cinemaNumber = cinemaNumber;
    }

    public void setShowingDate(Date showingDate) {
        this.showingDate = showingDate;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }

    public void setNumberOfDiscountedTickets(int numberOfDiscountedTickets) {
        this.numberOfDiscountedTickets = numberOfDiscountedTickets;
    }

    public void reserveSeats(ArrayList<String> seats) {
        // Perform seat reservation logic here
        this.seats.addAll(seats);
    }

    public double calculateSubtotal() {
        // Calculate the subtotal based on the number of reserved seats
        return pricePerSeat * seats.size();
    }

    public double calculateDiscountAmount() {
        // Calculate the discount amount based on the number of discounted tickets (20% for senior citizens)
        if (numberOfDiscountedTickets > 0) {
            double discount = 0.20; // 20% discount
            return discount * pricePerSeat * numberOfDiscountedTickets;
        }
        return 0.0;
    }

    public double calculateTotalAmount() {
        // Calculate the total amount considering the discount
        double subtotal = calculateSubtotal();
        double discountAmount = calculateDiscountAmount();
        return subtotal - discountAmount;
    }

    public String generateTransactionReferenceNumber() {
        // Generate a random transaction reference number
        Random random = new Random();
        int referenceNumber = random.nextInt(1000000); // Adjust as needed
        return String.format("%06d", referenceNumber); // Format as a 6-digit string
    }

    public String generateSummary() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM d, yyyy");
        StringBuilder summary = new StringBuilder();

        summary.append("*****************SUMMARY*****************\n");
        summary.append("Movie Title:\n").append(movieTitle).append("\n");
        summary.append("Cinema Number:\n").append(cinemaNumber).append("\n");
        summary.append("Date:\n").append(dateFormat.format(showingDate)).append("\n");
        summary.append("Time:\n").append(time).append("\n");
        summary.append("Number of Ticket/s:\n").append(numberOfTickets).append("\n");
        summary.append("Seats Reserved:\n").append(seats).append("\n");
        summary.append("Subtotal:\n₱").append(String.format("%.2f", calculateSubtotal())).append("\n");
        summary.append("Discount Amount:\n₱").append(String.format("%.2f", calculateDiscountAmount())).append("\n");
        summary.append("Total Amount:\n₱").append(String.format("%.2f", calculateTotalAmount())).append("\n");

        return summary.toString();
    }

    public String generateReceipt() {
        StringBuilder receipt = new StringBuilder();

        receipt.append("Receipt for ").append(isPremiere ? "Premiere" : "Regular").append(" Movie (").append(cinemaNumber).append(")\n\n");
        receipt.append("Cinema World\n");
        receipt.append("Transaction Reference Number:\n").append(generateTransactionReferenceNumber()).append("\n");
        receipt.append("Movie Title:\n").append(movieTitle).append("\n");
        receipt.append("Cinema Number:\n").append(cinemaNumber).append("\n");
        receipt.append("Date:\n").append(showingDate).append("\n");
        receipt.append("Time:\n").append(time).append("\n");
        receipt.append("Number of Ticket/s:\n").append(numberOfTickets).append("\n");
        receipt.append("Number of Discounted Tickets:\n").append(numberOfDiscountedTickets).append("\n");
        receipt.append("Seats Reserved:\n").append(seats).append("\n");
        receipt.append("Subtotal:\n₱").append(String.format("%.2f", calculateSubtotal())).append("\n");
        receipt.append("Discount Amount:\n₱").append(String.format("%.2f", calculateDiscountAmount())).append("\n");
        receipt.append("Total Amount:\n₱").append(String.format("%.2f", calculateTotalAmount())).append("\n\n");
        receipt.append("**END\n\n");
        receipt.append("[1] <<<Make Another Transaction?\n");
        receipt.append("[2] Exit >>>");

        return receipt.toString();
    }
}
