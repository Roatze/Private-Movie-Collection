package gui.Controllers;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;


public class AddMovieController {
    public Button returnMainMenu;

    public void goReturnMainMenu(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) returnMainMenu.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../view/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public void getSongInfo(ActionEvent actionEvent) {
    }

    public void chooseFile(ActionEvent actionEvent) {
    }
}
