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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
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

        tableEmulators.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    // When a game is selected, enables edit and delete buttons
                    btnEditEmulator.setDisable(false);
                    btnDeleteEmulator.setDisable(false);
                });
    }

    // Reads emulators from database and they set into ObservableList
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

    // Show emulators in the emulators table
    private void showEmulators() {
        ObservableList<Emulator> emulators = getEmulatorList();

        colNameEmulator.setCellValueFactory(new PropertyValueFactory<Emulator, String>("name"));
        colPathEmulator.setCellValueFactory(new PropertyValueFactory<Emulator, String>("path"));

        tableEmulators.setItems(emulators);
        tableEmulators.refresh();
    }

    @FXML
    private void onClickBtnAddEmulator(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddEmulator.getScene().getWindow();
        emulatorRegisterWindow(stage).setOnHidden(e -> showEmulators());
    }

    @FXML
    private void onClickBtnEditEmulator(ActionEvent event) throws IOException {
        editEmulator();
    }

    @FXML
    private void onMouseClickedCol(MouseEvent event) throws IOException {
        Emulator emulator = tableEmulators.getSelectionModel().getSelectedItem();
        if (emulator != null) {
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
            emulatorEditWindow(stage, emulator).setOnHidden(e -> showEmulators());
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
                    // When a emulator is deleted, disables edit and delete buttons
                    btnEditEmulator.setDisable(true);
                    btnDeleteEmulator.setDisable(true);
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    // Shows emulator register window
    private Stage emulatorRegisterWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/emulatorregisterdialog.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Registro de emuladores");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    // Shows emulator edit window
    private Stage emulatorEditWindow(Stage stage, Emulator emulator) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/emulatoreditdialog.fxml"));
        Parent root = loader.load();
        EmulatorEditDialogController controller = loader.getController();
        controller.init(emulator);
        newStage.setScene(new Scene(root));
        newStage.setTitle("Edición de emuladores");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }
}
