package com.alan.businessLayer;

import com.alan.models.User;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LeaderboardManager {

    private static final String LEADERBOARD_PATH = "leaderboard.txt";
    private LeaderboardManager(){

    }

    private static List<User> listOfUsers = new ArrayList<>();

    public static void addNewUserToLeaderboard(User user){
        listOfUsers.add(user);
        Collections.sort(listOfUsers);
    }
    public static void saveLeaderboard() throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        listOfUsers.forEach(us -> stringBuilder.append(us.prepareForLeaderBoard()));
        Files.write(Paths.get(LEADERBOARD_PATH), stringBuilder.toString().getBytes(), StandardOpenOption.APPEND);
    }
    public static List<User> loadLeaderboardList() throws IOException {

        if (listOfUsers.size() != 0){
            return listOfUsers;
        }

        File f = new File(LEADERBOARD_PATH);
        if(!f.exists() && !f.isDirectory()) {
            Files.createFile(f.toPath());
            return listOfUsers;
        }

        Files.readAllLines(Paths.get(LEADERBOARD_PATH)).forEach(s -> {
            listOfUsers.add(User.parseFromFile(s));
        });

        return listOfUsers;
    }
}
