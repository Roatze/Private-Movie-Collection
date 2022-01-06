package bll.util;


import be.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * This class handles searching for songs through a list of songs - mainly for All Songs.
 */
public class SongSearcher {
    public List<Movie> SearchSongs(List<Movie> searchBase, String searchQuery) throws Exception {
        ArrayList<Movie> searchedMovies = new ArrayList<>();
        for (Movie movie : searchBase) {
            if (compareToArtistName(searchQuery, movie)
                    || compareToSongName(searchQuery, movie)){
                searchedMovies.add(movie);
            }
        }
        return searchedMovies;
    }

    /**
     * Compares input to artist names
     * @return true if a match is found
     */
    private boolean compareToArtistName(String query, Movie movie)
    {
        return movie.getPublicRating().toLowerCase().contains(query.toLowerCase());
    }

    /**
     * Compares input to movie titles
     * @return true if a match is found
     */
    private boolean compareToSongName(String query, Movie movie)
    {
        return movie.getMovieName().toLowerCase().contains(query.toLowerCase());
    }
}
