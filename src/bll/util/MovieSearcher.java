package bll.util;

import be.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles searching for Movies through a list of Movies - mainly for All Movies.
 */
public class MovieSearcher {
    public List<Movie> SearchMovies(List<Movie> searchBase, String searchQuery) throws Exception {
        ArrayList<Movie> searchedMovies = new ArrayList<>();
        for (Movie movie : searchBase) {
            if (compareToPublicRating(searchQuery, movie)
                    || compareToPrivateRating(searchQuery, movie)
                    || compareToMovieName(searchQuery, movie)){
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }

    /**
     * Compares input to the Public Rating
     * @return true if a match is found
     */
    private boolean compareToPublicRating(String query, Movie movie)
    {
        return movie.getPublicRating().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Compares input to the private rating
     * @return true if a match is found
     */
    private boolean compareToPrivateRating(String query, Movie movie)
    {
        return movie.getPrivateRating().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Compares input to movie titles
     * @return true if a match is found
     */
    private boolean compareToMovieName(String query, Movie movie)
    {
        return movie.getMovieName().toLowerCase().contains(query.toLowerCase());
    }
}
