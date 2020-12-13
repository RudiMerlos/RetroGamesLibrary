package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Emulator;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.EmulatorService;
import org.rmc.retrogameslibrary.service.objectdb.ObjectdbEmulatorService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class EmulatorDialogController {

    @FXML
    private TableView<Emulator> tableEmulators;
    @FXML
    private TableColumn<Emulator, String> colNameEmulator;
    @FXML
    private TableColumn<Emulator, String> colPathEmulator;

    @FXML
    private Button btnAddEmulator;
    @FXML
    private Button btnEditEmulator;
    @FXML
    private Button btnDeleteEmulator;

    @FXML
    public void initialize() {
        tableEmulators.setPlaceholder(new Label("No hay emuladores"));
        showEmulators();
    }

    private ObservableList<Emulator> getEmulatorList() {
        EmulatorService emulatorService = new ObjectdbEmulatorService();
        ObservableList<Emulator> emulatorList = FXCollections.observableArrayList();
        try {
            emulatorList.setAll(emulatorService.getAll());
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
        return emulatorList;
    }

    private void showEmulators() {
        ObservableList<Emulator> emulators = getEmulatorList();

        colNameEmulator.setCellValueFactory(new PropertyValueFactory<Emulator, String>("name"));
        colPathEmulator.setCellValueFactory(new PropertyValueFactory<Emulator, String>("path"));

        tableEmulators.setItems(emulators);
        tableEmulators.refresh();
    }

    @FXML
    private void onClickBtnAddEmulator(ActionEvent event) {
        Stage stage = (Stage) btnAddEmulator.getScene().getWindow();
        emulatorRegisterEditWindow(stage, null).setOnHidden(e -> showEmulators());
    }

    @FXML
    private void onClickBtnEditEmulator(ActionEvent event) throws IOException {
        editEmulator();
    }

    @FXML
    private void onMouseClickedCol(MouseEvent event) throws IOException {
        Emulator emulator = tableEmulators.getSelectionModel().getSelectedItem();
        if (emulator != null) {
            btnEditEmulator.setDisable(false);
            btnDeleteEmulator.setDisable(false);
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1)
                editEmulator();
        }
    }

    @FXML
    private void onKeyReleasedCol(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            Emulator emulator = tableEmulators.getSelectionModel().getSelectedItem();
            if (emulator != null)
                editEmulator();
        }
    }

    private void editEmulator() throws IOException {
        Emulator emulator = tableEmulators.getSelectionModel().getSelectedItem();
        if (emulator != null) {
            Stage stage = (Stage) btnEditEmulator.getScene().getWindow();
            emulatorRegisterEditWindow(stage, emulator).setOnHidden(e -> showEmulators());
        }
    }

    @FXML
    private void onClickBtnDeleteEmulator(ActionEvent event) {
        Emulator emulator = tableEmulators.getSelectionModel().getSelectedItem();
        if (emulator != null) {
            if (AppDialog.confirmationDialog("Eliminar emuladores",
                    "¿Estás seguro de que quieres borrar el emulador " + emulator.getName()
                            + "?")) {
                EmulatorService emulatorService = new ObjectdbEmulatorService();
                try {
                    emulatorService.remove(emulator);
                    AppDialog.messageDialog("Eliminar emuladores",
                            "Se ha eliminado el emulador " + emulator.getName() + " con éxito.");
                    showEmulators();
                    btnEditEmulator.setDisable(true);
                    btnDeleteEmulator.setDisable(true);
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    private Stage emulatorRegisterEditWindow(Stage stage, Emulator emulator) {
        Stage newStage = new Stage();
        return newStage;
    }
}
