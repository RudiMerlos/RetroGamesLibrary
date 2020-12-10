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

public class PlatformRegisterDialogController {

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
    private Button btnOk;
    @FXML
    private Button btnCancel;

    @FXML
    private void onClickBtnOk(ActionEvent event) {
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
                Platform platform = new Platform(name, model, company, country, year);
                PlatformService platformService = new HibernatePlatformService();
                try {
                    platformService.insert(platform);
                    AppDialog.messageDialog("Creación de plataformas",
                            "Plataforma " + name + " " + model + " creada con éxito.");
                    Stage stage = (Stage) btnOk.getScene().getWindow();
                    stage.close();
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    @FXML
    private void onClickBtnCancel(ActionEvent event) {
        Stage stage = (Stage) btnCancel.getScene().getWindow();
        stage.close();
    }

    private boolean yearIsValid(String year) {
        String regex = "^[12][0-9]{3}$";
        return year.matches(regex);
    }
}
