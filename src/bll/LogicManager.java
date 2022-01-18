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
    public Movie createMovie (String name, String publicRating, String privateRating, String fileLink) throws Exception
    {
       return(movieDAO.createMovie(name, publicRating, privateRating, fileLink));
    }


    //Here we get all songs from the dal.
    public List<Movie> getAllSongs() throws SQLException
    {
        return (movieDAO.getAllMovie());
    }
    // here we delete a movie from the database.
    public void removeMovie(Movie movie)
    {
        movieDAO.deleteMovie(movie);
    }
    //here we update a movie in the database.
    public void updateSong(Movie movie)
    {
        movieDAO.updateMovie(movie);
    }

    public List<Movie> searchMovie(String query) throws Exception {
        List<Movie> allMovies = getAllSongs();
        List<Movie> searchedMovies = movieSearcher.SearchSongs(allMovies, query);
        return searchedMovies;
    }

    /**
     * Methods for CategoryDAO.
     */

    public List<Category> getAllPlaylists() throws Exception {
        return categoryDAO.getAllCategory();
    }
    public Category createPlaylist(String name) throws Exception
    {
       return categoryDAO.createCategory(name);
    }

    public List<Movie> getPlaylist (Category category) throws Exception
    {
        return categoryDAO.getCategory(category);
    }

    public void deletePlaylist(Category category)
    {
        categoryDAO.deleteCategory(category);
    }

    public void addToPlaylist(Category category, Movie movie) throws Exception

    {
        categoryDAO.addToCategory(category, movie);
    }

    //deleteCategory
    public void removeFromPlaylist (Category category, Movie movie) throws Exception
    {
        categoryDAO.removeFromCategory(category, movie);
    }


    public void clearPlaylist(Category category) throws Exception
    {
        categoryDAO.clearCategory(category);
    }
    public void updatePlaylist (Category category) throws Exception
    {
        categoryDAO.updateCategory(category);
    }
}

