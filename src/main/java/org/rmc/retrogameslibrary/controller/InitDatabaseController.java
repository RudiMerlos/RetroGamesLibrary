package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.sql.SQLException;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.service.jdbc.MysqlConnection;
import org.rmc.retrogameslibrary.view.AppDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InitDatabaseController {

    @FXML
    private TextField txtHost;
    @FXML
    private TextField txtUser;
    @FXML
    private PasswordField pswPassword;
    @FXML
    private TextField txtDatabase;

    @FXML
    private Button btnOk;
    @FXML
    private Button btnCancel;

    @FXML
    private void onClickBtnOk(ActionEvent event) throws IOException {
        String host = txtHost.getText();
        String user = txtUser.getText();
        String pass = pswPassword.getText();
        String db = txtDatabase.getText();

        if (!host.isEmpty() && !user.isEmpty() && !db.isEmpty()) {
            try {
                MysqlConnection.getInstance().connect(host, user, pass, db);
                PropertiesConfig.writeDatabaseProperties(host, user, pass, db);
                initLoginWindow((Stage) btnOk.getScene().getWindow());
            } catch (SQLException e) {
                AppDialog.errorDialog("Error en la base de datos de usuarios",
                        "No se ha podido conectar a la base de datos.",
                        "Comprueba que los datos introducidos son correctos y que el servidor MySQL está corriendo.");
            }
        } else {
            AppDialog.errorDialog("Error en la base de datos de usuarios",
                    "Debes rellenar los campos obligatorios.");
        }
    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private void initLoginWindow(Stage stage) throws IOException {
        Parent root = FXMLLoader
                .load(getClass().getResource("/view/userdialog.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }
}
