package gui.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MovieEditController {

    @FXML
    private Button ButtonEditMovie;
    @FXML
    public Button MovieEditCancel;

    public void genreCancelEditBtn(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) MovieEditCancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }
}
