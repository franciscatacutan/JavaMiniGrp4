import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.zip.DataFormatException;

public class FileContentHandler {

    /**
     * Does the ff:
     * 1: Reads the Movie csv File from the Resource Directory.
     * 2: Stores the value in a Movie object.
     * 3: Adds the Movie object to the hashmap.
     *
     * @return Movies stored in a HashMap
     */
    public HashMap<Integer, Movie> readMovieFile() throws DataFormatException, FileNotFoundException {
        HashMap<Integer, Movie> movieList = new HashMap<Integer, Movie>();
        int idCounter = 1;
        int lineNum = 1;


        try {
            File filePath = new File("Resources/Reservations.csv");
            Scanner file = new Scanner(new FileReader("Resources/Movies.csv"));

            if(filePath.exists()){
                if(!file.hasNextLine()){
                    System.err.println("[ERROR] No Data within Movie File.");
                    System.exit(1);
                } else {
                    while (file.hasNextLine()) {
                        String line = file.nextLine();
                        String[] movieData = line.split(",");

                        if (!containsNull(movieData) && movieData.length == 6) {
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
                        } else {
                            System.err.println("[ERROR] No Data within the Movie File.");
                            System.exit(1);
                        }
                        lineNum++;
                    }
                    file.close();
                }
            } else {
                System.err.println("[ERROR] No Movie File is exists.");
                System.exit(1);
            }

        } catch (RuntimeException e) {
            System.err.println("[ERROR] A Movie/s is invalid within the Movie File: " + e +
                    "\nFound at Line Number:  " + lineNum);
            System.exit(1);
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
    public ArrayList<Reservation> readReservationFile() throws DataFormatException, FileNotFoundException {
        ArrayList<Reservation> resList = new ArrayList<>();

        int lineNum = 1;

        try {
            File filePath = new File("Resources/Reservations.csv");
            if (!filePath.exists()) {
                try {
                    filePath.createNewFile();
                } catch (IOException e) {
                    System.err.println("[ERROR] Reservation File: " + e.getMessage());
                    System.exit(1);
                }
            }

            Scanner file = new Scanner(new FileReader("Resources/Reservations.csv"));

            if(!file.hasNextLine()){
                System.err.println("[ERROR] No Data within Reservation File.");
                System.exit(1);
            }

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

                    Reservation res = new Reservation(ticketNum, date, cinemaNum, time, seats, price);
                    resList.add(res);

                } else {
                    System.err.println("Data has invalid/null value on line " + lineNum + ". Please try another file."); // To add additional if
                    System.exit(1);
                }

                lineNum++;
            }
            file.close();

        } catch (RuntimeException e) {
            System.err.println("[ERROR] A Reservation/s is invalid within the data provided." + e +
                    "\nFound at Line Number:  " + lineNum);
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
                System.err.println("[ERROR] Reservation with ticket number " + ticketNumber + " is not found.");
                tempFile.delete();
                return;
            }

        } catch (IOException e) { // Exits System Runtime after displaying message
            System.err.println("[ERROR] Deleting reservation from CSV file: " + e.getMessage());
            System.exit(1);
        }

        if (!inputFile.delete()) {
            System.out.println("Could not delete file: " + inputFile);
            return;
        }
        if (isFound && tempFile.renameTo(inputFile)) {
            System.out.println("Reservation with ticket number " + ticketNumber + " deleted successfully.");
        } else {
            System.err.println(
                    "[ERROR] Unable to update tempReservations CSV file. Cannot rename to Reservations.csv file.");
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
        try {
            File filePath = new File("Resources/Reservations.csv");
            Scanner file = new Scanner(new FileReader("Resources/Movies.csv"));

            if (!filePath.exists()) {
                filePath.createNewFile();
                System.err.println("File not Updated: ");
                System.exit(1);
            }

            PrintWriter writer = new PrintWriter(new FileWriter(filePath, true));
            while (file.hasNext()) {
                String line = file.nextLine();
                if (line.isEmpty()) {
                    writer.write(toCSVString(reservation));
                    break;

                } else {
                    writer.write("\n" + toCSVString(reservation));
                    break;
                }
            }
            file.close();
            writer.close();
        } catch (IOException e) {
            System.err.println("[ERROR] Writing reservation details to CSV file: " + e.getMessage());
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
                .append('"').append(String.join(",", reservation.getSeats())).append('"').append(",")
                .append('"').append(String.format("%.2f", reservation.getPrice())).append('"');
        return csvContent.toString();
    }
}