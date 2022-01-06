package dal.db.dao;


import be.Movie;
import bll.util.ConvertTime;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import dal.db.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

/**
 * This is where we access data for all songs.
 */
public class MovieDAO {

    private DatabaseConnector DC;

    public MovieDAO() throws IOException
    {
        DC = new DatabaseConnector();
    }

    public String filePathToURI(String filePath)
    {
        return "file:/" + filePath.replace("\\", "/");
    }

    // This is the method to create a song in the Database. This is also where the song gets an ID.
    public Movie createSong(String songName, String artist, String filePath, String songLength) throws Exception
    {
        Connection con = DC.getConnection();

        String sql = "INSERT INTO songsTable (songName,artist,filePath,songLength) VALUES (?,?,?,?);";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, songName);
        ps.setString(2, artist);
        ps.setString(3, filePathToURI(filePath));
        ps.setInt(4, ConvertTime.timeToSec(songLength));

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                Movie movie = new Movie(id, songName, artist, filePath, songLength);
                return movie;
            }

        }
        return null;
    }

    // This is the method to get all available songs in the database.
    public ObservableList<Movie> getAllSongs() throws SQLException
    {
        try(Connection connection = DC.getConnection())
        {
            String sql = "SELECT * FROM songsTable;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            ObservableList<Movie> allMovies = FXCollections.observableArrayList();

            while(rs.next())
            {
                int songÍD = rs.getInt("songID");
                String songName = rs.getString("songName");
                String artistName = rs.getString("artist");
                String filePath = rs.getString("filePath");
                String songLength = ConvertTime.secToTime(rs.getInt("songLength"));

                Movie movie = new Movie(songÍD, songName,artistName,filePath,songLength);
                allMovies.add(movie);
            }

            return allMovies;
        }
    }

    public void deleteSong(Movie movie)
    {
        String sql1 = "DELETE FROM playlistContentTable WHERE songID = (?);";
        String sql2 = "DELETE FROM songsTable WHERE songID = (?);";

        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps1 = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);

            ps1.setInt(1, movie.getSongId());
            ps2.setInt(1, movie.getSongId());
            ps1.executeUpdate();
            ps2.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateSong(Movie movie)
    {

        String sql = "UPDATE songsTable SET songName= (?), artist=(?), songLength=(?), filePath=(?) WHERE songID = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movie.getName());
            statement.setString(2, movie.getArtistName());
            statement.setInt(3,ConvertTime.timeToSec(movie.getSongLength()));
            statement.setString(4, filePathToURI(movie.getFilePath()));
            statement.setInt(5, movie.getSongId());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
