package com.alan.discordapp;

import com.alan.businessLayer.LeaderboardManager;
import com.alan.businessLayer.SettingsManager;
import com.alan.businessLayer.UserManager;
import com.alan.models.User;
import com.alan.utils.ResolutionChangerUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField lastNameTextField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void login() throws IOException {

        if (firstNameTextField.getText().trim().isBlank() || lastNameTextField.getText().trim().isBlank()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Logging In");
            alert.setContentText("Please fill all information to log in");
            alert.show();
            return;
        }

        User loginUser = new User(firstNameTextField.getText(), lastNameTextField.getText());
        UserManager.setLoggedInUser(loginUser);
        LeaderboardManager.addNewUserToLeaderboard(loginUser);

        loadMainApplication();
    }

    private void loadMainApplication() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("gameScreen.fxml"));

        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 1200, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage stage = MainApplication.getMainStage();
        stage.setTitle("Fake Discord!");
        stage.setScene(scene);
        stage.centerOnScreen();
        ResolutionChangerUtil.changeResolution(SettingsManager.getCurrenSettings().getResolutionType());
        stage.show();
    }
}