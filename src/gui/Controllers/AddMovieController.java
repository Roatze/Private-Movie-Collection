package gui.Controllers;

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


public class AddMovieController{

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

    public void goReturnMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) returnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public void getMovieInfo(ActionEvent actionEvent) {
    }

    public void chooseFile(ActionEvent actionEvent) {
    }

    public void chooseFileMethod(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Movie");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")+ System.getProperty("file.separator") +"Desktop"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Audio Files",  "*.mp4", "*.mpeg4", "*.aac"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            //insert data from file
            fileBar.setText(selectedFile.getAbsolutePath());
        }
    }

    /*
    Error metode.
     */
    private void error(String text){
        Alert alert = new Alert(Alert.AlertType.ERROR,text,ButtonType.YES);
        alert.showAndWait();
    }

    public void moreGenreBtn(ActionEvent event) throws IOException {
        Stage swich = (Stage) returnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/Genre.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }
}
