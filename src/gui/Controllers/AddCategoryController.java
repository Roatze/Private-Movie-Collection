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
    //here we call the Button/Textfield from FXML that we use
    @FXML
    private Button cancel;
    @FXML
    private TextField addGenre;

    /**
     * This is our cancel button, it cancels the act of adding a category and takes us to the main window again
     * @param event
     * @throws IOException
     */
    public void cancel(ActionEvent event) throws IOException {
        Stage switchScene = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);
    }


    /**
     * This is our save button, it saves the new name of the Category
     * @param actionEvent
     * @throws Exception
     */
    public void save(ActionEvent actionEvent) throws Exception {
        String uploadTitle = addCategory();
        uploadCategoryInfo(uploadTitle);
        Stage switchScene = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);

    }

    /**
     * This is where we upload our information to the database
     * @param category
     * @throws Exception
     */
    private void uploadCategoryInfo(String category) throws Exception {
        PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

        privateMovieCollectionModel.addCategory(category);

    }

    public String addCategory() {
        String categoryTemp = addGenre.getText();
        return categoryTemp;
    }
}
