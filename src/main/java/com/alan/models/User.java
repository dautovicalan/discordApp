package com.alan.models;

import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.nio.file.Path;
import java.util.UUID;

@XmlAccessorType(XmlAccessType.FIELD)
public class User implements Serializable {

    private final String DEL = "|";
    @XmlElement(name = "firstName")
    private String firstName;
    @XmlElement(name = "lastName")
    private String lastName;

    private Image userPicture;

    public Image getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(Image userPicture) {
        this.userPicture = userPicture;
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, Image userPicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPicture = userPicture;
    }

    public static User parseFromFile(String stringRep) {

        String[] s = stringRep.split("_");

        if (s.length == 0){
            return new User("No name", "No name");
        }
        return new User(s[0], s[1]);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public byte[] prepareForLeaderBoard() {
        return (firstName.trim() + "_" +  lastName.trim()).getBytes();
    }

    @Override
    public String toString() {
        return "Ja se zovem " +  firstName + " " + lastName + System.lineSeparator();
    }
}
