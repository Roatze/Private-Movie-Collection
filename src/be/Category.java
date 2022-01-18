package be;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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

    public Category(String updatePlaylistName) {
    }

    public void addMovieToList(Movie movie)
    {
        listOfMovies.add(movie);
    }
    public void removeMovieFromList(Movie movie)
    {
        listOfMovies.remove(movie);
    }

    public String getCategoryName() {
        return categoryName.get();
    }
    public void setCategoryName(String name) { categoryName.set(name); }

    public int getCategoryId() {
        return categoryId;
    }

    public ObservableList<Movie> getCategoryMovies() {
        ObservableList<Movie> temp = FXCollections.observableArrayList(listOfMovies);
        return temp;
    }
}
