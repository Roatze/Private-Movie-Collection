package gui.Controllers;

import be.Category;
import be.Movie;
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

public class GenreEditController {

    @FXML
    public Button genreEditCancel;
    public TextField editGenre;

    private Category selectedGenre;

    PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

    public GenreEditController() throws Exception {
    }


    public void genreCancelEditBtn(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) genreEditCancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    /**
     * OnAction knappen der gør at vi kan edit en playlist, her udfylder den textfielded, så man ændrer på det rigtige
     * navn og playlist.
     * @param actionEvent
     * @throws IOException
     */
    public void genreSaveEditBtn(ActionEvent actionEvent) throws Exception {
        String updateGenreName = editGenre.getText();
        this.selectedGenre.setCategoryName(updateGenreName);
        privateMovieCollectionModel.updatePlaylist(this.selectedGenre);

        genreCancelEditBtn(actionEvent);
    }

    public void setSelectedGenre(Category genre) {
        this.selectedGenre = genre;
        editGenre.setText(genre.getCategoryName());
    }

    public void newGenreEditTxt(ActionEvent actionEvent) {
    }
}
