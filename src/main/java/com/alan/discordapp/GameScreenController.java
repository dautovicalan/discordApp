package com.alan.discordapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
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
    private Button settingsButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

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
    public void showSettingsScene() throws IOException {
        Parent settingView = FXMLLoader.load(getClass().getResource("settingsScreen.fxml"));
        mainPane.getChildren().removeAll();
        mainPane.getChildren().setAll(settingView);
    }
}
