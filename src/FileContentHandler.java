import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.util.Scanner;

public class FileContentHandler {
    public ArrayList<Movie> readMovieFile() {
        ArrayList<Movie> movieList = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            Scanner file = new Scanner(new FileReader("Resources/Movies.csv"));

            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] movieData = line.split(",");
                if (movieData.length == 6 && !containsNull(movieData)) {
                    LocalDateTime showingDate = LocalDate.parse(movieData[1].replace("\"", ""), formatter).atStartOfDay();
                    int cinemaNum = Integer.parseInt(movieData[1].replace("\"", ""));

                    LocalDateTime timeStart = LocalDateTime.parse(movieData[2].replace("\"", ""));

                    boolean isPremier = Boolean.parseBoolean(movieData[3].replace("\"", ""));
                    String movieTitle = movieData[4].replace("\"", "");
                    double movieTimeDuration = Double.parseDouble(movieData[5].replace("\"", ""));
                    int[][] seats = new int[5][8];

                    Movie movie = new Movie(showingDate, cinemaNum, timeStart, isPremier, movieTitle, movieTimeDuration, seats);
                    movieList.add(movie);
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
    private boolean containsNull(String[] array) {
        for (String value : array) {
            if (value == null || value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }
}