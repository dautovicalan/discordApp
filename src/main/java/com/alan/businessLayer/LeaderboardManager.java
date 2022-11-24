package com.alan.businessLayer;

import com.alan.models.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class LeaderboardManager {

    private static final String LEADERBOARD_PATH = "leaderboard.txt";
    private LeaderboardManager(){

    }

    public static void saveUserToLeaderBoard(User user) throws IOException {
        Files.write(Paths.get(LEADERBOARD_PATH), user.prepareForLeaderBoard(), StandardOpenOption.APPEND);
    }
    public static List<User> loadLeaderboardList() throws IOException {

        File f = new File(LEADERBOARD_PATH);
        if(!f.exists() && !f.isDirectory()) {
            Files.createFile(f.toPath());
        }

        List<User> userList = new ArrayList<>();
        List<String> stringRepUsers = Files.readAllLines(Paths.get(LEADERBOARD_PATH));

        stringRepUsers.forEach(s -> {
            userList.add(User.parseFromFile(s));
        });

        return userList;
    }
}
