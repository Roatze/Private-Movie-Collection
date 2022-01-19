package gui.Controllers;


import be.Category;
import be.Movie;

import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.awt.Desktop;

import java.io.File;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;

public class PrivateMovieCollectionController implements Initializable {

    @FXML
    private TableView<Movie> tvMoviesInCategoryTable;
    @FXML
    private TableView<Category> tvCategoryTable;
    @FXML
    private TableColumn<Category, String> tcCategory;
    @FXML
    private TableView<Movie> tvMovieTable;
    @FXML
    private TableColumn<Movie, String> tcTitle;
    @FXML
    private TableColumn<Movie, Integer> tcPersonalRating;
    @FXML
    private TableColumn<Movie, Integer> tcIMDBRating;
    @FXML
    private TableColumn<Movie, String> tcTitleInCategory;
    @FXML
    private TableColumn<Movie, Integer> tcPersonalRatingInCategory;
    @FXML
    private TableColumn<Movie, Integer> tcIMDBRatingInCategory;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private Button buttonAddCategory;
    @FXML
    private Button buttonAddMovie;

    public Category selectedCategory;
    public Movie selectedCatMovie;
    public Movie selectedMovie;
    private gui.Model.PrivateMovieCollectionModel PrivateMovieCollectionModel;
    private MovieDialogController movieDialogController;


    public PrivateMovieCollectionController() throws Exception {
        PrivateMovieCollectionModel = new PrivateMovieCollectionModel();

    }

    /**
     * Initializes MOVIE AND CATEGORY
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTvMovieTable();
        setTcCategoryTable();
        setTcMoviesInCategoryTable();
        //selectedMovie();
        //selectedCatMovie();
        //selectedCategory();


        txtSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                PrivateMovieCollectionModel.searchedMovies(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        tvMoviesInCategoryTable.setOnMouseClicked((MouseEvent event) -> {
            setSelectedItems();
            if (event.getClickCount() == 2) {
                System.out.println("ur mom");
                try {
                    OpenMovie();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        tvMovieTable.setOnMouseClicked((MouseEvent event) -> {
            setSelectedItems();
        });
        tvCategoryTable.setOnMouseClicked((MouseEvent event) -> {
            setSelectedItems();
            if (selectedCategory != null) {
                tvMoviesInCategoryTable.setItems(selectedCategory.getCategoryMovies());
            }
        });


    }

    public void OpenMovie() throws IOException {
        if(selectedCatMovie != null) {
            Movie selectedMovie = selectedCatMovie;
            String fileLink = selectedMovie.getFileLink().replace("\\", "/");
            File movieFile = new File(System.getProperty("user.dir") + fileLink.replace("file:", ""));
            Desktop desktop = Desktop.getDesktop();
            if(movieFile.exists()){
                desktop.open(movieFile);
            }
        }
    }

    public void addMovie(ActionEvent actionEvent) throws IOException {
        Stage switchScene = (Stage) buttonAddMovie.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/AddMovie.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);
    }

    /**
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected song
     */
    public void removeMovie(ActionEvent actionEvent) throws Exception {
        if (SimpleDialogController.delete() && selectedMovie != null) {
            PrivateMovieCollectionModel.removeMovie(selectedMovie);
        }
    }

    /**
     * Adds the selected Movie to the selected Genre when then button is pressed
     */
    public void addMoviesInCategory(ActionEvent actionEvent) throws Exception {
        setSelectedItems();
        PrivateMovieCollectionModel.addMoviesInCategory(
                selectedCategory,
                selectedMovie);
        tvMoviesInCategoryTable.getItems().add(selectedMovie);
        tvMoviesInCategoryTable.refresh();
    }

