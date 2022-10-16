package com.alan.businessLayer;

import com.alan.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class UserManager {

    private static final String USER_FOLDER_PATH = "/AppUsers";

    private static User loggedInUser;

    public static void setLoggedInUser(User user) throws IOException {
        if (!Files.exists(Paths.get(USER_FOLDER_PATH, user.getUserName()))){
            Files.createDirectories(Paths.get(USER_FOLDER_PATH, user.getUserName()));
            Files.write(Paths.get(USER_FOLDER_PATH, user.getUserName(), "userInfo.txt"), user.prepareForConfig());
            loggedInUser = user;
        } else {
            User parseUser = User.parseFromFile(Paths.get(USER_FOLDER_PATH, user.getUserName(), "userInfo.txt"));
            loggedInUser = parseUser;
        }
    }

    public static User getLoggedInUser() { return loggedInUser; }

    public static void saveUser(){

    }
}
