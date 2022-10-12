package com.alan.discordapp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatScreenController implements Initializable {

    @FXML
    private TextArea chatBox;
    @FXML
    private TextField textField;
    @FXML
    private Button sendButton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void sendMessage(){
        if (textField.getText().equals("")){
            return;
        }
        chatBox.appendText(textField.getText() + "\n");
        textField.clear();
    }
}
