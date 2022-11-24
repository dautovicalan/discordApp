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
            loggedInUser = user;
    }

    public static User getLoggedInUser() { return loggedInUser; }

    public static void saveUser(){

    }
}
