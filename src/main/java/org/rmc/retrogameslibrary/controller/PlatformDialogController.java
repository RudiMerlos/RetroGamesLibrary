package org.rmc.retrogameslibrary.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

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

    }

    @FXML
    private void onClickBtnAddPlatform(ActionEvent event) {

    }

    @FXML
    private void onClickBtnEditPlatform(ActionEvent event) {

    }

    @FXML
    private void onClickBtnDeletePlatform(ActionEvent event) {

    }
}
