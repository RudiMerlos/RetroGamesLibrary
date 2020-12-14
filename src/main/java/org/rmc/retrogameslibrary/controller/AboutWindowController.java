package org.rmc.retrogameslibrary.controller;

import org.rmc.retrogameslibrary.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

public class AboutWindowController {

    @FXML
    private Hyperlink hyperlink;
    @FXML
    private Button btnClose;

    @FXML
    private void onClickHyperlink(ActionEvent event) {
        // Shows hyperlink url in the user default browser
        Main.hostServices.showDocument(hyperlink.getAccessibleText());
    }

    @FXML
    private void onClickBtnClose(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }
}
