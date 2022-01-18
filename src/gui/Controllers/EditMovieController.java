package gui.Controllers;

import be.Category;
import be.Movie;
import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EditMovieController implements Initializable {

    @FXML
    private TextField titleBar;
    @FXML
    private TextField personalRatingBar;
    @FXML
    private TextField imdbRatingBar;
    @FXML
    private TextField fileBar;
    @FXML
    private TextField textFieldId;
    @FXML
    private Button returnMainMenu;
    @FXML
    private Button movieSaveButton;
    private PrivateMovieCollectionModel privateMovieCollectionModel;
    private Movie selectedMovie;

    public EditMovieController() throws Exception {
         privateMovieCollectionModel = new PrivateMovieCollectionModel();

     }
    /**
     * Initialize the proofs.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


     /* Saves the newly added song.
     */
    public void movieSaveButton(ActionEvent actionEvent) throws Exception {
        String title = titleBar.getText();
        String personalRating = personalRatingBar.getText();
        String imbdRating = imdbRatingBar.getText();
        String url = fileBar.getText();
        int id = Integer.parseInt(textFieldId.getText());

        Movie movie = new Movie(id, title, personalRating, imbdRating , url);
        privateMovieCollectionModel.updateMovie(movie);
        cancelButton(actionEvent);
    }

    /**
     * Pressing the cancel button takes you back to the main window.
     */
    public void cancelButton(ActionEvent actionEvent) {
        FXMLLoader parent = new FXMLLoader(getClass().getResource("/gui/view/PrivateMovieCollection.fxml"));
        Scene mainWindowScene = null;
        try{
            mainWindowScene = new Scene(parent.load());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Stage editMovieStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        editMovieStage.setScene(mainWindowScene);
    }

    /**
     * Gets the values of the selected song.
     */
    public void setSelectedMovie(Movie movie) {
        titleBar.setText(movie.getMovieName());
        personalRatingBar.setText(String.valueOf(movie.getPrivateRating()));
        imdbRatingBar.setText(String.valueOf(movie.getPublicRating()));
        //fileBar.setText(movie.getFileLink().replaceAll("file:/", ""));
        this.selectedMovie = movie;
    }

    public void goReturnMainMenu(ActionEvent actionEvent) throws IOException {
            Stage swich = (Stage) returnMainMenu.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
            Scene scene = new Scene(parent);
            swich.setScene(scene);
    }

    public void getMovieInfo(ActionEvent actionEvent) throws SQLException, IOException {
        String updateMovieName = titleBar.getText();
        String updatePublicRating = imdbRatingBar.getText();
        String updatePrivateRating = personalRatingBar.getText();
        String updateFileLink = fileBar.getText();
        int id = Integer.parseInt(textFieldId.getText());
        this.selectedMovie.setMovieName(updateMovieName);
        this.selectedMovie.setPrivateRating(updatePrivateRating);
        this.selectedMovie.setPublicRating(updatePublicRating);
        this.selectedMovie.setFileLink(updateFileLink);

        Movie movie = new Movie(id, updateMovieName, updatePublicRating, updatePrivateRating , updateFileLink);
        privateMovieCollectionModel.updateMovie(movie);

        goReturnMainMenu(actionEvent);
    }


    public void chooseFile(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/dal/db/MovieFiles"));
        File selectedFile1 = fileChooser.showOpenDialog(null);
        fileChooser.setTitle("Select Movie");
        fileBar.appendText("src/dal/db/MovieFiles" + selectedFile1.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            //insert data from file
            fileBar.setText(selectedFile.getAbsolutePath());
        }
    }
}
