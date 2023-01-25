package com.alan.discordapp;

import com.alan.businessLayer.LeaderboardManager;
import com.alan.utils.AlertUtils;
import com.alan.utils.XMLUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainApplication extends Application {

    private static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader;
        Scene scene;
        if (continueAsLastLoggedInUser()){
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("gameScreen.fxml"));
            System.out.println("Hello");
            scene = new Scene(fxmlLoader.load(), 1200, 600);
        } else {
            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
            scene = new Scene(fxmlLoader.load(), 500, 500);
        }
        stage.setTitle("Fake Discord!");
        stage.setScene(scene);
        LeaderboardManager.loadLeaderboardList();
        stage.show();
        mainStage = stage;
    }

    private boolean continueAsLastLoggedInUser() {
        try {
            if(Files.exists(Paths.get(XMLUtils.DIR + File.separator + "testXml.xml"))){
                if (AlertUtils.showYesNoAlert("Load existing config or continue as new user?").get() == ButtonType.YES) {
                    XMLUtils.loadConfigurationFromXmlFile();
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
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