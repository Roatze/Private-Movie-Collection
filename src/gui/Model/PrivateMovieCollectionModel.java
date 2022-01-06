package gui.Model;


import be.Category;
import be.Movie;

import bll.LogicManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.TimerTask;

/**
 * In this class we acces the bll, to handle the connection from gui to bll.
 */
public class PrivateMovieCollectionModel
{


    private ObservableList<Movie> songlist;
    private ObservableList<Category> playlistlist;
    private ObservableList<Movie> playlistWithMovies;
    private LogicManager lm;

    public PrivateMovieCollectionModel() throws Exception{
        lm = new LogicManager();
        songlist= FXCollections.observableArrayList();
        songlist.addAll(lm.getAllSongs());
        playlistlist = FXCollections.observableArrayList();
        playlistlist.addAll(lm.getAllPlaylists());
        playlistWithMovies = FXCollections.observableArrayList();
    }

    public void playSong(Movie movie) {
        lm.playSong(movie);
    }

    public void stopPlaying() {
        lm.stopPlaying();
    }

    public boolean isPlaying() {
        return lm.isPlaying();
    }

    public void nextSong(Movie movie){
        lm.nextSong(movie);}

    public void previousSong(Movie movie) {
        lm.previousSong(movie);
    }

    public void createSong(String name, String artistName, String filePath, String songLength) throws Exception {
        songlist.add(lm.createSong(name, artistName, filePath, songLength));
    }

    public ObservableList<Movie> getSonglist() {
        return songlist;
    }

    public void searchSongs(String query) throws Exception {
        List<Movie> searchedMovies = lm.searchSongs(query);
        songlist.clear();
        songlist.addAll(searchedMovies);
    }

    public void deleteSong(Movie movie) throws Exception {
        lm.deleteSong(movie);
        songlist.remove(movie);
    }

    public void updateSong(Movie movie) throws SQLException {
        lm.updateSong(movie);
        songlist.clear();
        songlist.addAll(lm.getAllSongs());
    }

    public ObservableList<Category> getAllPlaylists() throws Exception {
        return playlistlist;
    }

    public void createPlaylist (String name) throws Exception {
        lm.createPlaylist(name);
        playlistlist.clear();
        playlistlist.addAll(lm.getAllPlaylists());
    }


    public ObservableList<Movie> getPlaylist (Category category) throws Exception {
        playlistWithMovies.addAll(lm.getPlaylist(category));
        return playlistWithMovies;

    }

    public void deletePlaylist (Category category) {
        playlistlist.remove(category);
        lm.deletePlaylist(category);
    }


    public void addToPlaylist(Category category, Movie movie) throws Exception {
        lm.addToPlaylist(category, movie);
        playlistWithMovies.add(movie);
        updatePlaylist(category);
    }

    public void removeFromPlaylist(Category category, Movie movie, int i) throws  Exception {

        lm.removeFromPlaylist(category, i);
        playlistWithMovies.remove(movie);
        updatePlaylist(category);
    }

    public void clearPlaylist(Category category) throws Exception {
        lm.clearPlaylist(category);
        playlistWithMovies.clear();
    }

    public void updatePlaylist(Category category) throws  Exception {
        lm.updatePlaylist(category);
        playlistlist.clear();
        playlistlist.addAll(lm.getAllPlaylists());
    }


    public void swapSongsInPlaylist(Category category, int i, int j) throws Exception {
        Collections.swap(playlistWithMovies, i, j);

        lm.swapSongsInPlaylist(category, i, j);
    }

    public void timer(TimerTask t) {
        lm.timer(t);
    }

    public boolean isSongFinished() {
        return lm.isSongFinished();
    }

    public void setSongFinished(boolean b) {
        lm.setSongFinished(b);
    }

    public void setVolume(double volume)
    {
        lm.setVolume(volume);
    }
}


