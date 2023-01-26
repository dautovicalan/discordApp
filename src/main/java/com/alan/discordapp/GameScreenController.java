package com.alan.discordapp;

import com.alan.businessLayer.SettingsManager;
import com.alan.businessLayer.UserManager;
import com.alan.utils.AlertUtils;
import com.alan.utils.DocumentationUtils;
import com.alan.utils.ResolutionChangerUtil;
import com.alan.utils.XMLUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {
    @FXML
    private StackPane mainPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button statsButton;
    @FXML
    private Button profileButton;

    @FXML
    private Button rankButton;

    @FXML
    private Button settingsButton;
    @FXML
    private Button logoutButton;

    @FXML
    private MenuItem miGenerateDocumentation;

    @FXML
    private MenuItem miSaveToXml;

    @FXML
    private MenuItem miExit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupListeners();
    }

    private void setupListeners() {
        miSaveToXml.setOnAction(e -> {
            try {
                XMLUtils.saveConfigurationToXmlFile();
                AlertUtils.showInfoMessage("Saved XML Successfully!");
            } catch (Exception ex) {
                AlertUtils.showErrorMessage("Something went wrong while saving XML...");
                ex.printStackTrace();
            }
        });
        miGenerateDocumentation.setOnAction(e -> DocumentationUtils.generateDocumentation());
        miExit.setOnAction(e -> Platform.exit());
    }

    public void showHomeScene(){
        loadScene("homeScreen.fxml");
    }

    public void showStatsScene(){
        loadScene("statsScreen.fxml");
    }

    public void showProfileScene(){
        loadScene("profileScreen.fxml");
    }
    public void showRankScene() {
        loadScene("leaderboardScreen.fxml");
    }
    public void showSettingsScene() {
        loadScene("settingsScreen.fxml");
    }

    private void loadScene(String resource){
        Parent view = null;
        try {
            view = FXMLLoader.load(getClass().getResource(resource));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logout() {
        UserManager.logout();

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 500, 500);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = MainApplication.getMainStage();
        stage.setTitle("Fake Discord!");
        stage.setScene(scene);
        stage.centerOnScreen();
        ResolutionChangerUtil.changeResolution(SettingsManager.getCurrenSettings().getResolutionType());
        stage.show();
    }
}
