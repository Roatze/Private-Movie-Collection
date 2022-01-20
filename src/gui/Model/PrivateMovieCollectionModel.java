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

    /**
     * strings that will hold the information about a movie and adds them.
     * @param title
     * @param personalRating
     * @param privateRating
     * @param fileLink
     * @throws Exception
     */
    public void addMovie(String title, String personalRating, String privateRating, String fileLink) throws Exception {
        movieList.add(lm.addMovie(title, personalRating, privateRating, fileLink));
    }

    /**
     * Makes an ObservableList that will keep track of the movie list
     * @return
     */
    public ObservableList<Movie> getMovieList() {
        return movieList;
    }

    /**
     *Method for searching movies
     */
    public void searchedMovies(String query) throws Exception {
        List<Movie> searchedMovies = lm.searchMovie(query);
        movieList.clear();
        movieList.addAll(searchedMovies);
    }

    /**
     *Method for removing movies
     */
    public void removeMovie(Movie movie) throws Exception {
        lm.removeMovie(movie);
        movieList.remove(movie);
    }

    /**
     *Method for updating the movie information
     */
    public void updateMovie(Movie movie) throws SQLException {
        lm.updateMovie(movie);
        movieList.clear();
        movieList.addAll(lm.getAllMovies());
    }
    /**
     * Makes an ObservableList that will keep track of the category list
     * @return
     */
    public ObservableList<Category> getAllCategories() throws Exception {
        return CategoryList;
    }

    /**
     *Method for adding a category
     */
    public void addCategory(String genre) throws Exception {
        lm.addCategory(genre);
        CategoryList.clear();
        CategoryList.addAll(lm.getAllCategory());
    }

    /**
     * Makes an ObservableList that will keep track of the movies in category list
     * @return
     */
    public ObservableList<Movie> getMoviesInCategoryList(Category category) throws Exception {
        MoviesInCategoryList.addAll(lm.getCategory(category));
        return MoviesInCategoryList;

    }

    /**
     *Method for removing a category
     */
    public void removeCategory(Category category) {
        CategoryList.remove(category);
        lm.removeCategory(category);
    }

    /**
     *Method for adding movies to a category
     */
    public void addMoviesInCategory(Category category, Movie movie) throws Exception {
        lm.addMoviesInCategory(category, movie);
        MoviesInCategoryList.add(movie);
        updateCategory(category);
    }

    /**
     *Method for removing movies in a category
     */
    public void removeMoviesInCategory(Category category, Movie movie) throws  Exception {

        lm.removeMoviesInCategory(category, movie);
        MoviesInCategoryList.remove(movie);
        updateCategory(category);
    }

    /**
     *Method for updating the information for a category
     */
    public void updateCategory(Category category) throws  Exception {
        lm.updateCategory(category);
        CategoryList.clear();
        CategoryList.addAll(lm.getAllCategory());
    }
}


