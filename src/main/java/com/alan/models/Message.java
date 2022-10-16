package com.alan.models;

import com.alan.models.User;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class Message {
    private final String DEL = "|";
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

    public static Message createMessage(String messageContent, User messageSender){
        return new Message(messageContent, messageSender);
    }

    public String prepareForFile(){
        return messageSender + DEL + messageContent;
    }

}
