package com.alan.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public final class AlertUtils {
    private AlertUtils(){
    }
    public static void showInfoMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info Message");
        alert.setHeaderText("Info");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showErrorMessage(String message){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Message");
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static Optional<ButtonType> showYesNoAlert(String message){
        Alert alert =
                new Alert(Alert.AlertType.WARNING,
                        message,
                        ButtonType.YES,
                        ButtonType.NO);
        alert.setTitle("FOUND XML");
        return alert.showAndWait();
    }
}
