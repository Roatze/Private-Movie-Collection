package gui.Controllers;


import be.Category;
import be.Movie;

import gui.Model.PrivateMovieCollectionModel;
import gui.SimpleDialog;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.security.spec.RSAOtherPrimeInfo;
import java.util.ResourceBundle;

public class PrivateMovieCollectionController implements Initializable {

    @FXML
    private TableView<Movie> tvEntertainmentTable;
    @FXML
    private TableColumn<Category, String> tcEntertainment;
    @FXML
    private TableView<Category> tvGenreTable;
    @FXML
    private TableColumn<Category, String> tcGenre;
    @FXML
    private TableView<Movie> tvMovieTable;
    @FXML
    private TableColumn<Movie, String> tcTitle;
    @FXML
    private TableColumn<Movie, Integer> tcPersonalRating;
    @FXML
    private TableColumn<Movie, Integer> tcIMDBRating;
    @FXML
    private TableColumn<Movie, String> tcTitleCat;
    @FXML
    private TableColumn<Movie, Integer> tcPersonalRatingCat;
    @FXML
    private TableColumn<Movie, Integer> tcIMDBRatingCat;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private TextField txtNowPlaying;
    @FXML
    private Button addMovie;
    @FXML
    private Button ButtonAddGenre;
    @FXML
    private Button ButtonAddMovie;
    @FXML
    private Button ButtonEditMovie;
    @FXML
    private Button ButtonEditGenre;
    @FXML
    private Button search;




    public Category selectedCategory;
    public Movie selectedMovie;
    private gui.Model.PrivateMovieCollectionModel PrivateMovieCollectionModel;
    private MovieDialogController movieDialogController;


    public PrivateMovieCollectionController() throws Exception {
        PrivateMovieCollectionModel = new PrivateMovieCollectionModel();

    }

    /**
     * Initializes MOVIE AND PLAYLIST
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTvMovieTable();
        setTcGenreTable();
        setTcEntertainmentTable();
        selectedMovie();
        selectedGenre();

        txtSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                PrivateMovieCollectionModel.searchedMovies(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void addMovie(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) ButtonAddMovie.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/AddMovie.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    /**
     * Creates the Movie Dialog window for New song and Edit song
     */
    public Stage createMovieDialog(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MovieDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        movieDialogController = fxmlLoader.getController();
        movieDialogController.setModel(PrivateMovieCollectionModel);
        return stage;
    }

