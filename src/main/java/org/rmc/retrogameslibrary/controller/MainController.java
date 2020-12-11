package org.rmc.retrogameslibrary.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import org.rmc.retrogameslibrary.config.PropertiesConfig;
import org.rmc.retrogameslibrary.dialog.AppDialog;
import org.rmc.retrogameslibrary.model.Game;
import org.rmc.retrogameslibrary.repository.CrudException;
import org.rmc.retrogameslibrary.service.GameService;
import org.rmc.retrogameslibrary.service.hibernate.HibernateGameService;
import org.rmc.retrogameslibrary.service.jdbc.MysqlConnection;
import javafx.application.Platform;
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
import javafx.scene.image.ImageView;
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
        showGames();
    }

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

    private void showGames() {
        ObservableList<Game> games = getGameList();

        colTitleGame.setCellValueFactory(new PropertyValueFactory<Game, String>("title"));
        colPlatformGame.setCellValueFactory(new PropertyValueFactory<Game, String>("platform"));
        colYearGame.setCellValueFactory(new PropertyValueFactory<Game, Integer>("year"));
        colGenderGame.setCellValueFactory(new PropertyValueFactory<Game, String>("gender"));

        tableGames.setItems(games);
        tableGames.refresh();
    }

    @FXML
    private void onClickAddNewGame(ActionEvent event) throws IOException {
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        gameRegisterWindow(stage).setOnHidden(e -> showGames());
    }

    @FXML
    private void onClickImport(ActionEvent event) {

    }

    @FXML
    private void onClickExport(ActionEvent event) {

    }

    @FXML
    private void onClickSessionClose(ActionEvent event) throws IOException {
        PropertiesConfig.writeCurrentUserProperties("");
        Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/view/logindialog.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
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
            Platform.exit();
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
            menuEditGame.setDisable(false);
            menuDelete.setDisable(false);
            if (event.getClickCount() > 1)
                editGame();
        }
    }

    private void editGame() throws IOException {
        Game game = tableGames.getSelectionModel().getSelectedItem();
        if (game != null) {
            Stage stage = (Stage) btnAddNewGame.getScene().getWindow();
            gameEditWindow(stage, game).setOnHidden(e -> showGames());
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
                    showGames();
                    menuEditGame.setDisable(true);
                    menuDelete.setDisable(true);
                } catch (CrudException e) {
                    AppDialog.errorDialog(e.getMessage(), e.getCause().toString());
                }
            }
        }
    }

    @FXML
    private void onClickPlatforms(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) btnAddNewGame.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initOwner(thisStage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/platformdialog.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Platforms");
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onClickUsers(ActionEvent event) throws IOException {
        Stage thisStage = (Stage) btnAddNewGame.getScene().getWindow();
        Stage newStage = new Stage();
        newStage.initOwner(thisStage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/usersdialog.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Users");
        newStage.setResizable(false);
        newStage.show();
    }

    @FXML
    private void onClickEmulators(ActionEvent event) {

    }

    @FXML
    private void onClickPreferences(ActionEvent event) {

    }

    @FXML
    private void onClickAbout(ActionEvent event) {

    }

    @FXML
    private void onClickPlayGame(ActionEvent event) {

    }

    @FXML
    private void onTextChangedTxtSearch(ActionEvent event) {

    }

    private Stage gameRegisterWindow(Stage stage) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/view/gameregisterdialog.fxml"));
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Registro de juegos");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }

    private Stage gameEditWindow(Stage stage, Game game) throws IOException {
        Stage newStage = new Stage();
        newStage.initOwner(stage);
        newStage.initModality(Modality.WINDOW_MODAL);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/gameeditdialog.fxml"));
        Parent root = loader.load();
        GameEditDialogController controller = loader.getController();
        controller.init(game);
        Scene scene = new Scene(root);
        newStage.setScene(scene);
        newStage.setTitle("Edición de juegos");
        newStage.setResizable(false);
        newStage.show();
        return newStage;
    }
}
