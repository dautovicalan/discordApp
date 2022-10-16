package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import com.alan.models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField loginTexfField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public  void login() throws IOException {
        // errorLabel.setVisible(true);
        User loginUser = new User(loginTexfField.getText());


        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("gameScreen.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1200, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UserManager.setLoggedInUser(loginUser);
        Stage stage = MainApplication.getMainStage();
        stage.setTitle("Fake Discord!");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setMinWidth(1200);
        stage.setMinHeight(600);
        stage.show();
    }
}