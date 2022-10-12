package com.alan.businessLayer;

import com.alan.models.User;

public class UserManager {

    private UserManager(){

    }

    private static User loggedInUser;

    public static void setLoggedInUser(User user){
        loggedInUser = user;
    }

    public static User getLoggedInUser() { return loggedInUser; }
}
