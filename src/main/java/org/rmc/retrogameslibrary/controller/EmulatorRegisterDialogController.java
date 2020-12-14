package org.rmc.retrogameslibrary.controller;

import java.io.File;
import java.util.Properties;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Emulator;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.EmulatorService;
import org.rmc.retrogameslibrary.service.objectdb.ObjectdbEmulatorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EmulatorRegisterDialogController {

    @FXML
    private TextField txtNameEmulator;

    @FXML
    private Button btnSelectPathEmulator;
    @FXML
    private Label lblPathEmulator;

    @FXML
    private Button btnCancel;
    @FXML
    private Button btnSave;

    private File emulatorFile = null;

    @FXML
    private void onClickBtnSelectPathEmulator(ActionEvent event) {
        Stage stage = (Stage) btnSelectPathEmulator.getScene().getWindow();
        Properties properties = PropertiesConfig.readProperties();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccionar emulador");
        // Sets initial directory that it is save in the config file, if not setted, the initial
        // directory will be user home
        fileChooser.setInitialDirectory(new File(properties.getProperty(
                PropertiesConfig.EMULATOR_LAST_PATH, System.getProperty("user.home"))));
        emulatorFile = fileChooser.showOpenDialog(stage);
        lblPathEmulator.setText(emulatorFile.getAbsolutePath());
        // Saves the last directory into the config file
        PropertiesConfig.writeEmulatorLastPathProperites(emulatorFile.getParent());
    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onClickBtnSave(ActionEvent event) {
        String name = txtNameEmulator.getText();
        String path = lblPathEmulator.getText();

        if (!name.isEmpty() && !path.isEmpty()) {
            Emulator emulator = new Emulator(name, path);
            EmulatorService emulatorService = new ObjectdbEmulatorService();
            try {
                emulatorService.insert(emulator);
                AppDialog.messageDialog("Creación de emuladores",
                        "Emulador " + name + " creado con éxito.");
                Stage stage = (Stage) btnSave.getScene().getWindow();
                stage.close();
            } catch (CrudException e) {
                AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
            }
        } else {
            AppDialog.errorDialog("Error en el registro de emuladores",
                    "Debes rellenar los campos obligatorios.");
        }
    }
}
