package com.alan.discordapp;

import com.alan.utils.CameraCapture;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.ResourceBundle;

public class CameraScreenController implements Initializable {

    @FXML
    private ImageView ivVideo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
    public static void addBufferedImage(Image image, ImageView imageView) {
        Platform.runLater(() -> imageView.setImage(image));
    }

    public void startCamera(){
    }

}
