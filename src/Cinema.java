import java.util.ArrayList;

public class Cinema {
    private int cinemaNum;
    private ArrayList<Movie> movies;


    
    public Cinema(int cinemaNum) {
        this.cinemaNum = cinemaNum;
    }


    public int getCinemaNum() {
        return this.cinemaNum;
    }

    public void setCinemaNum(int cinemaNum) {
        this.cinemaNum = cinemaNum;
    }

    public ArrayList<Movie> getMovies() {
        return this.movies;
    }

    public void setMovies(ArrayList<Movie> movies) {
        this.movies = movies;
    }

}
