package com.alan.models;

import com.alan.models.User;

import java.io.*;

public class Message implements Serializable, Externalizable {

    private static final long serialVersionUID = 5L;

    public Message(){
    }

    private String messageContent;
    private User messageSender;

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }

    public User getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(User messageSender) {
        this.messageSender = messageSender;
    }

    private Message(String messageContent, User messageSender) {
        this.messageContent = messageContent;
        this.messageSender = messageSender;
    }

    public File getSentFile() {
        return sentFile;
    }

    public void setSentFile(File sentFile) {
        this.sentFile = sentFile;
    }

    private File sentFile;
    private Message(File file, User messageSender){
        this.sentFile = file;
        this.messageSender = messageSender;
    }

    public static Message createMessage(String messageContent, User messageSender){
        return new Message(messageContent, messageSender);
    }

    public static Message createMessage(File sentFile, User messageSender){
        return new Message(sentFile, messageSender);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(messageContent);
        out.writeObject(messageSender);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        messageContent = in.readUTF();
        messageSender = (User) in.readObject();
    }
}
