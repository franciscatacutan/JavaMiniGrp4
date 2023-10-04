import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class FileContentHandler {

    /**
     * Does the ff:
     * 1: Reads the Movie csv File from the Resource Directory.
     * 2: Stores the value in a Movie object.
     * 3: Adds the Movie object to the hashmap.
     *
     * @return Movies stored in a HashMap
     */
    public HashMap<Integer, Movie> readMovieFile() {
        HashMap<Integer, Movie> movieList = new HashMap<Integer, Movie>();
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
                } else { // File Movie has NULL value
                    System.out.println("Data has invalid/null value, Please try another file");
                    System.exit(1);
                }
            }
            file.close();
        } catch (IOException e) {
            System.err.println("(ERROR)Movie File can't be read: " + e);
        }
        return movieList;
    }

    /**
     * Does the ff:
     * 1: Reads the Reservation csv File from the Resource Directory.
     * 2: Stores the value in a Reservation object.
     * 3: Stores the created Reservation object in an Arraylist of Reservation.
     *
     * @return an ArrayList of Reservation
     */
    public ArrayList<Reservation> readReservationFile() {
        ArrayList<Reservation> resList = new ArrayList<>();

        try {
            File filePath = new File("Resources/Reservations.csv");
            if (!filePath.exists()) {
                try {
                    filePath.createNewFile();
                } catch (IOException e) {
                    System.err.println("(ERROR)Reservation File: " + e.getMessage());
                    System.exit(1);
                }
            }

            Scanner file = new Scanner(new FileReader("Resources/Reservations.csv"));

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
                    System.out.println("Data has invalid/null value, Please try another file"); // To add additional if have time
                }
            }
            file.close();

        } catch (Exception e) { // Exits System Runtime after displaying message
            System.err.println("(ERROR) Reservation File can't be read: " + e);
            System.exit(1);
        }
        return resList;
    }

    /**
     * Deletes all the values of the chosen Reservation
     *
     * @param ticketNumber the reference for deletion of record
     */
    public void deleteReservation(int ticketNumber) {
        File inputFile = new File("Resources/Reservations.csv");
        File tempFile = new File("Resources/TempReservations.csv");
        String line;
        boolean isFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             PrintWriter writer = new PrintWriter(new FileWriter(tempFile, true))) {
            while ((line = reader.readLine()) != null) {
                String[] resData = line.split("\",");
                if (resData != null && resData.length == 6) {
                    int currentTicketNum = Integer.parseInt(resData[0].replace("\"", ""));
                    if (currentTicketNum == ticketNumber) {
                        isFound = true;
                        
                    } else {
                        writer.println(line);
                    }
                }
            }

            if (!isFound) {
                System.err.println("(ERROR)Reservation with ticket number " + ticketNumber + " not isFound.");
                tempFile.delete();
                return;
            }

        } catch (IOException e) { // Exits System Runtime after displaying message
            System.err.println("(ERROR)Deleting reservation from CSV file: " + e.getMessage());
            System.exit(1);
        }

        if (!inputFile.delete()) {
            System.out.println("Could not delete file: " + inputFile);
            return;
        }
        if (isFound && tempFile.renameTo(inputFile)) {
            System.out.println("Reservation with ticket number " + ticketNumber + " deleted successfully.");
        } else {
            System.err.println("(ERROR)Unable to update tempReservations CSV file. Cannot rename to Reservations.csv file.");
        }
    }

    /**
     * Determines if value is null.
     *
     * @param array the value that will be used for determining.
     * @return boolean
     */
    private boolean containsNull(String[] array) {
        for (String value : array) {
            if (value == null || value.trim().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Write File Reservations to the csv.
     *
     * @param reservation the value determined to be written on the csv.
     */
    public void reservationFileWrite_toCSV(Reservation reservation) {
        File filePath = new File("Resources/Reservations.csv");

        if (!filePath.exists()) {
            try {
                filePath.createNewFile();
            } catch (IOException e) { // Exits System Runtime after displaying message
                System.err.println("File not Updated: " + e.getMessage());
                System.exit(1);
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.write(toCSVString(reservation) + "\n");
        } catch (IOException e) { // Exits System Runtime after displaying message
            System.err.println("(ERROR)Writing reservation details to CSV file: " + e.getMessage());
            System.exit(1);
        }

    }

    /**
     * Used for formatting the output result to the csv.
     *
     * @param reservation the value used for determining the print values.
     * @return csvContent
     */
    public String toCSVString(Reservation reservation) {
        StringBuilder csvContent = new StringBuilder();
        csvContent.append('"').append(reservation.getReserveTicketNum()).append('"').append(",")
                .append('"').append(reservation.getDate()).append('"').append(",")
                .append('"').append(reservation.getCinemaNum()).append('"' + ",")
                .append('"').append(reservation.getTime()).append('"').append(",")
                .append('"').append(String.join(", ", reservation.getSeats())).append('"').append(",")
                .append('"').append(String.format("%.2f", reservation.getPrice())).append('"');
        return csvContent.toString();
    }
}