import java.io.BufferedReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileContentHandler {

    public ArrayList<Movie> readMovieFile() {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new FileReader("Resources/Movies.csv"));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] movieData = line.split(",");
                if (movieData != null && movieData.length == 6) {
                    LocalDateTime showingDate = LocalDate.parse(movieData[1].replace("\"", ""), formatter)
                            .atStartOfDay();
                    int cinemaNum = Integer.parseInt(movieData[1].replace("\"", ""));
                    LocalDateTime timeStart = LocalDateTime.parse(movieData[2]);
                    boolean isPremier = Boolean.parseBoolean(movieData[3]);
                    String movieTitle = movieData[4];
                    double movieTimeDuration = Double.parseDouble(movieData[5]);
                    int[][] seats = new int[4][4];

                    Movie movie = new Movie(showingDate, cinemaNum, timeStart, isPremier, movieTitle, movieTimeDuration,
                            seats);
                } else {
                    System.out.println("Data has invalid/null value, Please try another file"); // To add additional if
                                                                                                // have time
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

}
