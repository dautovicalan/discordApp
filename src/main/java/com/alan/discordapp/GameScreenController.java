package com.alan.discordapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {

    @FXML
    private BorderPane mainPane;
    @FXML
    private Button homeButton;
    @FXML
    private Button statsButton;
    @FXML
    private Button neznamButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showHomeScene(){
        try {
            AnchorPane homeView = FXMLLoader.load(getClass().getResource("homeScreen.fxml"));
            mainPane.setCenter(homeView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStatsScene(){
        AnchorPane statsView = null;
        try {
            statsView = FXMLLoader.load(getClass().getResource("statsScreen.fxml"));
            mainPane.setCenter(statsView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
