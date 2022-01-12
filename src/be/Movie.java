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
    private IntegerProperty songId = new SimpleIntegerProperty();
    private StringProperty movieName = new SimpleStringProperty();
    private StringProperty publicRating = new SimpleStringProperty();
    private StringProperty privateRating = new SimpleStringProperty();
    private String fileLink;
    private Timestamp lastview;

    public Movie(IntegerProperty songId, StringProperty movieName, StringProperty publicRating, StringProperty privateRating, String fileLink, Timestamp lastview) {
        this.songId = songId;
        this.movieName = movieName;
        this.publicRating = publicRating;
        this.privateRating = privateRating;
        this.fileLink = fileLink;
        this.lastview = lastview;
    }

    public Movie(int songID, String movieName, String publicRating, String privateRating, String fileLink)
    {
        this.songId.set(songID);
        this.movieName.set(movieName);
        this.publicRating.set(publicRating);
        this.privateRating.set(privateRating);
        this.fileLink = fileLink;
    }

    public String getMovieName() {
        return movieName.get();
    }

    public int getMovieId() {
        return songId.get();
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

}
