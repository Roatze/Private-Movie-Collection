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
    private StringProperty name = new SimpleStringProperty();
    private StringProperty artistName = new SimpleStringProperty();
    private StringProperty songLength = new SimpleStringProperty();
    private IntegerProperty songId = new SimpleIntegerProperty();
    private String filePath;
    private Timestamp lastview;

    public Movie(int songID, String name, String artistName, String filePath, String songLength)
    {
        this.songId.set(songID);
        this.name.set(name);
        this.artistName.set(artistName);
        this.filePath = filePath;
        this.songLength.set(songLength);
    }

    /*
    Beneath we have getters and setters for the different properties of the class,
    we don't have a setter for ID since we want our database to handle the ID.
     */
    public String getName() {
        return name.get();
    }


    public int getSongId() {
        return songId.get();
    }

    public String getArtistName() {
        return artistName.get();
    }




    public String getSongLength() {
        return songLength.get();
    }



    public String getFilePath() {
        return filePath;
    }


}
