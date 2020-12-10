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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

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

    private void showPlatforms() {
        ObservableList<Platform> platforms = getPlatformList();

        colNamePlatform.setCellValueFactory(new PropertyValueFactory<Platform, String>("name"));
        colModelPlatform.setCellValueFactory(new PropertyValueFactory<Platform, String>("model"));
        colCompanyPlatform.setCellValueFactory(new PropertyValueFactory<Platform, String>("company"));
        colYearPlatform.setCellValueFactory(new PropertyValueFactory<Platform, Integer>("year"));

        tablePlatforms.setItems(platforms);
        tablePlatforms.refresh();
    }

    @FXML
    private void onClickBtnAddPlatform(ActionEvent event) {
    }

    @FXML
    private void onClickBtnEditPlatform(ActionEvent event) {

    }

    @FXML
    private void onMouseClickedCol(MouseEvent event) throws IOException {

    }

    @FXML
    private void onClickBtnDeletePlatform(ActionEvent event) {

    }
}
