package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController implements Initializable {

    @FXML
    private MenuItem menuAddNewGame;
    @FXML
    private MenuItem menuImport;
    @FXML
    private MenuItem menuExport;
    @FXML
    private MenuItem menuSessionClose;
    @FXML
    private MenuItem menuQuit;
    @FXML
    private MenuItem menuEditGame;
    @FXML
    private MenuItem menuDelete;
    @FXML
    private MenuItem menuPlatforms;
    @FXML
    private MenuItem menuUsers;
    @FXML
    private MenuItem menuEmulators;
    @FXML
    private MenuItem menuPreferences;
    @FXML
    private MenuItem menuAbout;

    @FXML
    private Button btnAddNewGame;
    @FXML
    private Button btnPlayGame;
    @FXML
    private Label lblUser;
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Game> tableGames;
    @FXML
    private TableColumn<Game, String> colTitleGame;
    @FXML
    private TableColumn<Game, String> colPlatformGame;
    @FXML
    private TableColumn<Game, Integer> colYearGame;
    @FXML
    private TableColumn<Game, String> colGendreGame;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Properties properties = PropertiesConfig.readProperties();
        lblUser.setText(properties.getProperty(PropertiesConfig.CURRENT_USER));
    }

    @FXML
    private void onClickAddNewGame(ActionEvent event) {

    }

    @FXML
    private void onClickImport(ActionEvent event) {

    }

    @FXML
    private void onClickExport(ActionEvent event) {

    }

    @FXML
    private void onClickSessionClose(ActionEvent event) throws IOException {
        PropertiesConfig.writeCurrentUserProperties("");
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/logindialog.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void onClickQuit(ActionEvent event) {

    }

    @FXML
    private void onClickEditGame(ActionEvent event) {

    }

    @FXML
    private void onClickDeleteGame(ActionEvent event) {

    }

    @FXML
    private void onClickPlatforms(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) btnAddNewGame.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initOwner(thisStage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/platformdialog.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Platforms");
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onClickUsers(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) btnAddNewGame.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initOwner(thisStage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/usersdialog.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Users");
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onClickEmulators(ActionEvent event) {

    }

    @FXML
    private void onClickPreferences(ActionEvent event) {

    }

    @FXML
    private void onClickAbout(ActionEvent event) {

    }

    @FXML
    private void onClickPlayGame(ActionEvent event) {

    }

    @FXML
    private void onTextChangedTxtSearch(ActionEvent event) {

    }
}
