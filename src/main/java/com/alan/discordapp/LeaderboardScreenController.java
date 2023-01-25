package com.alan.discordapp;

import com.alan.businessLayer.LeaderboardManager;
import com.alan.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;

public class LeaderboardScreenController implements Initializable {

    @FXML
    private ListView<User> userListView;
    @FXML
    private Button btnSortAsc;
    @FXML
    private Button btnSortDesc;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupListeners();
        ObservableList<User> observableListUsers = FXCollections.observableList(
                LeaderboardManager.getListOfUsers());
        userListView.setItems(observableListUsers);
    }

    private void setupListeners() {
        btnSortAsc.setOnAction(actionEvent -> {
            Collections.sort(userListView.getItems());
        });
        btnSortDesc.setOnAction(actionEvent -> {
            Collections.reverse(userListView.getItems());
        });
    }
}
