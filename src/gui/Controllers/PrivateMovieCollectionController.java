package gui.Controllers;


import be.Category;
import be.Movie;

import gui.Model.PrivateMovieCollectionModel;
import gui.SimpleDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.TimerTask;

public class PrivateMovieCollectionController implements Initializable {

    @FXML
    private TableView<Movie> tvEntertainmentTable;
    @FXML
    private TableColumn<Category, String> tcEntertainment;
    @FXML
    private TableView<Category> tvGenreTable;
    @FXML
    private TableColumn<Category, String> tcGenre;
    @FXML
    private TableView<Movie> tvMovieTable;
    @FXML
    private TableColumn<Movie, String> tcTitle;
    @FXML
    private TableColumn<Movie, Integer> tcPersonalRating;
    @FXML
    private TableColumn<Movie, Integer> tcIMDBRating;
    @FXML
    private TableColumn<Movie, String> tcTitleCat;
    @FXML
    private TableColumn<Movie, Integer> tcPersonalRatingCat;
    @FXML
    private TableColumn<Movie, Integer> tcIMDBRatingCat;
    @FXML
    private TextField txtSearchBar;
    @FXML
    private TextField txtNowPlaying;
    @FXML
    private Button addMovie;
    @FXML
    private Button ButtonAddGenre;


    private gui.Model.PrivateMovieCollectionModel PrivateMovieCollectionModel;
    private SongDialogController songController;


    public PrivateMovieCollectionController() throws Exception {
        PrivateMovieCollectionModel = new PrivateMovieCollectionModel();

    }

