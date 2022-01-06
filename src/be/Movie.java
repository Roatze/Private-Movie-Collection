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

    public Movie(int songID, String movieName, String publicRating, String fileLink, String privateRating)
    {
        this.songId.set(songID);
        this.movieName.set(movieName);
        this.publicRating.set(publicRating);
        this.privateRating.set(privateRating);
        this.fileLink = fileLink;
    }

    /*
    Beneath we have getters and setters for the different properties of the class,
    we don't have a setter for ID since we want our database to handle the ID.
     */
    public String getMovieName() {
        return movieName.get();
    }


    public int getSongId() {
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
