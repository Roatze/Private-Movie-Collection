package gui.Controllers;

import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class AddCategoryController {
    @FXML
    private Button cancel;
    @FXML
    private TextField addGenre;

    public void cancel(ActionEvent event) throws IOException {
        Stage switchScene = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);
    }



    public void save(ActionEvent actionEvent) throws Exception {
        String uploadTitle = addCategory();
        uploadCategoryInfo(uploadTitle);
        Stage switchScene = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);

    }

    private void uploadCategoryInfo(String category) throws Exception {
        PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

        privateMovieCollectionModel.addCategory(category);

    }

    public String addCategory() {
        String categoryTemp = addGenre.getText();
        return categoryTemp;
    }
}
