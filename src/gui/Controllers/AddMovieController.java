package gui.Controllers;

import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;


public class AddMovieController {

    //here we call the Button/Textfield from FXML that we use
    @FXML
    private Button cancel;

    @FXML
    private TextField titleBar;

    @FXML
    private TextField personalRatingBar;

    @FXML
    private TextField imdbRatingBar;

    @FXML
    private TextField fileBar;

    AddMovieController addMovieController;

    PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

    public AddMovieController() throws Exception {
    }

    /**
     * Initialize metode, der instancierer MovieModel, men også sætter kategorierne i vores comboBox
     */
    public void initialize() throws Exception {

        addMovieController = new AddMovieController();
    }

    /**
     * her uploades vores data til vores model layer, dataen får den fra LogicManger.
     *
     * @param title
     * @param personalRating
     * @param privateRating
     * @throws IOException
     * @throws SQLException
     */
    public void uploadMovieInfo(String title, String personalRating, String privateRating) throws Exception {
        PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

        privateMovieCollectionModel.addMovie(title, personalRating, privateRating, fileBar.getText());
    }

    /**
     * Her har vi vores cancel knap, den tager en tilbage til vores main window
     * @param actionEvent
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    /**
     * her bliver vores film info læst og sendt videre til upload metoden ovenfor.
     *
     * @throws IOException
     * @throws SQLException
     */
    public void save(ActionEvent actionEvent) throws Exception {
        String uploadTitle = titleBar.getText();
        String uploadPersonalRating = personalRatingBar.getText();
        String uploadImdbRaring = imdbRatingBar.getText();
        uploadMovieInfo(uploadTitle, uploadPersonalRating, uploadImdbRaring);

        Stage switchScene = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);
    }

    /**
     * Her åbner vi en FileChooser og finder stien/fil-link til vores film
     * @param actionEvent
     */
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
