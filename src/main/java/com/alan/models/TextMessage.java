package com.alan.models;

import java.io.*;

public class TextMessage implements Serializable, Externalizable {

    private String messageContent;
    private User messageSender;

    public TextMessage() {
    }

    public User getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(User messageSender) {
        this.messageSender = messageSender;
    }

    public TextMessage(User messageSender, String messageContent) {
        this.messageSender = messageSender;
        this.messageContent = messageContent;
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
