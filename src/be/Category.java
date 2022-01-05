package be;

import bll.util.ConvertTime;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we describe the Playlist class, and what it can do.
 */
public class Category
{
    private StringProperty categoryName = new SimpleStringProperty();
    private List<Movie> listOfMovies = new ArrayList();
    private IntegerProperty playlistSongCount = new SimpleIntegerProperty();
    private StringProperty playlistTimelength = new SimpleStringProperty();
    private int playlistId;

    public Category(int playlistID, String playlistName)
    {
        this.playlistId = playlistID;
        this.categoryName.setValue(playlistName);

    }
    /*
    Beneath we have getters for all the properties of the class, and a single setter for the playlist name.
     */
    /*
    Here we want to return the list of songs
     */

    public void addSongToList(Movie movie)
    {
        listOfMovies.add(movie);
    }

    public String getPlaylistName() {
        return categoryName.get();
    }

    //is being used by a PropertyValueFactory in MTC.
    public String getPlaylistTimelength() {
        return playlistTimelength.get();
    }

    public int getPlaylistSongCount() {
        return playlistSongCount.get();
    }

    public int getPlaylistId() {
        return playlistId;
    }

    private void updatePlaylistSongCount()
    {
        int i = 0;
        for (Movie movie: listOfMovies) {
            if (movie != null)
            {
                i++;
            }
        }
        this.playlistSongCount.set(i);
    }

    private void updatePlaylistTimeLength()
    {
        List<String> times = new ArrayList<>();
        for (Movie movie: listOfMovies) {
            if (movie != null)
            {
                times.add(movie.getSongLength());
            }
        }
        this.playlistTimelength.set(ConvertTime.sumTime(times));
    }

    public void updatePlaylist(){ updatePlaylistTimeLength(); updatePlaylistSongCount(); }
}
