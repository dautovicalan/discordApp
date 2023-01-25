package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class StatsScreenController implements Initializable {

    @FXML
    private Label messagesSentLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        messagesSentLabel.setText(
                "You sent: " + UserManager.getLoggedInUser().getMessagesSent() + " messages :)"
        );
    }
}
