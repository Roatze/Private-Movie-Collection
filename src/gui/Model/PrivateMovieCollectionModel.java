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

    private ObservableList<Movie> movieList;
    private ObservableList<Category> CategoryList;
    private ObservableList<Movie> MoviesInCategoryList;
    private LogicManager lm;

    public PrivateMovieCollectionModel() throws Exception{
        lm = new LogicManager();
        movieList = FXCollections.observableArrayList();
        movieList.addAll(lm.getAllMovies());
        CategoryList = FXCollections.observableArrayList();
        CategoryList.addAll(lm.getAllCategory());
        MoviesInCategoryList = FXCollections.observableArrayList();
    }

    public void addMovie(String name, String artistName, String filePath, String movieLength) throws Exception {
        movieList.add(lm.addMovie(name, artistName, filePath, movieLength));
    }

    public ObservableList<Movie> getMovieList() {
        return movieList;
    }

    public void searchedMovies(String query) throws Exception {
        List<Movie> searchedMovies = lm.searchMovie(query);
        movieList.clear();
        movieList.addAll(searchedMovies);
    }

    public void removeMovie(Movie movie) throws Exception {
        lm.removeMovie(movie);
        movieList.remove(movie);
    }

    public void updateMovie(Movie movie) throws SQLException {
        lm.updateMovie(movie);
        movieList.clear();
        movieList.addAll(lm.getAllMovies());
    }

    public ObservableList<Category> getAllCategories() throws Exception {
        return CategoryList;
    }

    public void addCategory(String genre) throws Exception {
        lm.addCategory(genre);
        CategoryList.clear();
        CategoryList.addAll(lm.getAllCategory());
    }


    public ObservableList<Movie> getCategory(Category category) throws Exception {
        MoviesInCategoryList.addAll(lm.getCategory(category));
        return MoviesInCategoryList;

    }

    public void removeCategory(Category category) {
        CategoryList.remove(category);
        lm.removeCategory(category);
    }


    public void addMoviesInCategory(Category category, Movie movie) throws Exception {
        lm.addMoviesInCategory(category, movie);
        MoviesInCategoryList.add(movie);
        updateCategory(category);
    }

    public void removeMoviesInCategory(Category category, Movie movie) throws  Exception {

        lm.removeMoviesInCategory(category, movie);
        MoviesInCategoryList.remove(movie);
        updateCategory(category);
    }

    public void clearCategory(Category category) throws Exception {
        lm.clearCategory(category);
        MoviesInCategoryList.clear();
    }

    public void updateCategory(Category category) throws  Exception {
        lm.updateCategory(category);
        CategoryList.clear();
        CategoryList.addAll(lm.getAllCategory());
    }
}


