package com.alan.models;

import javafx.scene.image.Image;

import java.io.*;
import java.util.Objects;

public class User implements Serializable, Externalizable, Comparable<User> {

    private static final long serialVersionUID = 5L;

    public User(){
    }
    private long userId;
    private String firstName;
    private String lastName;
    private int messagesSent;

    public int getMessagesSent() {
        return messagesSent;
    }

    public void addMessage() {
        this.messagesSent++;
    }

    public void setMessagesSent(int messagesSent) {
        this.messagesSent = messagesSent;
    }

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

    public void updateUserProfile(String firstName, String lastName, Image userPicture) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userPicture = userPicture;
    }

    public static User parseFromFile(String stringRep) {

        String[] arrayOfUserInfo = stringRep.split("\\|");

        if (arrayOfUserInfo.length == 0){
            return new User("No name", "No name");
        }
        User preparedUser;
        String[] userInfo = arrayOfUserInfo[0].split("_");
        preparedUser = new User(userInfo[0], userInfo[1]);

        if (arrayOfUserInfo.length == 1){
            preparedUser.messagesSent = 0;
        }
        preparedUser.messagesSent = Integer.parseInt(arrayOfUserInfo[1]);

        return preparedUser;
    }

    public String prepareForLeaderBoard() {
        return (firstName.trim() + "_" +  lastName.trim() + "|" + messagesSent + System.lineSeparator());
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

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return this.firstName.toUpperCase().trim().equals(user.getFirstName().toUpperCase().trim()) &&
                this.lastName.toUpperCase().trim().equals(user.getLastName().toUpperCase().trim());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " | Messages sent: " + messagesSent;
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

    @Override
    public int compareTo(User o) {
        return Integer.valueOf(this.messagesSent).compareTo(Integer.valueOf(o.messagesSent));
    }
}
