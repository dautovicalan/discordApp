package com.alan.discordapp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {

    @FXML
    private ComboBox<String> resolutionType;
    @FXML
    private Button changeResolution;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void saveSettings(){
        return;
    }
}
