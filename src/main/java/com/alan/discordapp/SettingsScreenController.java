package com.alan.discordapp;

import com.alan.businessLayer.SettingsManager;
import com.alan.models.ResolutionType;
import com.alan.utils.ResolutionChangerUtil;
import javafx.collections.FXCollections;
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
        prepareView();
    }

    private void prepareView() {
        resolutionType.setItems(FXCollections.observableArrayList(
                ResolutionType.FULLSCREEN,
                ResolutionType.MEDIUM,
                ResolutionType.SMALL
        ));
        resolutionType
                .getSelectionModel()
                .select(
                        SettingsManager
                                .getCurrenSettings()
                                .getResolutionType());
    }

    public void saveSettings(){
        ResolutionChangerUtil.changeResolution(resolutionType.getSelectionModel().getSelectedItem());
    }
}
