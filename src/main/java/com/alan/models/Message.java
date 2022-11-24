package com.alan.models;

import com.alan.models.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.File;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements Serializable {
    @XmlElement(name = "messageContent")
    private String messageContent;
    @XmlElement(name = "messageSender")
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
}
