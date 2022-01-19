package gui.Model;


import be.Category;
import be.Movie;

import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.List;

/**
 * In this class we acces the bll, to handle the connection from gui to bll.
 */
public class PrivateMovieCollectionModel
{

    private ObservableList<Movie> movielist;
    private ObservableList<Category> playlistlist;
    private ObservableList<Movie> playlistWithMovies;
    private LogicManager lm;

    public PrivateMovieCollectionModel() throws Exception{
        lm = new LogicManager();
        movielist = FXCollections.observableArrayList();
        movielist.addAll(lm.getAllMovies());
        playlistlist = FXCollections.observableArrayList();
        playlistlist.addAll(lm.getAllCategory());
        playlistWithMovies = FXCollections.observableArrayList();
    }

    public void createMovie(String name, String artistName, String filePath, String songLength) throws Exception {
        movielist.add(lm.addMovie(name, artistName, filePath, songLength));
    }

    public ObservableList<Movie> getMovielist() {
        return movielist;
    }

    public void searchedMovies(String query) throws Exception {
        List<Movie> searchedMovies = lm.searchMovie(query);
        movielist.clear();
        movielist.addAll(searchedMovies);
    }

    public void deleteMovie(Movie movie) throws Exception {
        lm.removeMovie(movie);
        movielist.remove(movie);
    }

    public void updateMovie(Movie movie) throws SQLException {
        lm.updateMovie(movie);
        movielist.clear();
        movielist.addAll(lm.getAllMovies());
    }

    public ObservableList<Category> getAllPlaylists() throws Exception {
        return playlistlist;
    }

    public void createGenre (String genre) throws Exception {
        lm.addCategory(genre);
        playlistlist.clear();
        playlistlist.addAll(lm.getAllCategory());
    }


    public ObservableList<Movie> getPlaylist (Category category) throws Exception {
        playlistWithMovies.addAll(lm.getCategory(category));
        return playlistWithMovies;

    }

    public void deletePlaylist (Category category) {
        playlistlist.remove(category);
        lm.removeCategory(category);
    }


    public void addToPlaylist(Category category, Movie movie) throws Exception {
        lm.addToMoviesInCategory(category, movie);
        playlistWithMovies.add(movie);
        updatePlaylist(category);
    }

    public void removeFromPlaylist(Category category, Movie movie) throws  Exception {

        lm.removeMoviesInCategory(category, movie);
        playlistWithMovies.remove(movie);
        updatePlaylist(category);
    }

    public void clearPlaylist(Category category) throws Exception {
        lm.clearCategory(category);
        playlistWithMovies.clear();
    }

    public void updatePlaylist(Category category) throws  Exception {
        lm.updateCategory(category);
        playlistlist.clear();
        playlistlist.addAll(lm.getAllCategory());
    }
}


