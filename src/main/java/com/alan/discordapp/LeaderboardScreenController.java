package com.alan.discordapp;

import com.alan.businessLayer.LeaderboardManager;
import com.alan.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class LeaderboardScreenController implements Initializable {

    @FXML
    private ListView<User> userListView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            List<User> users = LeaderboardManager.loadLeaderboardList();
            ObservableList<User> observableListUsers = FXCollections.observableList(users);
            userListView.setItems(observableListUsers);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
