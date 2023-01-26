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

public class HomeScreenController implements Initializable {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button talkButton;

    @FXML
    private Button chatButton;

    @FXML
    private Button sendPictureButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showChatScene(){
        mainPane.setCenter(loadScene("chatScreen.fxml"));
    }

    public void showVoiceScene(){
        mainPane.setCenter(loadScene("voiceScreen.fxml"));
    }

    public void showSendPictureScene(){
        mainPane.setCenter(loadScene("sendPictureScreen.fxml"));
    }

    public void showVideoScene(){
        mainPane.setCenter(loadScene("cameraScreen.fxml"));
    }

    private AnchorPane loadScene(String resource) {
        AnchorPane scene = null;
        try {
            scene = FXMLLoader.load(getClass().getResource(resource));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return scene;
    }
}