    /**
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected song
     */
    public void removeMovie(ActionEvent actionEvent) throws Exception {
        if (SimpleDialog.delete() && tvMovieTable.getSelectionModel().getSelectedItem() != null) {
            PrivateMovieCollectionModel.deleteMovie(tvMovieTable.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Adds the selected Movie to the selected Genre when then button is pressed
     */
    public void addToGenre(ActionEvent actionEvent) throws Exception {
        PrivateMovieCollectionModel.addToPlaylist(tvGenreTable.getSelectionModel().getSelectedItem(), tvMovieTable.getSelectionModel().getSelectedItem());
        tvEntertainmentTable.refresh();
    }

    public void addGenre(ActionEvent actionEvent) throws Exception {
        Stage swich = (Stage) ButtonAddGenre.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/Genre.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);

    }

    /**
     * Opens the dialog to get user input for the name of the playlist, then updates the selected playlist with the new name
     */
    public void updateCategory(ActionEvent actionEvent) throws Exception {
        if (tvGenreTable.getSelectionModel().getSelectedItem() != null) {
            String name = SimpleDialog.playlist();
            Category pl = new Category(tvGenreTable.getSelectionModel().getSelectedItem().getCategoryId(), name);
            PrivateMovieCollectionModel.updatePlaylist(pl);
        }
    }

    /**
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected playlist
     */
    public void removeGenre(ActionEvent actionEvent) {
        if (SimpleDialog.delete())
            PrivateMovieCollectionModel.deletePlaylist(tvGenreTable.getSelectionModel().getSelectedItem());
            tvGenreTable.getItems().remove(tvGenreTable.getSelectionModel().getSelectedItem());
            tvGenreTable.refresh();
    }

    public void removeFromGenre(ActionEvent actionEvent) throws Exception {
        if (SimpleDialog.delete()) {
            PrivateMovieCollectionModel.removeFromPlaylist(tvGenreTable.getSelectionModel().getSelectedItem(),
                    tvEntertainmentTable.getSelectionModel().getSelectedItem(),
                    tvEntertainmentTable.getSelectionModel().getSelectedIndex());
            tvEntertainmentTable.getItems().remove(tvEntertainmentTable.getSelectionModel().getSelectedItem());
            tvEntertainmentTable.refresh();
        }
    }

    /**
     * When a playlist is clicked then songs on the playlist are shown in the middle table
     */
    public void showGenre(MouseEvent mouseEvent) {
        tvEntertainmentTable.getItems().clear();
        try {
            if (tvGenreTable.getSelectionModel().getSelectedItem() != null) {
                tvEntertainmentTable.setItems(PrivateMovieCollectionModel.getPlaylist(tvGenreTable.getSelectionModel().getSelectedItem()));
                tcTitleCat.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
                tvGenreTable.getItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearEntertainmentTableSelection(MouseEvent mouseEvent) {
        tvEntertainmentTable.getSelectionModel();
    }

    public void clearMovieTable(MouseEvent mouseEvent) {
        tvMovieTable.getSelectionModel();
    }

    public void clearGenreTableSelection(MouseEvent mouseEvent) {
        tvGenreTable.getSelectionModel();
        if (tvGenreTable.getSelectionModel().getSelectedItem() != null) {
            tvEntertainmentTable.setItems(tvGenreTable.getSelectionModel().getSelectedItem().getCategoryMovies());
        }
    }

    public void editGenre(ActionEvent actionEvent) throws IOException {
        if(selectedCategory != null) {
            Category selectedCategory = tvGenreTable.getSelectionModel().getSelectedItem();
            FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/FXML/GenreEdit.fxml"));
            Scene mainWindowScene = null;
            try {
                mainWindowScene = new Scene(parent.load());
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            Stage editGenreStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            editGenreStage.setScene(mainWindowScene);
            GenreEditController genreEditController = parent.getController();
            genreEditController.setSelectedGenre(selectedCategory);
            editGenreStage.show();
        }else{
            System.out.println("No Genre are selected");
        }
    }

    public void editMovie(ActionEvent actionEvent) throws IOException {
        if(selectedMovie != null) {
            Movie selectedMovie = tvMovieTable.getSelectionModel().getSelectedItem();
            FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/FXML/EditMovie.fxml"));
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
    public void setTcGenreTable() {
        tcGenre.setCellValueFactory(new PropertyValueFactory<Category, String>("categoryName"));
        try {
            tvGenreTable.setItems(PrivateMovieCollectionModel.getAllPlaylists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for initializing the entertainment table
     */
    public void setTcEntertainmentTable() {
        tcTitleCat.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
        tcPersonalRatingCat.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("privateRating"));
        tcIMDBRatingCat.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("publicRating"));
        try {
            if (tvGenreTable.getSelectionModel().getSelectedItem() != null) {
                tvEntertainmentTable.setItems(tvGenreTable.getSelectionModel().getSelectedItem().getCategoryMovies());
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
            tvMovieTable.setItems(PrivateMovieCollectionModel.getMovielist());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes selected Movie to the movie clicked in the tvMovieTable
     */
    private void selectedMovie() {
        this.tvMovieTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedMovie = (Movie) newValue;
            if (selectedMovie != null) {
            }
        }));
    }
    /**
     * Changes selected Genre to the movie clicked in the tvMovieTable
     */
    private void selectedGenre() {
        this.tvGenreTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            this.selectedCategory = (Category) newValue;
            if (selectedCategory != null) {
            }
        }));
    }

    /**
     * Virker ikke endnu...
     * @param mouseEvent
     */
        public void handle(MouseEvent mouseEvent) {
            if (mouseEvent.getClickCount() == 2 && selectedMovie != null) {
                this.tvMovieTable.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
                    this.selectedMovie = (Movie) newValue;
                    if (selectedMovie != null) {
                        System.out.println("yoo");
                    }
                }));
            }
        }

    public void buttonSearch(ActionEvent actionEvent) {
    //For Visuals
    }
}