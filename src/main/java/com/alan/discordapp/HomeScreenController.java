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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showChatScene(){
        try {
            AnchorPane chatView = FXMLLoader.load(getClass().getResource("chatScreen.fxml"));
            mainPane.setCenter(chatView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void showVoiceScene(){
        try{
            AnchorPane voiceView = FXMLLoader.load(getClass().getResource("voiceScreen.fxml"));
            mainPane.setCenter(voiceView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
