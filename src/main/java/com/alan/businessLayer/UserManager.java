package com.alan.businessLayer;

import com.alan.models.User;

import java.io.IOException;

public class UserManager {

    private static final String USER_FOLDER_PATH = "/AppUsers";

    private static User loggedInUser;

    public static void setLoggedInUser(User user) throws IOException {
            loggedInUser = user;
    }

    public static User getLoggedInUser() { return loggedInUser; }

    public static void logout(){
        if (loggedInUser != null) {
            loggedInUser = null;
        }
    }

    public static void saveUser(){

    }
}
