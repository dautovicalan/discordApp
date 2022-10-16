package com.alan.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.nio.file.Path;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
public class User {

    private final String DEL = "|";
    @XmlElement(name = "userName")
    private String userName;
    @XmlElement(name = "forename")
    private String forename;
    @XmlElement(name = "surname")
    private String surname;

    public User(String userName) {
        this.userName = userName;
    }

    public User(String userName, String forename, String surname) {
        this.userName = userName;
        this.forename = forename;
        this.surname = surname;
    }

    public static User parseFromFile(Path path) {
        // TODO: 16.10.2022. IMPLEMENT TO END 
        return new User("mirko");
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

    public String generateUniqueFolderName(){
        return this.forename + "_" + this.surname + "_" + UUID.randomUUID();
    }


    @Override
    public String toString() {
        return userName;
    }

    public byte[] prepareForConfig() {
        String userConfigPrepare = userName + DEL + forename + DEL + surname;
        return userConfigPrepare.getBytes();
    }
}
