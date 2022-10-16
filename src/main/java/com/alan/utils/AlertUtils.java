package com.alan.utils;

import javafx.scene.control.Alert;

public class AlertUtils {

    private AlertUtils(){

    }

    public static void showInfoMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("message");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
