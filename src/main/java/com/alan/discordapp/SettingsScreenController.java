package com.alan.discordapp;

import com.alan.models.ResolutionType;
import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsScreenController implements Initializable {

    @FXML
    private ComboBox<ResolutionType> resolutionType;
    @FXML
    private Button changeResolution;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        resolutionType.setItems(FXCollections.observableArrayList(
                ResolutionType.FULLSCREEN,
                ResolutionType.MEDIUM,
                ResolutionType.SMALL
        ));
    }

    public void saveSettings(){
        return;
    }
}
