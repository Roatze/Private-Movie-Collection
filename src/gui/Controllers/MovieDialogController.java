package gui.Controllers;

import be.Movie;
import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Handles the Movie dialog window
 */
public class MovieDialogController implements Initializable {

    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtPath;

    private int movieID;
    private boolean edit;
    private PrivateMovieCollectionModel privateMovieCollectionModel;


    public MovieDialogController() throws Exception {
        movieID = 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        txtTitle.setText("");
        txtArtist.setText("");
        txtTime.setText("");
        txtPath.setText("");
        edit = false;
    }

    /**
     * Closes the Movie window when the Cancel button is clicked.
     */
    public void cancel(ActionEvent actionEvent) {
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }

    /**
     * When the Save button is clicked the user inputs are sent on to either create a new movie or update the info of
     * an already existing movie, depending on which of the "New" or "Edit" button was clicked in the main view.
     */
    public void save(ActionEvent actionEvent) throws Exception {
        if (!edit) {
            privateMovieCollectionModel.addMovie(txtTitle.getText(), txtArtist.getText(), txtPath.getText(), txtTime.getText());

        } else {
            Movie movie = new Movie(movieID, txtTitle.getText(), txtArtist.getText(), txtPath.getText(), txtTime.getText());
            privateMovieCollectionModel.updateMovie(movie);
        }
        ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void setModel(PrivateMovieCollectionModel model) {
        privateMovieCollectionModel = model;
    }
}