    public void addCategory(ActionEvent actionEvent) throws Exception {
        Stage switchScene = (Stage) buttonAddCategory.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/AddCategory.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);
    }

    /**
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected category
     */
    public void removeCategory(ActionEvent actionEvent) {
        if (SimpleDialogController.delete())
            PrivateMovieCollectionModel.removeCategory(selectedCategory);
            tvCategoryTable.refresh();
    }

    public void removeFromCategory(ActionEvent actionEvent) throws Exception {
        if (SimpleDialogController.delete()) {
            PrivateMovieCollectionModel.removeMoviesInCategory(selectedCategory,
                    selectedCatMovie);
            tvMoviesInCategoryTable.getItems().remove(selectedCatMovie);
            tvMoviesInCategoryTable.refresh();
        }
    }

    public void editCategory(ActionEvent actionEvent) throws IOException {
        setSelectedItems();
        if(selectedCategory != null) {
            //Category selectedCategory = tvCategoryTable.getSelectionModel().getSelectedItem();
            FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/View/EditCategory.fxml"));
            Scene mainWindowScene = null;
            try {
                mainWindowScene = new Scene(parent.load());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            Stage editGenreStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            editGenreStage.setScene(mainWindowScene);
            EditCategoryController editCategoryController = parent.getController();
            editCategoryController.setSelectedCategory(selectedCategory);
            editGenreStage.show();
        }else{
            System.out.println("No Genre are selected");
        }
    }

    public void editMovie(ActionEvent actionEvent) throws IOException {
        setSelectedItems();
        if(selectedMovie != null) {
            //Movie selectedMovie = tvMovieTable.getSelectionModel().getSelectedItem();
            FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/View/EditMovie.fxml"));
            Scene mainWindowScene = null;
            try {
                mainWindowScene = new Scene(parent.load());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            Stage editMovieStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            editMovieStage.setScene(mainWindowScene);
            EditMovieController editMovieController = parent.getController();
            editMovieController.setSelectedMovie(selectedMovie);
            editMovieStage.show();
        }else{
            System.out.println("No movies are selected");
        }
    }

    /**
     * Method used for initializing the genre table
     */
    public void setTcCategoryTable() {
        tcCategory.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        try {
            tvCategoryTable.setItems(PrivateMovieCollectionModel.getAllCategories());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for initializing the entertainment table
     */
    public void setTcMoviesInCategoryTable() {
        tcTitleInCategory.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
        tcPersonalRatingInCategory.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("privateRating"));
        tcIMDBRatingInCategory.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("publicRating"));
        try {
            if (tvCategoryTable.getSelectionModel().getSelectedItem() != null) {
                tvMoviesInCategoryTable.setItems(selectedCategory.getCategoryMovies());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for initializing the movie table.
     */
    private void setTvMovieTable() {
        tcTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
        tcPersonalRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("privateRating"));
        tcIMDBRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("publicRating"));
        try {
            tvMovieTable.setItems(PrivateMovieCollectionModel.getMovieList());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes selected Movie to the movie clicked in the tvMovieTable
     */
    private void selectedMovie() {
        this.tvMovieTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (selectedMovie != null) {
                this.selectedMovie = (Movie) newValue;
            }
        }));
    }

    /**
     * Changes selectedCatMovie to the movie clicked in the tvMoviesInCategoryTable
     */
    private void selectedCatMovie() {
        this.tvMoviesInCategoryTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (selectedCatMovie != null) {
                this.selectedCatMovie = (Movie) newValue;
            }
        }));
    }

    /**
     * Changes selected Genre to the movie clicked in the tvMovieTable
     */
    private void selectedCategory() {
        this.tvCategoryTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            if (selectedCategory != null) {
                this.selectedCategory = (Category) newValue;
            }
        }));
    }

    /**
     * Changes selected Genre to the movie clicked in the tvMovieTable
     */
    private void setSelectedItems() {
        if (tvCategoryTable.getSelectionModel().getSelectedItem() != null)
        {
            selectedCategory = tvCategoryTable.getSelectionModel().getSelectedItem();
        }
        if (tvMoviesInCategoryTable.getSelectionModel().getSelectedItem() != null)
        {
            selectedCatMovie = tvMoviesInCategoryTable.getSelectionModel().getSelectedItem();
        }
        if (tvMovieTable.getSelectionModel().getSelectedItem() != null)
        {
            selectedMovie = tvMovieTable.getSelectionModel().getSelectedItem();
        }
    }
}