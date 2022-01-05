package dal;


import be.Category;

import be.Movie;

import bll.util.ConvertTime;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO {

    private DatabaseConnector DC;

    public CategoryDAO() throws IOException
    {
        DC = new DatabaseConnector();
    }

    //creates a new playlist
    //@param name
    //@return Playlist
    public Category createPlaylist(String name) throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "INSERT INTO playlistTable (playlistName) VALUES (?);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, name);
        int affectedRows = ps.executeUpdate();
        if (affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                Category playlist = new Category(id, name);
                return playlist;
            }

        }
        return null;
    }

    //returns an ObservableList with playlists from playlist table
    //@return ObservableList with playlists
    public ObservableList<Category> getAllPlaylist() throws Exception
    {
        try (Connection connection = DC.getConnection())
        {
            String sql = "SELECT * FROM playlistTable;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // new empty list to hold all playlists
            ObservableList<Category> allPlaylist = FXCollections.observableArrayList();

            // get all playlists from database and put them in the list of playlists
            while (rs.next())
            {
                int id = rs.getInt("playlistID");
                String name = rs.getString("playlistName");

                // save the playlist data in a new playlist object
                Category playlist = new Category(id, name);

                // get songs in the playlist and put them in the playlists songlist
                for (Movie movie : getPlaylist(playlist)) {
                    playlist.addSongToList(movie);
                }
                playlist.updatePlaylist();

                // add the complete playlist to the list of playlists
                allPlaylist.add(playlist);
            }

            return allPlaylist;
        }
    }

    //returns a single playlist with its songs
    //@param playlist
    //@return a List of songs

    public List<Movie> getPlaylist(Category playlist) throws Exception
    {
        Connection connection = DC.getConnection();
        int p_id = playlist.getPlaylistId();

        String sql = "SELECT s.songID, s.songName , s.artist, s.filePath, s.songLength, pc.placement FROM songsTable s, playlistContentTable pc WHERE s.songID = pc.songID AND pc.playlistID ="+ p_id +" ORDER BY placement;";

        Statement ps = connection.createStatement();
        ResultSet rs = ps.executeQuery(sql);
        ArrayList<Movie> playlistWithMovies = new ArrayList<>();
        while (rs.next())
        {
            int id = rs.getInt("songID");
            String title = rs.getString("songName");
            String artist = rs.getString("artist");
            String source = rs.getString("filePath");
            String length = ConvertTime.secToTime(rs.getInt("songLength"));
            int index = rs.getInt("placement");

            Movie med = new Movie(id, title, artist, source, length);

            playlistWithMovies.add(index,med);

        }
        return playlistWithMovies;
    }

    //Deletes a playlist
    //@param playlist
    public void deletePlaylist(Category playlist)
    {
        int pId = playlist.getPlaylistId();

        String sql1 = "DELETE FROM playlistContentTable WHERE playlistID = (?); ";
        String sql2 = "DELETE FROM playlistTable WHERE playlistID=(?);";

        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps1 = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);

            ps1.setInt(1, pId);
            ps2.setInt(1, pId);
            ps1.executeUpdate();
            ps2.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    //Adds a movie to a playlist
    //@param playlist
    //@Param song
    public void addToPlaylist(Category playlist, Movie movie) throws Exception

    {
        Connection connection = DC.getConnection();
        int pId = playlist.getPlaylistId();
        int meId = movie.getSongId();
        int index = playlist.getPlaylistSongCount();

        String sql = "INSERT INTO playlistContentTable (playlistID , songID , placement) VALUES ((?), (?), (?)); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);
        pst.setInt(2, meId);
        pst.setInt(3, index);

        pst.executeUpdate();

    }

    //removes a song from a single playlist
    //@param playlist
    //@param song
    public void removeFromPlaylist(Category playlist, int i) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = playlist.getPlaylistId();
        int index = i;
        System.out.println(index);

        String sql = "DELETE FROM playlistContentTable WHERE playlistID = (?) AND placement=(?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);
        pst.setInt(2, index);

        pst.executeUpdate();

    }

    //removes all songs from a single playlist
    //@param playlist
    public void clearPlaylist(Category playlist) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = playlist.getPlaylistId();

        String sql = "DELETE FROM playlistContentTable WHERE playlistID = (?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);

        pst.executeUpdate();

    }

    //moves a song in a single playlist
    //@param playlist
    //@param song
    public void moveSongsInPlaylist(Category playlist, int i, int j) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = playlist.getPlaylistId();
        int index1 = i;
        int index2 = j;

        String sql1 = "Update playlistContentTable SET placement = -1 WHERE playlistID = (?) AND placement=(?); ";
        String sql2 = "Update playlistContentTable SET placement = (?) WHERE playlistID = (?) AND placement=(?); ";
        String sql3 = "Update playlistContentTable SET placement = (?) WHERE playlistID = (?) AND placement=-1; ";

        PreparedStatement pst1 = connection.prepareStatement(sql1);
        PreparedStatement pst2 = connection.prepareStatement(sql2);
        PreparedStatement pst3 = connection.prepareStatement(sql3);

        //Fancy sql magic to switch placement of two songs
        pst1.setInt(1, pId);
        pst1.setInt(2, index2);

        pst2.setInt(1, index2);
        pst2.setInt(2, pId);
        pst2.setInt(3, index1);

        pst3.setInt(1, index1);
        pst3.setInt(2, pId);

        pst1.executeUpdate();
        pst2.executeUpdate();
        pst3.executeUpdate();

    }

    //updates a single playlist with is new name
    //@param playlist
    public void updatePlaylist(Category playlist) throws Exception
    {
        Connection connection = DC.getConnection();

        int pId = playlist.getPlaylistId();
        String name = playlist.getPlaylistName();

        String sql = "UPDATE playlistTable SET playlistName = (?) WHERE playlistID = (?);";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, name);
        pst.setInt(2, pId);

        pst.executeUpdate();

    }

    //test Main to test database stuff
    public static void main(String[] args) throws Exception {
        CategoryDAO DAO = new CategoryDAO();

        if (true == true) //Set to true to run
        {
            //(re)creates new tables in database
            DAO.createPlaylistTable();
            DAO.createPlaylistContentTable();
            DAO.createSongsTable();

            //clears all data in tables
            //DAO.clearPlaylistTable();
            //DAO.clearPlaylistContentTable();
            //DAO.clearSongsTable();
        }
    }

    //clears playlistTable of all data
    public void clearPlaylistTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DELETE FROM playlistTable";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //clears playlistContentTable of all data
    public void clearPlaylistContentTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DELETE FROM playlistContentTable";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //clears songsTable of all data
    public void clearSongsTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DELETE FROM songsTable";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //recreates a new playlistTable
    public void createPlaylistTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DROP TABLE category;" +
                "CREATE TABLE category (" +
                "playlistName varchar(255)" +
                ");";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //recreates a new playlistContentTable
    public void createPlaylistContentTable() throws Exception
    {
        Connection connection = DC.getConnection();
        String sql1 = "DROP TABLE catMovie";
        String sql2 = "CREATE TABLE catMovie ( ID int IDENTITY(1,1) PRIMARY KEY, playlistID int, songID int);";
        PreparedStatement ps1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
        ps1.executeUpdate();
        PreparedStatement ps2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
        ps2.executeUpdate();
    }

    //recreates a new songsTable
    public void createSongsTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DROP TABLE movie;" +
                "CREATE TABLE movie(" +
                "id int IDENTITY(1,1) NOT NULL," +
                "name varchar(255)," +
                "rating FLOAT(53)," +
                "fileLink varchar(255)," +
                "lastview TIMESTAMP" +
                ");";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

}
