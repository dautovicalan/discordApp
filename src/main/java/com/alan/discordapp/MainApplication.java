package com.alan.discordapp;

import com.alan.businessLayer.LeaderboardManager;
import com.alan.businessLayer.SettingsManager;
import com.alan.models.ResolutionType;
import com.alan.models.Settings;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainApplication extends Application {

    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        SettingsManager.loadSettings();



        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 500, 500);
        stage.setTitle("Fake Discord!");
        stage.setScene(scene);
        stage.show();
        mainStage = stage;
    }

    public static Stage getMainStage() {return mainStage;}
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() throws Exception {
        SettingsManager.saveSettings();
    }
}