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

    @FXML
    private Button cancel;
    @FXML
    private TextField edit;

    private Category selectedCategory;

    PrivateMovieCollectionModel privateMovieCollectionModel = new PrivateMovieCollectionModel();

    public EditCategoryController() throws Exception {
    }


    public void cancel(ActionEvent actionEvent) throws IOException {
        Stage switchScene = (Stage) cancel.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../View/PrivateMovieCollection.fxml"));
        Scene scene = new Scene(parent);
        switchScene.setScene(scene);
    }

    public void save(ActionEvent actionEvent) throws Exception {
        String updateGenreName = edit.getText();
        this.selectedCategory.setCategoryName(updateGenreName);
        privateMovieCollectionModel.updateCategory(this.selectedCategory);

        cancel(actionEvent);
    }

    public void setSelectedCategory(Category category) {
        this.selectedCategory = category;
        edit.setText(category.getCategoryName());
    }
}