    public void addMovie(ActionEvent actionEvent) throws IOException {
        Stage swich = (Stage) ButtonAddGenre.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/AddMovie.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public Stage createMovieDialog(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("MovieDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        songController = fxmlLoader.getController();
        songController.setModel(PrivateMovieCollectionModel);
        return stage;
    }


    public void removeMovie(ActionEvent actionEvent) throws Exception {
        if (SimpleDialog.delete() && tvMovieTable.getSelectionModel().getSelectedItem() != null) {
            PrivateMovieCollectionModel.deleteMovie(tvMovieTable.getSelectionModel().getSelectedItem());
        }
    }


    public void addToGenre(ActionEvent actionEvent) throws Exception {
        PrivateMovieCollectionModel.addToPlaylist(tvGenreTable.getSelectionModel().getSelectedItem(), tvMovieTable.getSelectionModel().getSelectedItem());
    }

    public void addGenre(ActionEvent actionEvent) throws Exception {
        Stage swich = (Stage) ButtonAddGenre.getScene().getWindow();
        Parent parent = FXMLLoader.load(getClass().getResource("../FXML/Genre.fxml"));
        Scene scene = new Scene(parent);
        swich.setScene(scene);
    }

    public void updateCategory(ActionEvent actionEvent) throws Exception {
        if (tvGenreTable.getSelectionModel().getSelectedItem() != null) {
            String name = SimpleDialog.playlist();
            Category pl = new Category(tvGenreTable.getSelectionModel().getSelectedItem().getCategoryId(), name);
            PrivateMovieCollectionModel.updatePlaylist(pl);
        }
    }

    public void removeGenre(ActionEvent actionEvent) {
        if (SimpleDialog.delete())
            PrivateMovieCollectionModel.deletePlaylist(tvGenreTable.getSelectionModel().getSelectedItem());
        tvGenreTable.refresh();
    }


    public void positionUp(ActionEvent actionEvent) throws Exception {
        changeOrderInPlaylist(-1);
    }

    public void positionDown(ActionEvent actionEvent) throws Exception {
        changeOrderInPlaylist(+1);
    }

    private void changeOrderInPlaylist(int upOrDown) throws Exception {
        PrivateMovieCollectionModel.swapMoviesInGenre(tvGenreTable.getSelectionModel().getSelectedItem(), tvEntertainmentTable.getSelectionModel().getSelectedIndex(),
                tvEntertainmentTable.getSelectionModel().getSelectedIndex() + upOrDown);
    }

    public void removeFromGenre(ActionEvent actionEvent) throws Exception {
        if (SimpleDialog.delete()) {
            PrivateMovieCollectionModel.removeFromPlaylist(tvGenreTable.getSelectionModel().getSelectedItem(),
                    tvEntertainmentTable.getSelectionModel().getSelectedItem(),
                    tvEntertainmentTable.getSelectionModel().getSelectedIndex());
        }
    }

    public void showGenre(MouseEvent mouseEvent) {
        tvEntertainmentTable.getItems().clear();
        try {
            if (tvGenreTable.getSelectionModel().getSelectedItem() != null) {
                tvEntertainmentTable.setItems(PrivateMovieCollectionModel.getPlaylist(tvGenreTable.getSelectionModel().getSelectedItem()));
                tcTitleCat.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
                tvGenreTable.getItems();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clearEntertainmentTableSelection(MouseEvent mouseEvent) {
        tvEntertainmentTable.getSelectionModel();
    }

    public void clearMovieTable(MouseEvent mouseEvent) {
        tvMovieTable.getSelectionModel();
    }

    public void clearGenreTableSelection(MouseEvent mouseEvent) {
        tvGenreTable.getSelectionModel();
    }

    public void editMovie(ActionEvent actionEvent) {
    }

    public void editGenre(ActionEvent actionEvent) {
    }



    /*å
    /**
     * Adds the selected song to the selected playlist when then button is pressed
     */
    /*
    public void addToCategory(ActionEvent actionEvent) throws Exception {
        PrivateMovieCollectionModel.addToCategory(tvPlaylists.getSelectionModel().getSelectedItem(), tvSongTable.getSelectionModel().getSelectedItem());
    }

    /**å
     * Opens the dialog to get user input to name a new playlist, then creates playlist with that name
     */
    /*
    public void newPlaylist(ActionEvent actionEvent) throws Exception {
        String name = SimpleDialog.playlist();
        PrivateMovieCollectionModel.createCategory(name);
    }

    /**å
     * Opens the dialog to get user input for the name of the playlist, then updates the selected playlist with the new name
     */
    /*
    public void updateCategory(ActionEvent actionEvent) throws Exception {
        if(tvPlaylists.getSelectionModel().getSelectedItem() != null) {
            String name = SimpleDialog.playlist();
            Category pl = new Category(tvPlaylists.getSelectionModel().getSelectedItem().getCategoryId(), name);
            PrivateMovieCollectionModel.updateCategory(pl);
        }
    }

    /**å
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected playlist
     */
    /*
    public void deleteCategory(ActionEvent actionEvent) {
        if(SimpleDialog.delete())
            PrivateMovieCollectionModel.deleteCategory(tvPlaylists.getSelectionModel().getSelectedItem());
        tvPlaylists.refresh();
    }


    public void positionUp(ActionEvent actionEvent) throws Exception  {
        changeOrderInPlaylist(-1);
    }

    public void positionDown(ActionEvent actionEvent) throws Exception  {
        changeOrderInPlaylist(+1);
    }

    private void changeOrderInPlaylist(int upOrDown) throws Exception {
        PrivateMovieCollectionModel.swapSongsInPlaylist(tvPlaylists.getSelectionModel().getSelectedItem(),tvPlaylistSongTable.getSelectionModel().getSelectedIndex(),
                tvPlaylistSongTable.getSelectionModel().getSelectedIndex() + upOrDown);
    }

    /**å
     * Creates a dialog to ask the user to confirm the deletion, then removes the selected song from the current playlist
     */
    /*
    public void removeFromCategory(ActionEvent actionEvent) throws Exception {
        if(SimpleDialog.delete())
        {
            PrivateMovieCollectionModel.removeFromCategory(tvPlaylists.getSelectionModel().getSelectedItem(),
                 tvPlaylistSongTable.getSelectionModel().getSelectedItem(),
                 tvPlaylistSongTable.getSelectionModel().getSelectedIndex());
        }
    }

    /**å
     * Opens a new Movie Dialog window
     */
    /*
    public void newSong(ActionEvent actionEvent) throws IOException, InterruptedException {
        Stage stage = createSongDialog("New Movie");
        Stage mainStage = ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow()));
        stage.initOwner(mainStage);
        stage.showAndWait();
    }

    /**
     * Opens a new Movie Dialog window with the current info of the selected song already in the text fields
     */
    /*
    public void editSong(ActionEvent actionEvent) throws IOException, InterruptedException {
        if(tvSongTable.getSelectionModel().getSelectedItem() != null) {
            Stage stage = createSongDialog("Edit Movie");
            Stage mainStage = ((Stage) (((Button) actionEvent.getSource()).getScene().getWindow()));
            stage.initOwner(mainStage);

            Movie movie = tvSongTable.getSelectionModel().getSelectedItem();
            String filepath = movie.getFileLink().replace("file:/", "");
            songController.setSongValues(movie.getMovieId(), movie.getMovieName(), movie.getPublicRating(), movie.getPrivateRating(), filepath);
            stage.showAndWait();
        }
    }

    /**å
     * Creates a dialog to ask the user to confirm the deletion, then deletes the selected song
     */
    /*
    public void deleteMovie(ActionEvent actionEvent) throws Exception {
        if(SimpleDialog.delete() && tvSongTable.getSelectionModel().getSelectedItem() != null) {
            PrivateMovieCollectionModel.deleteMovie(tvSongTable.getSelectionModel().getSelectedItem());
        }
    }

    /**ååååå
     * Plays or pauses a song - it detects both if the music player is playing or if it is paused
     * it also checks if there is no song selected from the song table and tries to play the start of a playlist.
     */
    /*
    public void playPause(ActionEvent actionEvent)
    {
        if(!PrivateMovieCollectionModel.isPlaying())
        {
            if(tvPlaylistSongTable.getSelectionModel().getSelectedItem() != null)
            {
                PrivateMovieCollectionModel.playSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
                txtNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
            }
            else if (tvSongTable.getSelectionModel().getSelectedItem()!= null)
            {
                txtNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.playSong(tvSongTable.getSelectionModel().getSelectedItem());

            }

        }
        else if(PrivateMovieCollectionModel.isPlaying())
            PrivateMovieCollectionModel.stopPlaying();
        else if (tvSongTable.getSelectionModel().getSelectedItem() == null && tvPlaylistSongTable.getItems() != null)
        {
            System.out.println("test");
            tvPlaylistSongTable.getSelectionModel().select(1);
            PrivateMovieCollectionModel.playSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
        }

    }

    /**ååååå
     * Gets the previous song from the playlist or song table
     */
    /*
    public void previousSong(ActionEvent actionEvent)
    {
        if(tvSongTable.getSelectionModel().getSelectedItem() == null) {
            if (tvPlaylistSongTable.getSelectionModel().getSelectedIndex() == 0) {
                tvPlaylistSongTable.getSelectionModel().selectLast();
                txtNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            } else {
                tvPlaylistSongTable.getSelectionModel().selectPrevious();
                txtNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.previousSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            }
        }
        else
        {
            if(tvSongTable.getSelectionModel().getSelectedIndex() == 0)
            {
                tvSongTable.getSelectionModel().selectLast();
                txtNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.previousSong(tvSongTable.getSelectionModel().getSelectedItem());}
            else
            {
                tvSongTable.getSelectionModel().selectPrevious();
                txtNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.previousSong(tvSongTable.getSelectionModel().getSelectedItem());
            }
        }

    }

    /**åååå
     * Gets the next song from the playlist or song table
     */
    /*
    public void nextSong(ActionEvent actionEvent)
    {
        if(tvSongTable.getSelectionModel().getSelectedItem() == null)
        {
            if(tvPlaylistSongTable.getSelectionModel().getSelectedIndex()+1 == tvPlaylistSongTable.getItems().size())
            {
                tvPlaylistSongTable.getSelectionModel().selectFirst();
                txtNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            }
            else
            {
                tvPlaylistSongTable.getSelectionModel().selectNext();
                txtNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
            }
        }
        else
        {
            if(tvSongTable.getSelectionModel().getSelectedIndex()+1 == tvSongTable.getItems().size())
            {
                tvSongTable.getSelectionModel().selectFirst();
                txtNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
            }
            else
            {
                tvSongTable.getSelectionModel().selectNext();
                txtNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                PrivateMovieCollectionModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
            }
        }
    }

    /**åååå
     * Handles the autoplay functionality
     */
    /*
    public TimerTask continuePlaying()
    {
        TimerTask t = new TimerTask() {
            @Override
            public void run()
            {{if(PrivateMovieCollectionModel.isSongFinished())
                if(tvSongTable.getSelectionModel().getSelectedItem() == null && tvPlaylistSongTable.getSelectionModel().getSelectedItem() != null)
                {
                    if(tvPlaylistSongTable.getSelectionModel().getSelectedIndex()+1 == tvPlaylistSongTable.getItems().size())
                    {
                        tvPlaylistSongTable.getSelectionModel().selectFirst();
                        txtNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                        PrivateMovieCollectionModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
                    }
                    else
                    {
                        tvPlaylistSongTable.getSelectionModel().selectNext();
                        txtNowPlaying.setText(tvPlaylistSongTable.getSelectionModel().getSelectedItem().getName());
                        PrivateMovieCollectionModel.nextSong(tvPlaylistSongTable.getSelectionModel().getSelectedItem());
                    }
                }
                else if (tvSongTable.getSelectionModel().getSelectedItem() != null)
                {
                    if(tvSongTable.getSelectionModel().getSelectedIndex()+1 == tvSongTable.getItems().size())
                    {
                        tvSongTable.getSelectionModel().selectFirst();
                        txtNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                        PrivateMovieCollectionModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
                    }
                    else
                    {
                        tvSongTable.getSelectionModel().selectNext();
                        txtNowPlaying.setText(tvSongTable.getSelectionModel().getSelectedItem().getName());
                        PrivateMovieCollectionModel.nextSong(tvSongTable.getSelectionModel().getSelectedItem());
                    }
                }
            }
                PrivateMovieCollectionModel.setSongFinished(false);
            }
        };
        return t;
    }

    /**å
     * When a playlist is clicked then songs on the playlist are shown in the middle table
     */
    /*
    public void showPlaylist(MouseEvent mouseEvent) {
        tvPlaylistSongTable.getItems().clear();
        try{
            if(tvPlaylists.getSelectionModel().getSelectedItem() != null)
            {tvPlaylistSongTable.setItems(PrivateMovieCollectionModel.getPlaylist(tvPlaylists.getSelectionModel().getSelectedItem()));
                tcPlaylistSongs.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
                tvPlaylistSongTable.getItems();}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**å
     * Creates the Movie Dialog window for New song and Edit song
     */
    /*
    public Stage createSongDialog(String windowTitle) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("SongDialog.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle(windowTitle);
        stage.initModality(Modality.WINDOW_MODAL);
        songController = fxmlLoader.getController();
        songController.setModel(PrivateMovieCollectionModel);
        return stage;
    }
*/

    /**
     * Initializes MOVIE AND PLAYLIST
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTvMovieTable();
        setTcMoviesInGenreTable();

        txtSearchBar.textProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                PrivateMovieCollectionModel.searchSongs(newValue);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Method used for initializing the genre table
     */
    public void setTcMoviesInGenreTable() {
        tcTitleCat.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
        tcPersonalRatingCat.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("privateRating"));
        tcIMDBRatingCat.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("publicRating"));
        try {
            tvGenreTable.setItems(PrivateMovieCollectionModel.getAllPlaylists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method used for initializing the movie table.
     */
    private void setTvMovieTable() {
        tcTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("movieName"));
        tcPersonalRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("privateRating"));
        tcIMDBRating.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("publicRating"));
        try {
            tvMovieTable.setItems(PrivateMovieCollectionModel.getSonglist());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    




    /*
    /**
     * Method used for initializing the playlists
     */
    /*
    public void setTcPlaylistTable() {
        tcPlaylistName.setCellValueFactory(new PropertyValueFactory<Category, String>("playlistName"));
        tcPlaylistTime.setCellValueFactory(new PropertyValueFactory<Category, String>("playlistTimelength"));
        tcNumberSongs.setCellValueFactory(new PropertyValueFactory<Category, Integer>("playlistSongCount"));
        try {
            tvPlaylists.setItems(PrivateMovieCollectionModel.getAllPlaylists());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //*/
    /*
    /**
     * Method used for initializing the song table
     */
    /*
    private void setTvSongTable() {
        tcSongArtist.setCellValueFactory(new PropertyValueFactory<Movie, String>("artistName"));
        tcSongTitle.setCellValueFactory(new PropertyValueFactory<Movie, String>("name"));
        tcSongTime.setCellValueFactory(new PropertyValueFactory<Movie, String>("songLength"));
        try{
            tvSongTable.setItems(PrivateMovieCollectionModel.getSonglist());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**åå
     * Clears the selected song on the song table
     */
    /*
    public void clearSongTableSelection(MouseEvent mouseEvent) {
        tvSongTable.getSelectionModel().clearSelection();
    }

    /**åå
     * Clears the selected song on the playlist song table
     */
    /*
    public void clearPlaylistSongTable(MouseEvent mouseEvent) {

        tvPlaylistSongTable.getSelectionModel().clearSelection();
    }
    */

}
