package dal.db.dao;


import be.Movie;
import bll.util.ConvertUtil;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import dal.db.DatabaseConnector;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.sql.*;

/**
 * This is where we access data for all movies.
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

    // This is the method to create a movie in the Database. This is also where the movie gets an ID.
    public Movie addMovie(String movieName, String publicRating, String privateRating, String fileLink) throws Exception
    {
        Connection con = DC.getConnection();
        String rating = ConvertUtil.toCombined(publicRating,privateRating);

        String sql = "INSERT INTO movie (movieName,movieRating,fileLink) VALUES (?,?,?);";
        PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.setString(1, movieName);
        ps.setString(2, rating);
        ps.setString(3, filePathToURI(fileLink));

        int affectedRows = ps.executeUpdate();
        if (affectedRows == 1)
        {
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next())
            {
                int id = rs.getInt(1);
                Movie movie = new Movie(id, movieName, publicRating, privateRating, fileLink);
                return movie;
            }

        }
        return null;
    }

    // This is the method to get all available songs in the database.
    public ObservableList<Movie> getAllMovie() throws SQLException
    {
        try(Connection connection = DC.getConnection())
        {
            String sql = "SELECT * FROM movie;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            ObservableList<Movie> allMovies = FXCollections.observableArrayList();

            while(rs.next())
            {
                int id = rs.getInt("movieID");
                String movieName = rs.getString("movieName");
                String publicRating = ConvertUtil.combinedToPublic(rs.getString("movieRating"));
                String privateRating = ConvertUtil.combinedToPersonal(rs.getString("movieRating"));
                String fileLink = rs.getString("fileLink");

                Movie movie = new Movie(id, movieName, publicRating, privateRating, fileLink);
                allMovies.add(movie);
            }

            return allMovies;
        }
    }

    public void removeMovie(Movie movie)
    {
        String sql1 = "DELETE FROM catMovie WHERE movieID = (?);";
        String sql2 = "DELETE FROM movie WHERE movieID = (?);";

        try(Connection connection = DC.getConnection())
        {
            PreparedStatement ps1 = connection.prepareStatement(sql1,Statement.RETURN_GENERATED_KEYS);
            PreparedStatement ps2 = connection.prepareStatement(sql2,Statement.RETURN_GENERATED_KEYS);

            ps1.setInt(1, movie.getMovieId());
            ps2.setInt(1, movie.getMovieId());
            ps1.executeUpdate();
            ps2.executeUpdate();

        } catch (SQLServerException throwables) {
            throwables.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void updateMovie(Movie movie)
    {

        String rating = ConvertUtil.toCombined(movie.getPublicRating(),movie.getPrivateRating());

        String sql = "UPDATE movie SET movieName= (?), movieRating=(?), fileLink=(?) WHERE movieID = (?);";
        try(Connection connection = DC.getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, movie.getMovieName());
            statement.setString(2, rating);
            statement.setString(3, filePathToURI(movie.getFileLink()));
            statement.setInt(4, movie.getMovieId());
            statement.executeUpdate();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
