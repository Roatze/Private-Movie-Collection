package be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Here we describe the Category class, and what it can do.
 */
public class Category
{
    private StringProperty categoryName = new SimpleStringProperty();
    private List<Movie> listOfMovies = new ArrayList();
    private int categoryId;

    public Category(int playlistID, String playlistName)
    {
        this.categoryId = playlistID;
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

    public int getCategoryId() {
        return categoryId;
    }

}
