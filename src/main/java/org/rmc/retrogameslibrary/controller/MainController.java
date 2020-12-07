package org.rmc.retrogameslibrary.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.jdbc.MysqlUserService;
import org.rmc.retrogameslibrary.view.AppDialog;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
    private ComboBox<String> combOrderBy;
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Game> tableGames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            MysqlUserService userService = new MysqlUserService();
            userService.createTable();

        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
    }
}
