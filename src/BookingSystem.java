
public class BookingSystem {

    public static void main(String[] args) {
        System.out.println("Elijah");
    }

    public String reserveSeat(String seatNum, Movie movie) {
        return "reserve";
    }

    public double calculateAmount(int seatNums, int senior, boolean isPremier) {
        double price;
        // double premierPrice;

        if (isPremier) {
            price = seatNums * 500;

        } else {
            price = (seatNums - senior) * 350 + ((senior * 350) * 0.20);
        }

        return price;
    }

    public void seatAvailable(Movie movie) {
    }
}
