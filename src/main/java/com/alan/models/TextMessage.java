package com.alan.models;

import java.io.*;
import java.time.LocalDateTime;

public class TextMessage implements Serializable, Externalizable {

    private String messageContent;
    private User messageSender;

    public TextMessage() {
    }

    public TextMessage(String messageContent, LocalDateTime createdOn, User messageSender) {
        this.messageContent = messageContent;
        this.createdOn = createdOn;
        this.messageSender = messageSender;
    }

    public User getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(User messageSender) {
        this.messageSender = messageSender;
    }

    private LocalDateTime createdOn;
    public TextMessage(User messageSender, String messageContent) {
        this.createdOn = LocalDateTime.now();
        this.messageSender = messageSender;
        this.messageContent = messageContent;
    }

    public LocalDateTime getCreatedOn() {
        return createdOn;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
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
