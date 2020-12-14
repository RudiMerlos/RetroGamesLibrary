package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.PlatformService;
import org.rmc.retrogameslibrary.service.hibernate.HibernatePlatformService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PlatformDialogController {

    @FXML
    private TableView<Platform> tablePlatforms;
    @FXML
    private TableColumn<Platform, String> colNamePlatform;
    @FXML
    private TableColumn<Platform, String> colModelPlatform;
    @FXML
    private TableColumn<Platform, String> colCompanyPlatform;
    @FXML
    private TableColumn<Platform, String> colCountryPlatform;
    @FXML
    private TableColumn<Platform, Integer> colYearPlatform;

    @FXML
    private Button btnAddPlatform;
    @FXML
    private Button btnEditPlatform;
    @FXML
    private Button btnDeletePlatform;

    @FXML
    public void initialize() {
        showPlatforms();
    }

    // Reads platforms from database and they set into ObservableList
    private ObservableList<Platform> getPlatformList() {
        PlatformService platformService = new HibernatePlatformService();
        ObservableList<Platform> platformList = FXCollections.observableArrayList();
        try {
            platformList.setAll(platformService.getAll());
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
        return platformList;
    }

    // Show platforms in the platforms table
    private void showPlatforms() {
        ObservableList<Platform> platforms = getPlatformList();

        colNamePlatform.setCellValueFactory(new PropertyValueFactory<Platform, String>("name"));
        colModelPlatform.setCellValueFactory(new PropertyValueFactory<Platform, String>("model"));
        colCompanyPlatform
                .setCellValueFactory(new PropertyValueFactory<Platform, String>("company"));
        colCountryPlatform
                .setCellValueFactory(new PropertyValueFactory<Platform, String>("country"));
        colYearPlatform.setCellValueFactory(new PropertyValueFactory<Platform, Integer>("year"));

        tablePlatforms.setItems(platforms);
        tablePlatforms.refresh();
    }

    @FXML
    private void onClickBtnAddPlatform(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddPlatform.getScene().getWindow();
        platformRegisterWindow(stage).setOnHidden(e -> showPlatforms());
    }

    @FXML
    private void onClickBtnEditPlatform(ActionEvent event) throws IOException {
        editPlatform();
    }

    @FXML
    private void onMouseClickedCol(MouseEvent event) throws IOException {
        Platform platform = tablePlatforms.getSelectionModel().getSelectedItem();
        if (platform != null) {
            btnEditPlatform.setDisable(false);
            btnDeletePlatform.setDisable(false);
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1)
                editPlatform();
        }
    }

    @FXML
    private void onKeyReleasedCol(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            Platform platform = tablePlatforms.getSelectionModel().getSelectedItem();
            if (platform != null)
                editPlatform();
        }
    }

    // Shows platform edit window with the selected platform and shows platforms updated when is
    // finished
    private void editPlatform() throws IOException {
        Platform platform = tablePlatforms.getSelectionModel().getSelectedItem();
        if (platform != null) {
            Stage stage = (Stage) btnEditPlatform.getScene().getWindow();
            platformEditWindow(stage, platform).setOnHidden(e -> showPlatforms());
        }
    }

    @FXML
    private void onClickBtnDeletePlatform(ActionEvent event) {
        Platform platform = tablePlatforms.getSelectionModel().getSelectedItem();
        if (platform != null) {
            if (AppDialog.confirmationDialog("Eliminar plataformas",
                    "¿Estás seguro de que quieres borrar la plataforma " + platform.getName() + " "
                            + platform.getModel() + "?",
                    "Ten en cuenta que esta acción eliminará todos los juegos asociados con esta "
                            + "plataforma.")) {
                PlatformService platformService = new HibernatePlatformService();
                try {
                    platformService.remove(platform);
                    AppDialog.messageDialog("Eliminar plataformas", "Se ha eliminado la plataforma "
                            + platform.getName() + " " + platform.getModel() + " con éxito.");
                    showPlatforms();
                    // When a platform is deleted, disables edit and delete buttons
                    btnEditPlatform.setDisable(true);
                    btnDeletePlatform.setDisable(true);
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    // Shows platform register window
    private Stage platformRegisterWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/platformregisterdialog.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Registro de plataformas");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    // Shows platform edit window
    private Stage platformEditWindow(Stage stage, Platform platform) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/platformeditdialog.fxml"));
        Parent root = loader.load();
        PlatformEditDialogController controller = loader.getController();
        controller.init(platform);
        newStage.setScene(new Scene(root));
        newStage.setTitle("Edición de plataformas");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }
}
