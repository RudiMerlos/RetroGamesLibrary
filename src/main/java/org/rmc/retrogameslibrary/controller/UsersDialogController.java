package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.User;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.UserService;
import org.rmc.retrogameslibrary.service.jdbc.MysqlUserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

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
    private Button btnAddUser;
    @FXML
    private Button btnEditUser;
    @FXML
    private Button btnDeleteUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableUsers.setPlaceholder(new Label("No hay usuarios"));
        showUsers();
    }

    private ObservableList<User> getUserList() {
        UserService userService = new MysqlUserService();
        ObservableList<User> userList = FXCollections.observableArrayList();
        try {
            userList.setAll(userService.getAll());
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
        return userList;
    }

    private void showUsers() {
        ObservableList<User> users = getUserList();

        colEmailUser.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        colFirstnameUser.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        colLastnameUser.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        colBirthdateUser.setCellValueFactory(new PropertyValueFactory<User, LocalDate>("birthdate"));

        tableUsers.setItems(users);
    }

    @FXML
    private void onClickBtnAddUser(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddUser.getScene().getWindow();
        userRegisterWindow(stage);
    }

    @FXML
    private void onClickBtnEditUser(ActionEvent event) {

    }

    @FXML
    private void onClickBtnDeleteUser(ActionEvent event) {

    }

    private void userRegisterWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load( getClass().getResource("/view/userregisterdialog.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Registro de usuarios");
        newStage.setResizable(false);
        newStage.show();
    }
}
