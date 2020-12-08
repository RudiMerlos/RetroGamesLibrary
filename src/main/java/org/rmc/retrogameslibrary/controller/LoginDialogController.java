package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.User;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.UserService;
import org.rmc.retrogameslibrary.service.jdbc.MysqlUserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginDialogController implements Initializable {

    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField pswPassword;

    @FXML
    private Hyperlink lnkRegister;

    @FXML
    private Button btnOk;
    @FXML
    private Button btnExit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            MysqlUserService userService = new MysqlUserService();
            userService.createTable();

        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
    }

    @FXML
    private void onClickBtnOk(ActionEvent event) throws IOException {
        UserService userService = new MysqlUserService();
        String email = txtUsername.getText();
        try {
            User user = userService.getById(email);
            if (user != null) {
                if (!user.getPassword().equals(pswPassword.getText())) {
                    AppDialog.errorDialog("Error", "La contraseña es incorrecta.");
                } else {
                    PropertiesConfig.writeCurrentUserProperties(user.getEmail());
                    initMainWindow((Stage) btnOk.getScene().getWindow());
                }
            } else {
                AppDialog.errorDialog("Error", "No existe el usuario " + email);
            }
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
    }

    @FXML
    private void onClickBtnExit(ActionEvent event) {
        Stage stage = (Stage) btnExit.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onClickLnkRegister(ActionEvent event) throws IOException {
        Stage stage = (Stage) lnkRegister.getScene().getWindow();
        userRegisterWindow(stage);
    }

    private void initMainWindow(Stage stage) throws IOException {
        Parent root = FXMLLoader
                .load(getClass().getResource("/view/mainwindow.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Retro Games Library");
        stage.setMinWidth(900);
        stage.setMinHeight(700);
        stage.setResizable(true);
        stage.show();
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
