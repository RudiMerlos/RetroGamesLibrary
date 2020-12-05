package org.rmc.retrogameslibrary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
    private Button btnOk;
    @FXML
    private Button btnCancel;

    @FXML
    private void onClickBtnOk(ActionEvent event) {

    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) {

    }
}
