package org.rmc.retrogameslibrary.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.rmc.retrogameslibrary.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class UsersDialogController implements Initializable {

    @FXML
    private TableView<User> tableUsers;
    @FXML
    private TableColumn<User, String> colEmailUser;
    @FXML
    private TableColumn<User, String> colFirstnameUser;
    @FXML
    private TableColumn<User, String> colLastnameUser;
    @FXML
    private TableColumn<User, LocalDate> colBirthdateUser;

    @FXML
    private Button btnEditUser;
    @FXML
    private Button btnDeleteUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    private void onClickBtnEditUser(ActionEvent event) {

    }

    @FXML
    private void onClickBtnDeleteUser(ActionEvent event) {

    }
}
