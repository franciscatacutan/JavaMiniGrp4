import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
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

                    Movie movie = new Movie(showingDate, cinemaNum, timeStart, isPremier, movieTitle,
                            movieTimeDuration);
                    movieList.put(idCounter, movie);
                    idCounter++;
                } else { // Null Value
                    System.out.println("Data has invalid/null value, Please try another file"); // To add additional if
                                                                                                // still have time
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

                    int ticketNum = Integer.parseInt(resData[0].replace("\"", ""));
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


    private boolean containsNull(String[] array) {
        for (String value : array) {
            if (value == null || value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void reservationFileWrite_toCSV(Reservation reservation) {
        File filePath = new File("Resources/Reservations.csv");

        try {
            if (!filePath.exists()) {
                if (filePath.createNewFile()) {
                    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
                        writer.write(toCSVString(reservation));
                    } catch (IOException e) {
                        System.err.println("Error writing reservation details to CSV file: " + e.getMessage());
                    }
                } else {
                    System.err.println("Unable to create the CSV file.");
                }
            }

        } catch (IOException e) {
            System.err.println("Error creating the CSV file: " + e.getMessage());
        }
    }

    public String toCSVString(Reservation reservation) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("=============================================\n")
                .append("Reservation Details:\n")
                .append("=============================================\n")
                .append("Ticket Number: ").append(reservation.getReserveTicketNum()).append("\n")
                .append("Date: ").append(reservation.getDate()).append("\n")
                .append("Cinema Number: ").append(reservation.getCinemaNum()).append("\n")
                .append("Time: ").append(reservation.getTime()).append("\n")
                .append("Seats: ").append(String.join(", ", reservation.getSeats())).append("\n")
                .append("Price: Php").append(String.format("%.2f", reservation.getPrice()))
                .append("\n=============================================\n");
        return csvContent.toString();
    }
}