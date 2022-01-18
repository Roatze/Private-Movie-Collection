package gui.Controllers;

import be.Category;
import be.Movie;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import dal.db.dao.CategoryDAO;
import gui.Model.PrivateMovieCollectionModel;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;


public class AddMovieController {


    @FXML
    public Button returnMainMenu;

    @FXML
    public TextField titleBar;

    @FXML
    public TextField personalRatngBar;

    @FXML
    public TextField imdbRaringBar;

    @FXML
    public Button movieSaveButton;

    @FXML
    public ComboBox genreMenu;

    @FXML
    public TextField fileBar;

    AddMovieController addMovieController;

    PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

    public AddMovieController() throws Exception {
    }

    /**
     * Initialize metode, der instancierer songModel, men også sætter kategorierne i vores comboBox
     */
    public void initialize() throws Exception {

        CategoryDAO categoryDAO = new CategoryDAO();

        addMovieController = new AddMovieController();


    }

    /**
     * her uploades vores data til vores model layer, dataen får den fra LogicManger.
     *
     * @param title
     * @param artist
     * @param category
     * @throws IOException
     * @throws SQLException
     */
    public void uploadMovieInfo(String title, String artist, String category) throws Exception {
        PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

        privateMovieCollectionModel.createMovie(title, artist, category, fileBar.getText());
    }


    public void goReturnMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) returnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    /**
     * her bliver songInfo læst og sendt videre til upload metoden ovenfor.
     *
     * @throws IOException
     * @throws SQLException
     */
    public void getMovieInfo(ActionEvent actionEvent) throws Exception {
        String uploadTitle = titleBar.getText();
        String uploadPersonalRatng = personalRatngBar.getText();
        String uploadImdbRaring = imdbRaringBar.getText();
        uploadMovieInfo(uploadTitle, uploadPersonalRatng, uploadImdbRaring);

        Stage swich = (Stage) returnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);

    }
    
    /*
    Error metode.
     */
    private void error(String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.YES);
        alert.showAndWait();
    }

    public void moreGenreBtn(ActionEvent event) throws IOException {
        Stage swich = (Stage) returnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/Genre.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
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
    }
}
