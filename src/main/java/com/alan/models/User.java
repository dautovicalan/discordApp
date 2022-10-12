package com.alan.models;

public class User {
    private String userName;
    private String forename;
    private String surname;

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String forename, String surname) {
        this.userName = userName;
        this.forename = forename;
        this.surname = surname;
    }

    public String getForename() {
        return forename;
    }

    public String getUserName() {
        return userName;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return userName;
    }
}
