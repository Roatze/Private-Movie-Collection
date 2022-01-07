package dal.db.dao;


import be.Category;

import be.Movie;

import bll.util.ConvertUtil;
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

        String sql = "INSERT INTO category (categoryName) VALUES (?);";
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

        String sql = "SELECT m.movieID, m.movieName , m.movieRating, m.fileLink, m.lastview FROM movie m, catMovie cm WHERE m.movieID = cm.movieID AND cm.categoryID ="+ c_id +" ORDER BY movieName;";

        Statement ps = connection.createStatement();
        ResultSet rs = ps.executeQuery(sql);
        ArrayList<Movie> playlistWithMovies = new ArrayList<>();
        while (rs.next())
        {
            int id = rs.getInt("movieID");
            String name = rs.getString("movieName");
            String publicRating = ConvertUtil.combinedToPublic(rs.getString("movieRating"));
            String privateRating = ConvertUtil.combinedToPersonal(rs.getString("movieRating"));
            String source = rs.getString("fileLink");
            String lastview = rs.getString("lastview");

            Movie med = new Movie(id, name, publicRating, privateRating, source);

            //playlistWithMovies.add(index,med);

        }
        return playlistWithMovies;
    }

    //Deletes a category
    //@param category
    public void deletePlaylist(Category category)
    {
        int pId = category.getCategoryId();

        String sql1 = "DELETE FROM catMovie WHERE categoryID = (?);";
        String sql2 = "DELETE FROM category WHERE categoryID = (?);";

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
        int cId = category.getCategoryId();
        int mId = movie.getSongId();

        String sql = "INSERT INTO catMovie (categoryID , movieID) VALUES ((?), (?)); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, cId);
        pst.setInt(2, mId);

        pst.executeUpdate();

    }

    //removes a song from a single category
    //@param category
    //@param song
    public void removeFromPlaylist(Category category, Movie movie) throws Exception
    {
        Connection connection = DC.getConnection();
        int cId = category.getCategoryId();
        int mId = movie.getSongId();

        String sql = "DELETE FROM catMovie WHERE categoryID = (?) AND movieID = (?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, cId);
        pst.setInt(2, mId);

        pst.executeUpdate();

    }

    //removes all songs from a single category
    //@param category
    public void clearPlaylist(Category category) throws Exception
    {
        Connection connection = DC.getConnection();
        int pId = category.getCategoryId();

        String sql = "DELETE FROM catMovie WHERE categoryID = (?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, pId);

        pst.executeUpdate();

    }

    //updates a single category with is new name
    //@param category
    public void updatePlaylist(Category category) throws Exception
    {
        Connection connection = DC.getConnection();

        int cId = category.getCategoryId();
        String name = category.getPlaylistName();

        String sql = "UPDATE category SET categoryName = (?) WHERE categoryID = (?);";

        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, name);
        pst.setInt(2, cId);

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

        }
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
                "fileLink varchar(255)," +
                "lastview TIMESTAMP" +
                ");";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

}
