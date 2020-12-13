package org.rmc.retrogameslibrary.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import org.rmc.retrogameslibrary.dialog.AppDialog;

public class PropertiesConfig {

    // Path to properties file
    private static final String PROPERTIES_FILE = "./config.conf";
    private static final Path PROPERTIES_PATH = Paths.get(PROPERTIES_FILE);

    // Current user
    public static final String CURRENT_USER = "current_user";

    // Users database properties
    public static final String MYSQL_HOST = "mysql_host";
    public static final String MYSQL_USER = "mysql_user";
    public static final String MYSQL_PASSWORD = "mysql_password";
    public static final String MYSQL_DATABASE = "mysql_datatbase";

    // FileChooser last paths properties
    public static final String GAME_ROM_LAST_PATH = "game_rom_last_path";
    public static final String GAME_IMG_LAST_PATH = "game_img_last_path";
    public static final String EMULATOR_LAST_PATH = "emulator_last_path";

    private static Properties properties = new Properties();

    // Checks if users database properties are setted
    public static boolean thereIsDatabaseProperties() {
        boolean result = false;
        if (propertiesFileExists()) {
            try {
                properties.load(Files.newInputStream(PROPERTIES_PATH));
                result = properties.getProperty(MYSQL_HOST) != null
                        && properties.getProperty(MYSQL_USER) != null
                        && properties.getProperty(MYSQL_PASSWORD) != null
                        && properties.getProperty(MYSQL_DATABASE) != null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    // Writes properties into properties file
    private static void saveProperties() {
        try {
            properties.store(Files.newOutputStream(PROPERTIES_PATH),
                    "Retro Games Library Properties");
        } catch (IOException e) {
            AppDialog.errorDialog("Error al guardar la configuración", e.getCause().toString());
        }
    }

    // Sets users database properties
    public static void writeDatabaseProperties(String host, String user, String pass, String db) {
        properties.setProperty(MYSQL_HOST, host);
        properties.setProperty(MYSQL_USER, user);
        properties.setProperty(MYSQL_PASSWORD, pass);
        properties.setProperty(MYSQL_DATABASE, db);
        saveProperties();
    }

    // Sets current user properties
    public static void writeCurrentUserProperties(String currentUser) {
        properties.setProperty(CURRENT_USER, currentUser);
        saveProperties();
    }

    // Sets FileChooser game rom last path
    public static void writeGameLastPathProperties(String lastPath) {
        properties.setProperty(GAME_ROM_LAST_PATH, lastPath);
        saveProperties();
    }

    // Sets FileChooser game img last path
    public static void writeImgLastPathProperites(String lastPath) {
        properties.setProperty(GAME_IMG_LAST_PATH, lastPath);
        saveProperties();
    }

    // Sets FileChooser emulator last path
    public static void writeEmulatorLastPathProperites(String lastPath) {
        properties.setProperty(EMULATOR_LAST_PATH, lastPath);
        saveProperties();
    }

    // Reads properties
    // If they has been set it returns the properties, if not, it returns null
    public static Properties readProperties() {
        if (thereIsDatabaseProperties())
            return properties;
        return null;
    }

    // Checks if properties file exists
    private static boolean propertiesFileExists() {
        return Files.exists(PROPERTIES_PATH);
    }
}
