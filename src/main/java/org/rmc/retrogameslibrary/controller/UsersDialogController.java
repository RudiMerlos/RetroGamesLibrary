package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Properties;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class UsersDialogController {

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

    @FXML
    public void initialize() {
        showUsers();
    }

    // Reads users from database and they set into ObservableList
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

    // Show users in the users table
    private void showUsers() {
        ObservableList<User> users = getUserList();

        colEmailUser.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        colFirstnameUser.setCellValueFactory(new PropertyValueFactory<User, String>("firstName"));
        colLastnameUser.setCellValueFactory(new PropertyValueFactory<User, String>("lastName"));
        colBirthdateUser
                .setCellValueFactory(new PropertyValueFactory<User, LocalDate>("birthdate"));

        tableUsers.setItems(users);
        tableUsers.refresh();
    }

    @FXML
    private void onClickBtnAddUser(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddUser.getScene().getWindow();
        userRegisterWindow(stage).setOnHidden(e -> showUsers());
    }

    @FXML
    private void onClickBtnEditUser(ActionEvent event) throws IOException {
        editUser();
    }

    @FXML
    private void onMouseClickedCol(MouseEvent event) throws IOException {
        User user = tableUsers.getSelectionModel().getSelectedItem();
        Properties properties = PropertiesConfig.readProperties();
        if (user != null) {
            btnEditUser.setDisable(false);
            // If current user is selected, delete button is disabled
            if (!user.getEmail().equals(properties.getProperty(PropertiesConfig.CURRENT_USER)))
                btnDeleteUser.setDisable(false);
            else
                btnDeleteUser.setDisable(true);
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1)
                editUser();
        }
    }

    @FXML
    private void onKeyReleasedCol(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            User user = tableUsers.getSelectionModel().getSelectedItem();
            if (user != null) {
                editUser();
            }
        }
    }

    // Shows user edit window with the selected user and shows users updated when is finished
    private void editUser() throws IOException {
        User user = tableUsers.getSelectionModel().getSelectedItem();
        if (user != null) {
            Stage stage = (Stage) btnEditUser.getScene().getWindow();
            userEditWindow(stage, user).setOnHidden(e -> showUsers());
        }
    }

    @FXML
    private void onClickBtnDeleteUser(ActionEvent event) {
        User user = tableUsers.getSelectionModel().getSelectedItem();
        if (user != null) {
            if (AppDialog.confirmationDialog("Eliminar usuarios",
                    "¿Estás seguro de que quieres borrar al usuario " + user.getEmail() + "?")) {
                UserService userService = new MysqlUserService();
                try {
                    userService.remove(user);
                    AppDialog.messageDialog("Eliminar usuarios",
                            "Se ha eliminado el usuario " + user.getEmail() + " con éxito.");
                    showUsers();
                    // When a user is deleted, disables edit and delete buttons
                    btnEditUser.setDisable(true);
                    btnDeleteUser.setDisable(true);
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    // Shows user register window
    private Stage userRegisterWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/userregisterdialog.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Registro de usuarios");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    // Shows user edit window
    private Stage userEditWindow(Stage stage, User user) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/usereditdialog.fxml"));
        Parent root = loader.load();
        UserEditDialogController controller = loader.getController();
        controller.init(user); // the user is passed by init method
        newStage.setScene(new Scene(root));
        newStage.setTitle("Edición de usuarios");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }
}
