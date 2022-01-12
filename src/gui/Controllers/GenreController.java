package gui.Controllers;

import be.Category;
import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.microsoft.sqlserver.jdbc.SQLServerException;
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

public class GenreController {
    @FXML
    public Button genreCancel;
    @FXML
    public TextField newGenre;
    @FXML
    public Button genreSave;
    @FXML
    private TableView<Category> tvGenreTable;


    public void genreCancelBtn(ActionEvent event) throws IOException {
        Stage swich = (Stage) genreCancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }



    public void genreSaveBtn(ActionEvent actionEvent) throws Exception {
        String uploadTitle = genre();
        uploadGenreInfo(uploadTitle);
        Stage swich = (Stage) genreCancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);

    }

    private void uploadGenreInfo(String genre) throws Exception {
        PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

        privateMovieCollectionModel.createGenre(genre);

    }

    public String genre() {
        String genreTemp = newGenre.getText();
        return genreTemp;
    }



    public void newGenreTxt(ActionEvent event) {

    }

}
