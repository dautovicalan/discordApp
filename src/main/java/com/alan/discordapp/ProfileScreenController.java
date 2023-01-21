package com.alan.discordapp;

import com.alan.businessLayer.UserManager;
import com.alan.models.User;
import com.alan.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileScreenController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField forename;
    @FXML
    private ImageView profilePicture;
    @FXML
    private Button changePicture;
    @FXML
    private Button saveButton;

    private final FileChooser fileChooser = new FileChooser();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User currentUser = UserManager.getLoggedInUser();
        profilePicture.setImage(currentUser.getUserPicture());
        username.setText(currentUser.getFirstName());
        forename.setText(currentUser.getLastName());
    }

    public void changePicture(){
        fileChooser.setTitle("Select Profile Picture");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*jpg"));
        File chosenFile = fileChooser.showOpenDialog(null);

        if (chosenFile != null){
            Image image = new Image(chosenFile.toURI().toString());
            profilePicture.setImage(image);
        }
    }

    public void saveProfile() throws IOException {
        String newUsername, newForename;
        newUsername = username.getText().trim();
        newForename = forename.getText().trim();

        UserManager.getLoggedInUser()
                        .updateUserProfile(newUsername, newForename, profilePicture.getImage());
        AlertUtils.showInfoMessage("Succesfuly updated your profile");
    }
}
