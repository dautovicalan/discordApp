package com.alan.models;

import javafx.scene.image.Image;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.*;
import java.nio.file.Path;
import java.util.UUID;

public class User implements Serializable, Externalizable {

    private static final long serialVersionUID = 5L;

    private final String DEL = "|";

    public User(){
    }

    private long userId;
    private String firstName;
    private String lastName;

    private Image userPicture;

    public Image getUserPicture() {
        return userPicture;
    }

    public void setUserPicture(Image userPicture) {
        this.userPicture = userPicture;
    }

    public User(String firstName, String lastName) {
        this.userId = ProcessHandle.current().pid();
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String firstName, String lastName, Image userPicture) {
        this.userId = ProcessHandle.current().pid();
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

    public long getUserId(){ return userId; }

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
        return firstName + " " + lastName;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeLong(userId);
        out.writeUTF(firstName);
        out.writeUTF(lastName);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        userId = in.readLong();
        firstName = in.readUTF();
        lastName = in.readUTF();
    }
}
