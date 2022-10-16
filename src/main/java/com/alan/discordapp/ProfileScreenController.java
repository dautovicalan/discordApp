package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import com.alan.models.User;
import com.alan.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileScreenController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField forename;
    @FXML
    private TextField surname;
    @FXML
    private Button saveButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserManager.getLoggedInUser();
        username.setText(currentUser.getUserName());
        forename.setText(currentUser.getForename());
        surname.setText(currentUser.getSurname());
    }

    public void saveProfile() throws IOException {
        String newUsername, newForename, newSurname;
        newUsername = username.getText().trim();
        newForename = forename.getText().trim();
        newSurname = surname.getText().trim();

        User newUserInfo = new User(newUsername, newForename, newSurname);
        UserManager.setLoggedInUser(newUserInfo);
        AlertUtils.showInfoMessage("Succesfuly updated your profile");
    }
}
