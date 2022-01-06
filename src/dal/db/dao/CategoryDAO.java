package dal.db.dao;


import be.Category;

import be.Movie;

import bll.util.ConvertTime;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.DatabaseConnector;
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
    //@return Category
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
                Category category = new Category(id, name);
                return category;
            }

        }
        return null;
    }

    //returns an ObservableList with playlists from playlist table
    //@return ObservableList with playlists
    public ObservableList<Category> getAllCategory() throws Exception
    {
        try (Connection connection = DC.getConnection())
        {
            String sql = "SELECT * FROM category;";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sql);

            // new empty list to hold all playlists
            ObservableList<Category> allCategory = FXCollections.observableArrayList();

            // get all playlists from database and put them in the list of playlists
            while (rs.next())
            {
                int id = rs.getInt("categoryID");
                String name = rs.getString("categoryName");

                // save the category data in a new category object
                Category category = new Category(id, name);

                // get songs in the category and put them in the playlists songlist
                for (Movie movie : getCategory(category)) {
                    category.addSongToList(movie);
                }
                category.updatePlaylist();

                // add the complete category to the list of playlists
                allCategory.add(category);
            }

            return allCategory;
        }
    }

    //returns a single playlist with its songs
    //@param playlist
    //@return a List of songs

    public List<Movie> getCategory(Category category) throws Exception
    {
        Connection connection = DC.getConnection();
        int c_id = category.getCategoryId();

        String sql = "SELECT m.movieID, m.movieName , m.movieRating, m.movieFileLink, m.lastview FROM movie m, catMovie cm WHERE m.movieID = cm.movieID AND cm.categoryID ="+ c_id +" ORDER BY movieName;";

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

            Movie med = new Movie(id, title, artist, source, length);

            //playlistWithMovies.add(index,med);

        }
        return playlistWithMovies;
    }

    //Deletes a category
    //@param category
    public void deletePlaylist(Category category)
    {
        int pId = category.getCategoryId();

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

    //Adds a movie to a category
    //@param category
    //@Param song
    public void addToPlaylist(Category category, Movie movie) throws Exception

    {
        Connection connection = DC.getConnection();
        int pId = category.getCategoryId();
        int meId = movie.getSongId();
        int index = category.getMovieRating();

        String sql = "INSERT INTO playlistContentTable (playlistID , songID , placement) VALUES ((?), (?), (?)); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);
        pst.setInt(2, meId);
        pst.setInt(3, index);

        pst.executeUpdate();

    }

    //removes a song from a single category
    //@param category
    //@param song
    public void removeFromPlaylist(Category category, int i) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = category.getCategoryId();
        int index = i;
        System.out.println(index);

        String sql = "DELETE FROM playlistContentTable WHERE playlistID = (?) AND placement=(?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);
        pst.setInt(2, index);

        pst.executeUpdate();

    }

    //removes all songs from a single category
    //@param category
    public void clearPlaylist(Category category) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = category.getCategoryId();

        String sql = "DELETE FROM playlistContentTable WHERE playlistID = (?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);

        pst.executeUpdate();

    }

    //moves a song in a single category
    //@param category
    //@param song
    public void moveSongsInPlaylist(Category category, int i, int j) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = category.getCategoryId();
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

    //updates a single category with is new name
    //@param category
    public void updatePlaylist(Category category) throws Exception
    {
        Connection connection = DC.getConnection();

        int pId = category.getCategoryId();
        String name = category.getPlaylistName();

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
            DAO.createMovieCategoryTable();
            DAO.createMovieTable();

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

        String sql = "DELETE FROM category";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //clears playlistContentTable of all data
    public void clearPlaylistContentTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DELETE FROM catMovie";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //clears songsTable of all data
    public void clearSongsTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DELETE FROM movie";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //recreates a new playlistTable
    public void createPlaylistTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DROP TABLE category;" +
                "CREATE TABLE category (" +
                "categoryID int IDENTITY(1,1)," +
                "categoryName varchar(255)" +
                ");";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //recreates a new playlistContentTable
    public void createMovieCategoryTable() throws Exception
    {
        Connection connection = DC.getConnection();
        String sql1 = "DROP TABLE catMovie";
        String sql2 = "CREATE TABLE catMovie ( ID int IDENTITY(1,1) PRIMARY KEY, categoryID int, movieID int);";
        PreparedStatement ps1 = connection.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
        ps1.executeUpdate();
        PreparedStatement ps2 = connection.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
        ps2.executeUpdate();
    }

    //recreates a new songsTable
    public void createMovieTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DROP TABLE movie;" +
                "CREATE TABLE movie(" +
                "movieID int IDENTITY(1,1) NOT NULL," +
                "movieName varchar(255)," +
                "movieRating varchar(255)," +
                "movieFileLink varchar(255)," +
                "lastview TIMESTAMP" +
                ");";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

}
