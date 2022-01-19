package bll;


import be.Category;

import be.Movie;

import bll.util.MovieSearcher;
import dal.db.dao.CategoryDAO;
import dal.db.dao.MovieDAO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This is the class where we handle input from the Gui and  use methods from the dal and be, to return the right output.
 */
public class LogicManager
{
    MovieDAO movieDAO = new MovieDAO();
    CategoryDAO categoryDAO = new CategoryDAO();
    MovieSearcher movieSearcher = new MovieSearcher();

    public LogicManager() throws IOException
    {

    }

    /**
     *Methods for MovieDAO.
     */

    // here we create a song with the input from the gui, sending it to Dal.
    public Movie addMovie(String name, String publicRating, String privateRating, String fileLink) throws Exception
    {
       return(movieDAO.addMovie(name, publicRating, privateRating, fileLink));
    }

    //Here we get all songs from the dal.
    public List<Movie> getAllMovies() throws SQLException
    {
        return (movieDAO.getAllMovie());
    }
    // here we delete a movie from the database.
    public void removeMovie(Movie movie)
    {
        movieDAO.removeMovie(movie);
    }
    //here we update a movie in the database.
    public void updateMovie(Movie movie)
    {
        movieDAO.updateMovie(movie);
    }

    public List<Movie> searchMovie(String query) throws Exception {
        List<Movie> allMovies = getAllMovies();
        List<Movie> searchedMovies = movieSearcher.SearchMovies(allMovies, query);
        return searchedMovies;
    }

    /**
     * Methods for CategoryDAO.
     */

    public List<Category> getAllCategory() throws Exception {
        return categoryDAO.getAllCategory();
    }
    public Category addCategory(String name) throws Exception
    {
       return categoryDAO.addCategory(name);
    }

    public List<Movie> getCategory(Category category) throws Exception
    {
        return categoryDAO.getCategory(category);
    }

    public void removeCategory(Category category)
    {
        categoryDAO.removeCategory(category);
    }

    public void clearCategory(Category category) throws Exception
    {
        categoryDAO.clearCategory(category);
    }
    public void updateCategory(Category category) throws Exception
    {
        categoryDAO.updateCategory(category);
    }

    public void addToMoviesInCategory(Category category, Movie movie) throws Exception

    {
        categoryDAO.addToCategory(category, movie);
    }

    //deleteCategory
    public void removeMoviesInCategory(Category category, Movie movie) throws Exception
    {
        categoryDAO.removeFromCategory(category, movie);
    }



}

