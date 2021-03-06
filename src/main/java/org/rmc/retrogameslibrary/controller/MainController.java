package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Emulator;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.model.Platform;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.EmulatorService;
import org.rmc.retrogameslibrary.service.GameService;
import org.rmc.retrogameslibrary.service.PlatformService;
import org.rmc.retrogameslibrary.service.hibernate.HibernateGameService;
import org.rmc.retrogameslibrary.service.hibernate.HibernatePlatformService;
import org.rmc.retrogameslibrary.service.jdbc.MysqlConnection;
import org.rmc.retrogameslibrary.service.mongo.MongoConnection;
import org.rmc.retrogameslibrary.service.mongo.MongoEmulatorService;
import org.rmc.retrogameslibrary.service.mongo.MongoGameService;
import org.rmc.retrogameslibrary.service.mongo.MongoPlatformService;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainController {

    @FXML
    private MenuItem menuAddNewGame;
    @FXML
    private MenuItem menuImport;
    @FXML
    private MenuItem menuExport;
    @FXML
    private MenuItem menuSessionClose;
    @FXML
    private MenuItem menuQuit;
    @FXML
    private MenuItem menuEditGame;
    @FXML
    private MenuItem menuDelete;
    @FXML
    private MenuItem menuPlatforms;
    @FXML
    private MenuItem menuUsers;
    @FXML
    private MenuItem menuEmulators;
    @FXML
    private MenuItem menuPreferences;
    @FXML
    private MenuItem menuAbout;

    @FXML
    private Button btnAddNewGame;
    @FXML
    private Button btnPlayGame;
    @FXML
    private Label lblUser;
    @FXML
    private TextField txtSearch;

    @FXML
    private TableView<Game> tableGames;
    @FXML
    private TableColumn<Game, String> colTitleGame;
    @FXML
    private TableColumn<Game, String> colPlatformGame;
    @FXML
    private TableColumn<Game, Integer> colYearGame;
    @FXML
    private TableColumn<Game, String> colGenderGame;

    @FXML
    private ImageView imgPhotoDetails;
    @FXML
    private Label lblTitleDetails;
    @FXML
    private Label lblPlatformDetails;
    @FXML
    private Label lblGenderDetails;
    @FXML
    private Label lblYearDetails;
    @FXML
    private Label lblDescriptionDetails;

    @FXML
    public void initialize() {
        Properties properties = PropertiesConfig.readProperties();
        lblUser.setText(properties.getProperty(PropertiesConfig.CURRENT_USER));

        tableGames.setPlaceholder(new Label("No hay juegos disponibles."));
        showGames(getGameList());

        // Shows game details when it is selected
        tableGames.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    showDetails(newValue);
                    // When a game is selected, enables edit and delete buttons
                    menuEditGame.setDisable(false);
                    menuDelete.setDisable(false);
                });

        // Event listener to text search
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            HibernateGameService gameService = new HibernateGameService();
            try {
                ObservableList<Game> gameList = FXCollections.observableArrayList();
                gameList.setAll(gameService.searchByTitle("%" + newValue + "%"));
                showGames(gameList);
            } catch (CrudException e) {
                AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
            }
        });
    }

    // Reads games from database and they set into ObservableList
    private ObservableList<Game> getGameList() {
        GameService gameService = new HibernateGameService();
        ObservableList<Game> gameList = FXCollections.observableArrayList();
        try {
            gameList.setAll(gameService.getAll());
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
        return gameList;
    }

    // Show games in the games table
    private void showGames(ObservableList<Game> games) {
        colTitleGame.setCellValueFactory(new PropertyValueFactory<Game, String>("title"));
        colPlatformGame.setCellValueFactory(new PropertyValueFactory<Game, String>("platform"));
        colYearGame.setCellValueFactory(new PropertyValueFactory<Game, Integer>("year"));
        colGenderGame.setCellValueFactory(new PropertyValueFactory<Game, String>("gender"));

        tableGames.setItems(games);
        tableGames.refresh();
    }

    // Shows game details in the right panel
    private void showDetails(Game game) {
        resetDetails();
        if (game != null) {
            try {
                imgPhotoDetails
                        .setImage(new Image(Files.newInputStream(Paths.get(game.getScreenshot()))));
            } catch (Exception e) {
                imgPhotoDetails.setImage(null);
            }
            lblTitleDetails.setText(game.getTitle());
            lblPlatformDetails.setText(game.getPlatform().toString());
            lblGenderDetails.setText(game.getGender());
            lblYearDetails.setText(game.getYear() == null ? "" : game.getYear().toString());
            lblDescriptionDetails.setText(game.getDescription());
        }
    }

    // Reset details in the right panel
    private void resetDetails() {
        imgPhotoDetails.setImage(null);
        lblTitleDetails.setText("");
        lblPlatformDetails.setText("");
        lblGenderDetails.setText("");
        lblYearDetails.setText("");
        lblDescriptionDetails.setText("");
    }

    @FXML
    private void onClickAddNewGame(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        gameRegisterWindow(stage).setOnHidden(e -> showGames(getGameList()));
    }

    @FXML
    private void onClickImport(ActionEvent event) {
        // Get connection
        try {
            MongoConnection.getInstance().connect("localhost", 27017, "retrodb");
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
            return;
        }

        GameService gameService = null;
        PlatformService platformService = null;
        EmulatorService emulatorService = null;

        // Delete data
        gameService = new HibernateGameService();
        platformService = new HibernatePlatformService();
        emulatorService = new ObjectdbEmulatorService();
        try {
            emulatorService.removeAll();
            gameService.removeAll();
            platformService.removeAll();
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
            return;
        }

        // Read data from MongoDB
        gameService = new MongoGameService();
        platformService = new MongoPlatformService();
        emulatorService = new MongoEmulatorService();

        List<Game> games = null;
        List<Platform> platforms = null;
        List<Emulator> emulators = null;
        try {
            games = gameService.getAll();
            platforms = platformService.getAll();
            emulators = emulatorService.getAll();
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
            return;
        }

        // Save data into H2 and ObjectDB
        gameService = new HibernateGameService();
        platformService = new HibernatePlatformService();
        emulatorService = new ObjectdbEmulatorService();

        try {
            for (Platform platform : platforms)
                platformService.insert(platform);
            for (Game game : games)
                gameService.insert(game);
            for (Emulator emulator : emulators)
                emulatorService.insert(emulator);
            AppDialog.messageDialog("Importar colección",
                    "La colección se ha importado correctamente.");
            showGames(getGameList());
        } catch (CrudException e) {
            AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
        }
    }

    @FXML
    private void onClickExport(ActionEvent event) {
        if (AppDialog.confirmationDialog("Exportar colección",
                "La siguiente acción sustituirá cualquier otra colección que\n"
                        + "hubieras exportado anteriormente.",
                "¿Deseas continuar?")) {
            // Get connection
            try {
                MongoConnection.getInstance().connect("localhost", 27017, "retrodb");
            } catch (CrudException e) {
                AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                return;
            }

            GameService gameService = null;
            PlatformService platformService = null;
            EmulatorService emulatorService = null;

            // Drop collections
            gameService = new MongoGameService();
            platformService = new MongoPlatformService();
            emulatorService = new MongoEmulatorService();
            try {
                gameService.removeAll();
                platformService.removeAll();
                emulatorService.removeAll();
            } catch (CrudException e) {
                AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                return;
            }

            // Get data from application
            gameService = new HibernateGameService();
            platformService = new HibernatePlatformService();
            emulatorService = new ObjectdbEmulatorService();

            List<Game> games = null;
            List<Platform> platforms = null;
            List<Emulator> emulators = null;
            try {
                games = gameService.getAll();
                platforms = platformService.getAll();
                emulators = emulatorService.getAll();
            } catch (CrudException e) {
                AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                return;
            }

            // Save data into MongoDB
            gameService = new MongoGameService();
            platformService = new MongoPlatformService();
            emulatorService = new MongoEmulatorService();

            try {
                for (Game game : games)
                    gameService.insert(game);
                for (Platform platform : platforms)
                    platformService.insert(platform);
                for (Emulator emulator : emulators)
                    emulatorService.insert(emulator);
                AppDialog.messageDialog("Exportar colección",
                        "La colección se ha exportado correctamente.");
            } catch (CrudException e) {
                AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
            }
        }
    }

    @FXML
    private void onClickSessionClose(ActionEvent event) throws IOException {
        PropertiesConfig.writeCurrentUserProperties("");
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/logindialog.fxml"));
        stage.setScene(new Scene(root));
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.show();
    }

    @FXML
    private void onClickQuit(ActionEvent event) {
        if (AppDialog.confirmationDialog("Salir de Retro Games Library",
                "¿Estás seguro de que quieres salir de la aplicación?")) {
            try {
                MysqlConnection.getInstance().closeConnection();
            } catch (SQLException e) {
                AppDialog.warningDialog("Error de MySQL",
                        "No se ha podido cerrar la conexión a la base de datos de usuarios.");
            }
            javafx.application.Platform.exit();
        }
    }

    @FXML
    private void onClickEditGame(ActionEvent event) throws IOException {
        editGame();
    }

    @FXML
    private void onMouseClickedCol(MouseEvent event) throws IOException {
        Game game = tableGames.getSelectionModel().getSelectedItem();
        if (game != null) {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() > 1)
                editGame();
        }
    }

    @FXML
    private void onKeyReleasedCol(KeyEvent event) throws IOException {
        if (event.getCode() == KeyCode.ENTER) {
            Game game = tableGames.getSelectionModel().getSelectedItem();
            if (game != null)
                editGame();
        }
    }

    // Shows game edit window with the selected game and shows games updated when is finished
    private void editGame() throws IOException {
        Game game = tableGames.getSelectionModel().getSelectedItem();
        if (game != null) {
            Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
            gameEditWindow(stage, game).setOnHidden(e -> showGames(getGameList()));
        }
    }

    @FXML
    private void onClickDeleteGame(ActionEvent event) {
        Game game = tableGames.getSelectionModel().getSelectedItem();
        if (game != null) {
            if (AppDialog.confirmationDialog("Eliminar juegos",
                    "¿Estás seguro de que quieres borrar el juego " + game.getTitle() + "?")) {
                GameService gameService = new HibernateGameService();
                try {
                    gameService.remove(game);
                    AppDialog.messageDialog("Eliminar juegos",
                            "Se ha eliminado el juego " + game.getTitle() + " con éxito.");
                    menuEditGame.setDisable(true);
                    menuDelete.setDisable(true);
                    showGames(getGameList());
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    @FXML
    private void onClickPlatforms(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        platformWindow(stage).setOnHidden(e -> showGames(getGameList()));
    }

    // Shows platforms window
    private Stage platformWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/platformdialog.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Platforms");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    @FXML
    private void onClickUsers(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) btnAddNewGame.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initOwner(thisStage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/usersdialog.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Users");
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onClickEmulators(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        emulatorWindow(stage).setOnHidden(e -> showGames(getGameList()));
    }

    // Shows emulators window
    private Stage emulatorWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/emulatordialog.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Emulators");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    @FXML
    private void onClickPreferences(ActionEvent event) {

    }

    @FXML
    private void onClickAbout(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/aboutwindow.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Acerca de Retro Games Library");
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onClickPlayGame(ActionEvent event) {

    }

    // Shows game register window
    private Stage gameRegisterWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/gameregisterdialog.fxml"));
        newStage.setScene(new Scene(root));
        newStage.setTitle("Registro de juegos");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    // Shows game edit window
    private Stage gameEditWindow(Stage stage, Game game) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameeditdialog.fxml"));
        Parent root = loader.load();
        GameEditDialogController controller = loader.getController();
        controller.init(game);
        newStage.setScene(new Scene(root));
        newStage.setTitle("Edición de juegos");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }
}
