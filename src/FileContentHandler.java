import java.util.ArrayList;
import java.io.IOException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class FileContentHandler {
    public ArrayList<Movie> readMovieFile() {
        ArrayList<Movie> movieList = new ArrayList<>();
        try {
            Scanner file = new Scanner(new FileReader("Resources/Movies.csv"));

            while (file.hasNextLine()) {
                String line = file.nextLine();
                String[] movieData = line.split(",");
                if (movieData.length == 6 && !containsNull(movieData)) {
                    String showingDate = movieData[0];
                    int cinemaNum = Integer.parseInt(movieData[1]);

                    String[] timeStartArray = movieData[2].split(",");
                    ArrayList<String> timeStart = new ArrayList<>(Arrays.asList(timeStartArray));

                    boolean isPremier = Boolean.parseBoolean(movieData[3]);
                    String movieTitle = movieData[4];
                    double movieTimeDuration = Double.parseDouble(movieData[5]);
                    String[][] seats = new String[4][4];

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