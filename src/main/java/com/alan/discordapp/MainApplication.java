package com.alan.discordapp;

import com.alan.businessLayer.LeaderboardManager;
import com.alan.businessLayer.SettingsManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.ResolutionType;
import com.alan.models.Settings;
import com.alan.utils.XMLUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

public class MainApplication extends Application {

    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;
        Scene scene;
        if (continueAsLastLoggedInUser()){
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("gameScreen.fxml"));
            scene = new Scene(fxmlLoader.load(), 1200, 600);
        } else {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
            scene = new Scene(fxmlLoader.load(), 500, 500);
        }
        stage.setTitle("Fake Discord!");
        stage.setScene(scene);
        stage.show();
        mainStage = stage;
    }

    private boolean continueAsLastLoggedInUser() {
        try {
            if(Files.exists(Paths.get("testXml.xml"))){
                Alert alert =
                        new Alert(Alert.AlertType.WARNING,
                                "Load existing config or continue as new user?",
                                ButtonType.YES,
                                ButtonType.NO);
                alert.setTitle("FOUND XML");
                Optional<ButtonType> result = alert.showAndWait();

                if (result.get() == ButtonType.YES) {
                    XMLUtils.loadConfigurationFromXmlFile();
                    return true;
                }
            }
            return false;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Stage getMainStage() {return mainStage;}
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void stop() {
        try {
            LeaderboardManager.saveLeaderboard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}