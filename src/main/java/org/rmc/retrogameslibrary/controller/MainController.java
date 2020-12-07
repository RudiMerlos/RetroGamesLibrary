package org.rmc.retrogameslibrary.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.model.Game;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

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
    private Label lblUser;
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Game> tableGames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblUser.setText(PropertiesConfig.CURRENT_USER);
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
    private void onClickSessionClose(ActionEvent event) {

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
    private void onClickPlatforms(ActionEvent event) {

    }

    @FXML
    private void onClickUsers(ActionEvent event) {

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
    private void onTextChangedTxtSearch(ActionEvent event) {

    }
}
