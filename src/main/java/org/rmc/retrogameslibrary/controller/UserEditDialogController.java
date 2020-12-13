package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.time.LocalDate;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.User;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.UserService;
import org.rmc.retrogameslibrary.service.jdbc.MysqlUserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class UserEditDialogController {

    @FXML
    private TextField txtFirstName;
    @FXML
    private TextField txtLastName;
    @FXML
    private DatePicker dprBirthdate;
    @FXML
    private TextField txtEmail;
    @FXML
    private PasswordField pswPassword;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private User user = null;

    public void init(User user) {
        this.user = user;
        if (user == null) {
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.close();
        }
        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        dprBirthdate.setValue(user.getBirthdate());
        txtEmail.setText(user.getEmail());
        pswPassword.setText(user.getPassword());
    }

    @FXML
    private void onClickBtnSave(ActionEvent event) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate birthdate = dprBirthdate.getValue();
        String password = pswPassword.getText();

        if (!firstName.isEmpty() && !password.isEmpty()) {
            if (password.length() < 6 || password.length() > 16) {
                AppDialog.errorDialog("Error en la contraseña",
                        "La contraseña debe tener entre 6 y 16 caracteres.");
            } else {
                UserService userService = new MysqlUserService();
                try {
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setBirthdate(birthdate);
                    user.setPassword(password);
                    userService.modify(user);
                    AppDialog.messageDialog("Edición de usuarios",
                            "Usuario " + user.getEmail() + " modificado con éxito.");
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.close();
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        } else {
            AppDialog.errorDialog("Error en la edición de usuarios",
                    "Debes rellenar los campos obligatorios.");
        }
    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }
}
