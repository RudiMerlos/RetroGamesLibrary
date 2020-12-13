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

public class UserRegisterDialogController {

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

    @FXML
    private void onClickBtnSave(ActionEvent event) throws IOException {
        String firstName = txtFirstName.getText();
        String lastName = txtLastName.getText();
        LocalDate birthdate = dprBirthdate.getValue();
        String email = txtEmail.getText();
        String password = pswPassword.getText();

        if (!firstName.isEmpty() && !email.isEmpty() && !password.isEmpty()) {
            if (!emailIsValid(email)) {
                AppDialog.errorDialog("Error en el email",
                        "Debes de introducir una dirección de email válida.");
            } else if (password.length() < 6 || password.length() > 16) {
                AppDialog.errorDialog("Error en la contraseña",
                        "La contraseña debe tener entre 6 y 16 caracteres.");
            } else {
                User user = new User(email, password, firstName, lastName, birthdate);
                UserService userService = new MysqlUserService();
                try {
                    userService.insert(user);
                    AppDialog.messageDialog("Creación de usuarios",
                            "Usuario " + email + " creado con éxito.");
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.close();
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        } else {
            AppDialog.errorDialog("Error en el registro de usuarios",
                    "Debes rellenar los campos obligatorios.");
        }
    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private boolean emailIsValid(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
