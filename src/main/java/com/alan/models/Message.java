package com.alan.models;

import com.alan.models.User;

import java.io.*;

public abstract class Message implements Serializable, Externalizable {

    protected static final long serialVersionUID = 5L;

    public Message(){
    }

    protected User messageSender;

    public User getMessageSender() {
        return messageSender;
    }

    public void setMessageSender(User messageSender) {
        this.messageSender = messageSender;
    }

    public Message(User messageSender) {
        this.messageSender = messageSender;
    }


}
