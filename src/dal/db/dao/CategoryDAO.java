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
    public Category createCategory(String name) throws Exception
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

    //returns an ObservableList with categories from category table
    //@return ObservableList with categories
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
                    category.addMovieToList(movie);
                }

                // add the complete category to the list of playlists
                allCategory.add(category);
            }

            return allCategory;
        }
    }

    //returns a single category with its movies
    //@param category
    //@return a List of movies

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

            playlistWithMovies.add(med);
        }
        return playlistWithMovies;
    }

    //Deletes a category
    //@param category
    public void deleteCategory(Category category)
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
    //@Param movie
    public void addToCategory(Category category, Movie movie) throws Exception

    {
        Connection connection = DC.getConnection();
        int cId = category.getCategoryId();
        int mId = movie.getMovieId();

        String sql = "INSERT INTO catMovie (categoryID , movieID) VALUES ((?), (?)); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, cId);
        pst.setInt(2, mId);

        pst.executeUpdate();

    }

    //removes a movie from a single category
    //@param category
    //@param movie
    public void removeFromCategory(Category category, Movie movie) throws Exception
    {
        Connection connection = DC.getConnection();
        int cId = category.getCategoryId();
        int mId = movie.getMovieId();

        String sql = "DELETE FROM catMovie WHERE categoryID = (?) AND movieID = (?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, cId);
        pst.setInt(2, mId);

        pst.executeUpdate();

    }

    //removes all movies from a single category
    //@param category
    public void clearCategory(Category category) throws Exception
    {
        Connection connection = DC.getConnection();
        int cId = category.getCategoryId();

        String sql = "DELETE FROM catMovie WHERE categoryID = (?); ";

        PreparedStatement pst = connection.prepareStatement(sql);

        pst.setInt(1, cId);

        pst.executeUpdate();

    }

    //updates a single category with is new name
    //@param category
    public void updateCategory(Category category) throws Exception
    {
        Connection connection = DC.getConnection();

        int cId = category.getCategoryId();
        String name = category.getCategoryName();

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
            //removes old tables in database
            DAO.deletePlaylistTable();
            DAO.deleteMovieCategoryTable();
            DAO.deleteMovieTable();

            //creates new tables in database
            DAO.createPlaylistTable();
            DAO.createMovieCategoryTable();
            DAO.createMovieTable();

        }
    }

    //removes old playlistTable
    public void deletePlaylistTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DROP TABLE category;";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //removes old playlistContentTable
    public void deleteMovieCategoryTable() throws Exception
    {
        Connection connection = DC.getConnection();
        String sql = "DROP TABLE catMovie";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //removes old songsTable
    public void deleteMovieTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "DROP TABLE movie;";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //creates a new playlistTable
    public void createPlaylistTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "CREATE TABLE category (" +
                "categoryID int IDENTITY(1,1)," +
                "categoryName varchar(255)" +
                ");";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //creates a new playlistContentTable
    public void createMovieCategoryTable() throws Exception
    {
        Connection connection = DC.getConnection();
        String sql = "CREATE TABLE catMovie ( ID int IDENTITY(1,1) PRIMARY KEY, categoryID int, movieID int);";
        PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ps.executeUpdate();
    }

    //creates a new songsTable
    public void createMovieTable() throws Exception
    {
        Connection connection = DC.getConnection();

        String sql = "CREATE TABLE movie(" +
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
