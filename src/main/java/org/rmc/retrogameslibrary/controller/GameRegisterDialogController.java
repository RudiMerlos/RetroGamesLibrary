package org.rmc.retrogameslibrary.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class GameRegisterDialogController {

    @FXML
    private TextField txtTitleGame;
    @FXML
    private TextArea txaDescriptionGame;
    @FXML
    private TextField txtYearGame;
    @FXML
    private TextField txtGenderGame;
    @FXML
    private Button btnSelectGame;
    @FXML
    private Label lblPathGame;

    @FXML
    private ComboBox<String> cmbPlatformGame;
    @FXML
    private Button btnSelectImgGame;
    @FXML
    private ImageView imgScreenshotGame;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    @FXML
    public void initialize() {

    }

    @FXML
    private void onClickBtnSelectGame(ActionEvent event) {

    }

    @FXML
    private void onClickBtnSelectImgGame(ActionEvent event) {

    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) {

    }

    @FXML
    private void onClickBtnSave(ActionEvent event) {

    }
}
