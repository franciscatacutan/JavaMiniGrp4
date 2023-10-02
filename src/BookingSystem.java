
public class BookingSystem {

    public static void main(String[] args) {
        BookingSystem bs;
        try {
            bs = new BookingSystem();
            bs.startProgram();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void startProgram() {
        System.out.println("Elijah");

    }

    public String reserveSeat(String seatNum, Movie movie) {
        return "reserve";
    }

    public double calculateAmount(int seatNums, int senior) {
        double price = 0;
        return price;
    }

    public void seatAvailable(Movie movie) {
    }
}
