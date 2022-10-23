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

    public User(String firstName) {
        this.firstName = firstName;
    }

    public User(String firstName, String lastName, Image userPicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPicture = userPicture;
    }

    public static User parseFromFile(Path path) {
        // TODO: 16.10.2022. IMPLEMENT TO END 
        return new User("mirko");
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
}
