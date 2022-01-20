package gui.Controllers;

import be.Category;
import gui.Model.PrivateMovieCollectionModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class EditCategoryController {

    //here we call the Button/Textfield from FXML that we use
    @FXML
    private Button cancel;
    @FXML
    private TextField edit;

    private Category selectedCategory;

    PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

    public EditCategoryController() throws Exception {
    }

    /**
     * This is our cancel button, it cancels the act of editing a category and takes us to the main window again
     * @param actionEvent
     * @throws IOException
     */
    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage switchScene = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);
    }

    /**
     * his is our save button, it saves the new name of the Category
     * @param actionEvent
     * @throws Exception
     */
    public void save(ActionEvent actionEvent) throws Exception {
        String updateGenreName = edit.getText();
        this.selectedCategory.setCategoryName(updateGenreName);
        privateMovieCollectionModel.updateCategory(this.selectedCategory);

        cancel(actionEvent);
    }

    /**
     * this takes the selected category and changes the name
     * @param category
     */
    public void setSelectedCategory(Category category) {
        this.selectedCategory = category;
        edit.setText(category.getCategoryName());
    }
}
