package gui.Controllers;

import be.Movie;
import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
    private Button movieSaveButton;
    private PrivateMovieCollectionModel privateMovieCollectionModel;

    public EditMovieController() throws Exception {
         privateMovieCollectionModel = new PrivateMovieCollectionModel();

     }
    /**
     * Initialize the proofs.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * FileChooser for adding a Mp3 file
     * Add the new song to database.
     */
    public void chooseMP4Button() {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Music files", "*.mp4", "*.mpeg4"));
        Media f = new Media(selectedFile.toURI().toString());
        if (selectedFile != null){
            Media media = new Media(new File(selectedFile.getAbsolutePath()).toURI().toString());
            new MediaPlayer(media);
        }else {
            System.out.println("File is invalid");
        }
    }
    /**
     * Saves the newly added song.
     */
    public void movieSaveButton(ActionEvent actionEvent) throws Exception {
        String title = titleBar.getText();
        String personalRating = personalRatingBar.getText();
        String imbdRating = imdbRatingBar.getText();
        String url = fileBar.getText();
        int id = Integer.parseInt(textFieldId.getText());

        Movie movie = new Movie(id, title, personalRating, imbdRating , url);
        privateMovieCollectionModel.updateSong(movie);
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
        fileBar.setText(movie.getFileLink());
    }
}
