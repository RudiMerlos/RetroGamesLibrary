package org.rmc.retrogameslibrary;

import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.PlatformService;
import org.rmc.retrogameslibrary.service.hibernate.HibernateConnection;
import org.rmc.retrogameslibrary.service.hibernate.HibernatePlatformService;
import org.rmc.retrogameslibrary.service.jdbc.MysqlConnection;
import org.rmc.retrogameslibrary.service.objectdb.ObjectdbConnection;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static HostServices hostServices;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Get host services to access to default web browser
        hostServices = getHostServices();

        Parent root = null;

        // Handle events when application is closing
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
                HibernateConnection.getInstance().closeConnection();
                ObjectdbConnection.getInstance().closeConnection();
                javafx.application.Platform.exit();
            }
        });

        // Init database connections
        initPlatforms();
        initEmulators();

        // Read properties
        Properties properties = PropertiesConfig.readProperties();
        // If properties are not null, then try to set MySQL connection, if not, launches the login
        // window
        if (properties != null) {
            try {
                String host = properties.getProperty(PropertiesConfig.MYSQL_HOST);
                String user = properties.getProperty(PropertiesConfig.MYSQL_USER);
                String pass = properties.getProperty(PropertiesConfig.MYSQL_PASSWORD);
                String db = properties.getProperty(PropertiesConfig.MYSQL_DATABASE);
                MysqlConnection.getInstance().connect(host, user, pass, db);

                // If the current user property is not setted, launches the login window, if not,
                // launches the main window
                if (properties.getProperty(PropertiesConfig.CURRENT_USER) == null
                        || properties.getProperty(PropertiesConfig.CURRENT_USER).isEmpty()) {
                    root = FXMLLoader.load(getClass().getResource("/view/logindialog.fxml"));
                    stage.setTitle("Login");
                    stage.setResizable(false);
                } else {
                    root = FXMLLoader.load(getClass().getResource("/view/mainwindow.fxml"));
                    stage.setTitle("Retro Games Library");
                }
            } catch (SQLException e) {
                AppDialog.errorDialog("User Database Error",
                        "No se ha podido conectar a la base de datos.",
                        "Comprueba que el servidor MySQL está corriendo.");
            }
        } else {
            root = FXMLLoader.load(getClass().getResource("/view/initdatabase.fxml"));
            stage.setTitle("Configuración de la base de datos");
        }
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Get ObjectDB connection
    private void initEmulators() {
        ObjectdbConnection.getInstance().connect("emulatorsdb");
    }

    // Get Hibernate connection and fill platforms table with some values
    private void initPlatforms() {
        HibernateConnection.getInstance().connect();
        PlatformService platformService = new HibernatePlatformService();
        try {
            List<Platform> platforms = platformService.getAll();
            if (platforms == null || platforms.isEmpty())
                fillPlatforms();
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
    }

    // Insert some values into platform table
    private void fillPlatforms() {
        PlatformService platformService = new HibernatePlatformService();
        List<Platform> platforms = List.of(
            new Platform("Nintendo", "NES", "Nintendo Company Ltd", "Japón", 1983),
            new Platform("Sega", "Master System II", "Sega Corporation", "Japón", 1985),
            new Platform("Amstrad", "CPC 6128", "Amstrad", "UK", 1984),
            new Platform("Atari", "2600", "Atari Inc", "USA", 1977),
            new Platform("Spectrum", "ZX 128", "Sinclair Research Ltd", "UK", 1985)
        );
        platforms.forEach(p -> {
            try {
                platformService.insert(p);
            } catch (CrudException e) {
                AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
            }
        });
    }
}
