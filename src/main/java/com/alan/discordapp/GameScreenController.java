package com.alan.discordapp;

import com.alan.businessLayer.SettingsManager;
import com.alan.businessLayer.UserManager;
import com.alan.utils.DocumentationUtils;
import com.alan.utils.ResolutionChangerUtil;
import com.alan.utils.XMLUtils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
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

    private static final String CLASS_EXTENSION = ".class";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        miSaveToXml.setOnAction(e -> {
            try {
                XMLUtils.saveConfigurationToXmlFile();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("XML Success");
                alert.setContentText("Saved to XML successfully");
                alert.show();
            } catch (ParserConfigurationException ex) {
                ex.printStackTrace();
            } catch (TransformerException ex) {
                ex.printStackTrace();
            }
        });

    }

    public void showHomeScene(){
        try {
            Parent homeView = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(homeView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStatsScene(){
        try {
            Parent statsView = FXMLLoader.load(getClass().getResource("statsScreen.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(statsView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showProfileScene(){
        try {
            Parent profileView = FXMLLoader.load(getClass().getResource("profileScreen.fxml"));
            mainPane.getChildren().removeAll();
            mainPane.getChildren().setAll(profileView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void showRankScene() throws IOException {
        Parent settingView = FXMLLoader.load(getClass().getResource("leaderboardScreen.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().setAll(settingView);
    }
    public void showSettingsScene() throws IOException {
        Parent settingView = FXMLLoader.load(getClass().getResource("settingsScreen.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().setAll(settingView);
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

    public void generateDocumentation() {
        DocumentationUtils.generateDocumentation();
    }

    public void exitApplication(){
        Platform.exit();
    }
}
