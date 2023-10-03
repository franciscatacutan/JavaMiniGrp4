import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class FileContentHandler {

    public HashMap<Integer, Movie> readMovieFile() {
        HashMap<Integer, Movie> movieList = new HashMap<Integer, Movie>();
        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
        int idCounter = 1;

        try {
            Scanner file = new Scanner(new FileReader("Resources/Movies.csv"));

            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] movieData = line.split(",");
                if (movieData.length == 6 && !containsNull(movieData)) {
                    LocalDate showingDate = LocalDate.parse(movieData[0].replace("\"", ""));
                    int cinemaNum = Integer.parseInt(movieData[1].replace("\"", ""));
                    LocalTime timeStart = LocalTime.parse(movieData[2].replace("\"", ""));
                    boolean isPremier = Boolean.parseBoolean(movieData[3].replace("\"", ""));
                    String movieTitle = movieData[4].replace("\"", "");
                    double movieTimeDuration = Double.parseDouble(movieData[5].replace("\"", ""));

                        Movie movie = new Movie(showingDate, cinemaNum, timeStart, isPremier, movieTitle, movieTimeDuration);
                        movieList.put(idCounter, movie);
                        idCounter++;
                } else { // Null Value
                    System.out.println("Data has invalid/null value, Please try another file"); // To add additional if still have time
                }
            }
            file.close();
        } catch (IOException e) {
            System.out.println("File can't be read: " + e);
        }
        return movieList;
    }


    public ArrayList<Reservation> readReservationFile() {
        ArrayList<Reservation> resList = new ArrayList<>();

        try {
            Scanner file = new Scanner(new FileReader("Resources/Reservations.csv"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] resData = line.split("\",");

                if (resData != null && resData.length == 6) {

                    long ticketNum = Long.parseLong(resData[0].replace("\"", ""));
                    LocalDate date = LocalDate.parse(resData[1].replace("\"", ""));
                    int cinemaNum = Integer.parseInt(resData[2].replace("\"", ""));
                    LocalTime time = LocalTime.parse(resData[3].replace("\"", ""));
                    double price = Double.parseDouble(resData[5].replace("\"", ""));
                    ArrayList<String> seats = new ArrayList<>();

                    String[] seatsArr = resData[4].replace("\"", "").split(",");
                    for (String s : seatsArr) {
                        seats.add(s);
                    }

                    // Rservation boject
                    Reservation res = new Reservation(ticketNum, date, cinemaNum, time, seats, price);
                    resList.add(res);

                } else {
                    System.out.println("Data has invalid/null value, Please try another file"); // To add additional if
                    // have time
                }
            }
            file.close();

        } catch (Exception e) {
            System.out.println("File can't be read: " + e);
        }
        return resList;
    }

    // Add a method to write the reservation to a CSV file
    public void reservationFileWrite_toCSV() {
        File filePath = new File("Resources/Reservations.csv");
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.write(toCSVString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String toCSVString(Reservation reservation, Movie movie) {

        StringBuilder csvContent = new StringBuilder();
        csvContent.append(reservation.getReserveTicketNum()).append(",");
        csvContent.append(movie.getShowingDate()).append(",");
        csvContent.append(movie.getCinemaNum()).append(",");
        csvContent.append(movie.getTimeStart()).append(",");
        csvContent.append(String.join(",", reservation.getSeats())).append(",");
        csvContent.append(reservation.getPrice()).append("\n");
        return csvContent.toString();
    }

    private boolean containsNull(String[] array) {
        for (String value : array) {
            if (value == null || value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

}