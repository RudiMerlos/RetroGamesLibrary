package org.rmc.retrogameslibrary.controller;

import java.util.List;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.PlatformService;
import org.rmc.retrogameslibrary.service.hibernate.HibernatePlatformService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

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
    private TextField txtCommandGame;
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
        cmbPlatformGame.setItems(getPlatformList());
    }

    private ObservableList<String> getPlatformList() {
        ObservableList<String> platformList = FXCollections.observableArrayList();
        PlatformService platformService = new HibernatePlatformService();
        try {
            List<Platform> platforms = platformService.getAll();
            platforms.forEach(platform -> {
                platformList.add(platform.getName() + " - " + platform.getModel());
            });
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
        return platformList;
    }

    @FXML
    private void onClickBtnSelectGame(ActionEvent event) {

    }

    @FXML
    private void onClickBtnSelectImgGame(ActionEvent event) {

    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onClickBtnSave(ActionEvent event) {
        String title = txtTitleGame.getText();
        String description = txaDescriptionGame.getText();
        String strYear = txtYearGame.getText();
        String gender = txtGenderGame.getText();
        String command = txtCommandGame.getText();
        String strPlatform = cmbPlatformGame.getSelectionModel().getSelectedItem();

        if (!title.isEmpty() && !strPlatform.isEmpty()) {
            if (!yearIsValid(strYear)) {
                AppDialog.errorDialog("Error en el año", "Debes de introducir un año válido.");
            } else {
                int year = Integer.parseInt(strYear);
                String[] splitPlatform = strPlatform.split("-");
                String name = splitPlatform[0].trim();
                String model = splitPlatform[1].trim();
                PlatformService platformService = new HibernatePlatformService();
                Game game = null;
                try {
                    Platform platform = platformService.getByNameAndModel(name, model);
                    game = new Game(title, description, year, gender, "", "", command, platform);
                    platform.addGame(game);
                    AppDialog.messageDialog("Creación de juegos",
                            "Juego " + title + " creado con éxito.");
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.close();
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    private boolean yearIsValid(String year) {
        String regex = "^[12][0-9]{3}$";
        return year.matches(regex);
    }
}
