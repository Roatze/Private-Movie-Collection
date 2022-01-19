package be;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.sql.Timestamp;

/**
 * This is the Movie class, here we specify what a Movie is.
 */
public class Movie
{
    private IntegerProperty movieId = new SimpleIntegerProperty();
    private StringProperty movieName = new SimpleStringProperty();
    private StringProperty publicRating = new SimpleStringProperty();
    private StringProperty privateRating = new SimpleStringProperty();
    private String fileLink;

    public Movie(IntegerProperty movieId, StringProperty movieName, StringProperty publicRating, StringProperty privateRating, String fileLink, Timestamp lastview) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.publicRating = publicRating;
        this.privateRating = privateRating;
        this.fileLink = fileLink;
    }

    public Movie(int movieId, String movieName, String publicRating, String privateRating, String fileLink)
    {
        this.movieId.set(movieId);
        this.movieName.set(movieName);
        this.publicRating.set(publicRating);
        this.privateRating.set(privateRating);
        this.fileLink = fileLink;
    }


    public String getMovieName() {
        return movieName.get();
    }

    public int getMovieId() {
        return movieId.get();
    }

    public String getPublicRating() {
        return publicRating.get();
    }

    public String getPrivateRating() {
        return privateRating.get();
    }

    public String getFileLink() {
        return fileLink;
    }

    public void setMovieName(String name) { movieName.set(name); }

    public void setPublicRating(String val) { this.publicRating.set(val); }

    public void setPrivateRating(String val) { this.privateRating.set(val); }

    public void setFileLink(String val) { this.fileLink = val; }

}
