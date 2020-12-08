package org.rmc.retrogameslibrary;

import java.sql.SQLException;
import java.util.Properties;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.service.jdbc.MysqlConnection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root;
        Properties properties = PropertiesConfig.readProperties();

        stage.setOnCloseRequest(event -> {
            event.consume();
            if (AppDialog.confirmationDialog("Salir de Retro Games Library",
                    "¿Estás seguro de que quieres salir de la aplicación?")) {
                try {
                    MysqlConnection.getInstance().closeConnection();
                } catch (SQLException e) {
                    AppDialog.warningDialog("Error de MySQL",
                            "No se ha podido cerrar la conexión a la base de datos de usuarios.");
                }
                Platform.exit();
            }
        });

        if (properties != null) {
            try {
                String host = properties.getProperty(PropertiesConfig.MYSQL_HOST);
                String user = properties.getProperty(PropertiesConfig.MYSQL_USER);
                String pass = properties.getProperty(PropertiesConfig.MYSQL_PASSWORD);
                String db = properties.getProperty(PropertiesConfig.MYSQL_DATABASE);
                MysqlConnection.getInstance().connect(host, user, pass, db);

                if (properties.getProperty(PropertiesConfig.CURRENT_USER) == null
                        || properties.getProperty(PropertiesConfig.CURRENT_USER).isEmpty()) {
                    root = FXMLLoader.load(getClass().getResource("/view/logindialog.fxml"));
                    stage.setTitle("Login");
                } else {
                    root = FXMLLoader.load(getClass().getResource("/view/mainwindow.fxml"));
                    stage.setTitle("Retro Games Library");
                }

                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (SQLException e) {
                AppDialog.errorDialog("User Database Error",
                        "No se ha podido conectar a la base de datos.",
                        "Comprueba que el servidor MySQL está corriendo.");
            }
        } else {
            root = FXMLLoader.load(getClass().getResource("/view/initdatabase.fxml"));

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Configuración de la base de datos");
            stage.show();
        }
    }
}
