package org.rmc.retrogameslibrary.controller;

import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.PlatformService;
import org.rmc.retrogameslibrary.service.hibernate.HibernatePlatformService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PlatformEditDialogController {

    @FXML
    private TextField txtName;
    @FXML
    private TextField txtModel;
    @FXML
    private TextField txtCompany;
    @FXML
    private TextField txtCountry;
    @FXML
    private TextField txtYear;

    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;

    private Platform platform;

    // Sets the platform to edit
    public void init(Platform platform) {
        this.platform = platform;
        if (platform == null) {
            Stage stage = (Stage) txtName.getScene().getWindow();
            stage.close();
        }
        txtName.setText(platform.getName());
        txtModel.setText(platform.getModel());
        txtCompany.setText(platform.getCompany());
        txtCountry.setText(platform.getCountry());
        txtYear.setText(String.valueOf(platform.getYear()));
    }

    @FXML
    private void onClickBtnSave(ActionEvent event) {
        String name = txtName.getText();
        String model = txtModel.getText();
        String company = txtCompany.getText();
        String country = txtCountry.getText();
        String strYear = txtYear.getText();

        if (!name.isEmpty() && !model.isEmpty() && !company.isEmpty() && !strYear.isEmpty()) {
            if (!yearIsValid(strYear)) {
                AppDialog.errorDialog("Error en el año", "Debes de introducir un año válido.");
            } else {
                int year = Integer.parseInt(strYear);
                PlatformService platformService = new HibernatePlatformService();
                try {
                    platform.setName(name);
                    platform.setModel(model);
                    platform.setCompany(company);
                    platform.setCountry(country);
                    platform.setYear(year);
                    platformService.modify(platform);
                    AppDialog.messageDialog("Edición de plataformas",
                            "Plataforma " + name + " " + model + " modificada con éxito.");
                    Stage stage = (Stage) btnSave.getScene().getWindow();
                    stage.close();
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        } else {
            AppDialog.errorDialog("Error en la edición de plataformas",
                    "Debes rellenar los campos obligatorios.");
        }
    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    // Checks if year is valid
    private boolean yearIsValid(String year) {
        String regex = "^[12][0-9]{3}$";
        return year.matches(regex);
    }
}
