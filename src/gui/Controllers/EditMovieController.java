package gui.Controllers;

import be.Movie;
import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
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
    private Button cancel;
    @FXML

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

    /**
     * Gets the values of the selected movie.
     */
    public void setSelectedMovie(Movie movie) {
        titleBar.setText(movie.getMovieName());
        personalRatingBar.setText(String.valueOf(movie.getPrivateRating()));
        imdbRatingBar.setText(String.valueOf(movie.getPublicRating()));
        fileBar.setText(movie.getFileLink());
        this.selectedMovie = movie;
    }
    /**
     * Pressing the cancel button takes you back to the main window.
     */

    public void cancel(ActionEvent actionEvent) throws IOException {
            Stage switchScene = (Stage) cancel.getScene().getWindow();
            Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
            Scene scene = new Scene(parent);
            switchScene.setScene(scene);
    }
    /**
     * Pressing the save button saves your changes.
     */

    public void Save(ActionEvent actionEvent) throws SQLException, IOException {
        String updateMovieName = titleBar.getText();
        String updatePublicRating = imdbRatingBar.getText();
        String updatePrivateRating = personalRatingBar.getText();
        String updateFileLink = fileBar.getText();
        this.selectedMovie.setMovieName(updateMovieName);
        this.selectedMovie.setPrivateRating(updatePrivateRating);
        this.selectedMovie.setPublicRating(updatePublicRating);
        this.selectedMovie.setFileLink(updateFileLink);
        
        privateMovieCollectionModel.updateMovie(this.selectedMovie);

        cancel(actionEvent);
    }


    public void chooseFile(ActionEvent actionEvent) {
        fileBar.setText("");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("src/dal/db/MovieFiles/"));
        File selectedFile1 = fileChooser.showOpenDialog(null);
        fileChooser.setTitle("Select Movie");
        fileBar.appendText("src/dal/db/MovieFiles/" + selectedFile1.getName());
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Movie Files", "*.mp4", "*.mpeg4"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );
    }
}
