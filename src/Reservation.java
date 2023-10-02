import java.text.DecimalFormat;
import java.util.ArrayList;

public class Reservation {
    private double price;
    private long reservationTicketNum;
    private ArrayList<String> seats;

    public Reservation(long reservationTicketNum, String movieSchedule, int cinemaNumber, String timeSlot, ArrayList<String> reservedSeats) {
        this.reservationTicketNum = reservationTicketNum;
        this.seats = reservedSeats;

        // Calculate the price based on the movie schedule (premiere or regular)
        if (movieSchedule != null && movieSchedule.contains("Premiere")) {
            this.price = 500.00; // Premiere movie price
        } else {
            this.price = calculateRegularPrice(reservedSeats.size());
        }
    }

    public double calculateRegularPrice(int numSeats) {
        double basePrice = 350.00; // Regular movie price per seat
        return basePrice * numSeats;
    }

    public String generateReservationTicket() {
        // Convert the list of reserved seats to a comma-separated string
        String reservedSeatsStr = String.join(",", seats);

        // Format the total amount to have two decimal places
        DecimalFormat df = new DecimalFormat("#0.00");
        String totalAmountStr = df.format(price);

        // Generate the reservation ticket as per the format you provided
        return reservationTicketNum + "," + seats + "," + totalAmountStr;
    }

    public double getPrice() {
        return price;
    }
}
