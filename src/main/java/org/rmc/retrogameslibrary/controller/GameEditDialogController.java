package org.rmc.retrogameslibrary.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Properties;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.GameService;
import org.rmc.retrogameslibrary.service.PlatformService;
import org.rmc.retrogameslibrary.service.hibernate.HibernateGameService;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class GameEditDialogController {

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

    private File gameFile = null;
    private File imgFile = null;

    private Game game = null;

    @FXML
    public void initialize() {
        // Initialize combo box
        cmbPlatformGame.setItems(getPlatformList());
    }

    // Sets the game to edit
    public void init(Game game) {
        this.game = game;
        if (game == null) {
            Stage stage = (Stage) txtTitleGame.getScene().getWindow();
            stage.close();
        }
        txtTitleGame.setText(game.getTitle());
        txaDescriptionGame.setText(game.getDescription());
        txtYearGame.setText(game.getYear() == null ? "" : game.getYear().toString());
        txtGenderGame.setText(game.getGender());
        txtCommandGame.setText(game.getCommand());
        gameFile = game.getPath() == null ? null : new File(game.getPath());
        if (gameFile != null)
            lblPathGame.setText(game.getPath());
        cmbPlatformGame.getSelectionModel().select(game.getPlatform().toString());
        imgFile = game.getScreenshot() == null ? null : new File(game.getScreenshot());
        if (imgFile != null) {
            try {
                imgScreenshotGame.setImage(new Image(Files.newInputStream(imgFile.toPath())));
            } catch (IOException e) {
                imgScreenshotGame.setImage(null);
            }
        }
    }

    // Reads games from database and they set into ObservableList
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
        Stage stage = (Stage) btnSelectGame.getScene().getWindow();
        Properties properties = PropertiesConfig.readProperties();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar juego");
        // Sets initial directory that it is save in the config file, if not setted, the initial
        // directory will be user home
        fileChooser.setInitialDirectory(new File(properties.getProperty(
                PropertiesConfig.GAME_ROM_LAST_PATH, System.getProperty("user.home"))));
        gameFile = fileChooser.showOpenDialog(stage);
        lblPathGame.setText(gameFile.getAbsolutePath());
        // Saves the last directory into the config file
        PropertiesConfig.writeGameLastPathProperties(gameFile.getParent());
    }

    @FXML
    private void onClickBtnSelectImgGame(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnSelectImgGame.getScene().getWindow();
        Properties properties = PropertiesConfig.readProperties();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif", "*.giff"));
        fileChooser.setTitle("Seleccionar imagen");
        // Sets initial directory that it is save in the config file, if not setted, the initial
        // directory will be user home
        fileChooser.setInitialDirectory(new File(properties.getProperty(
                PropertiesConfig.GAME_IMG_LAST_PATH, System.getProperty("user.home"))));
        imgFile = fileChooser.showOpenDialog(stage);
        imgScreenshotGame.setImage(new Image(Files.newInputStream(imgFile.toPath())));
        // Saves the last directory into the config file
        PropertiesConfig.writeImgLastPathProperites(imgFile.getParent());
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
        String path = gameFile != null ? gameFile.getAbsolutePath() : null;
        String strPlatform = cmbPlatformGame.getSelectionModel().getSelectedItem();
        String screenshot = imgFile != null ? imgFile.getAbsolutePath() : null;

        if (!title.isEmpty() && !strPlatform.isEmpty()) {
            if (!strYear.isEmpty() && !yearIsValid(strYear)) {
                AppDialog.errorDialog("Error en el año", "Debes de introducir un año válido.");
            } else {
                Integer year = strYear.isEmpty() ? null : Integer.parseInt(strYear);
                String[] splitPlatform = strPlatform.split("-");
                String name = splitPlatform[0].trim();
                String model = splitPlatform[1].trim();
                PlatformService platformService = new HibernatePlatformService();
                GameService gameService = new HibernateGameService();
                try {
                    // Gets the platform by name and model
                    Platform platform = platformService.getByNameAndModel(name, model);
                    game.setTitle(title);
                    game.setDescription(description);
                    game.setYear(year);
                    game.setGender(gender);
                    game.setScreenshot(screenshot);
                    game.setPath(path);
                    game.setCommand(command);
                    game.setPlatform(platform);
                    gameService.modify(game);
                    AppDialog.messageDialog("Edición de juegos",
                            "Juego " + title + " modificado con éxito.");
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.close();
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        } else {
            AppDialog.errorDialog("Error en la edición de juegos",
                    "Debes rellenar los campos obligatorios.");
        }
    }

    // Checks if year is valid
    private boolean yearIsValid(String year) {
        String regex = "^[12][0-9]{3}$";
        return year.matches(regex);
    }
}
