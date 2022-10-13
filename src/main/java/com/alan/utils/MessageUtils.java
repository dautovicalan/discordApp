package com.alan.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class MessageUtils {

    private MessageUtils(){

    }

    public static void showInfoMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("message");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
