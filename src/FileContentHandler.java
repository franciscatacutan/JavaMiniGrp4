import java.io.BufferedReader;
import java.time.LocalDateTime;
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
                if(movieData!=null && movieData.length == 6){
                    LocalDateTime showingDate = LocalDateTime.parse(movieData[0], formatter);
                    int cinemaNum = Integer.parseInt(movieData[1]);
                    LocalDateTime timeStart = LocalDateTime.parse(movieData[2]);
                    boolean isPremier = Boolean.parseBoolean(movieData[3]);
                    String movieTitle = movieData[4];
                    double movieTimeDuration = Double.parseDouble(movieData[5]);
                    int[][] seats = new int[4][4];

                    Movie movie = new Movie(showingDate, cinemaNum, timeStart, isPremier, movieTitle, movieTimeDuration, seats);
                } else {
                    System.out.println("Data has invalid/null value, Please try another file"); // To add additional if have time
                }
            }
            file.close();
        } catch (IOException e) {
            System.out.println("File can't be read: " + e);
        }
        return movieList;
    }
}
