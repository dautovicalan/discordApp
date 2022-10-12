package com.alan.businessLayer;

import com.alan.models.User;

public class Message {
    private String messageStuff;
    private User sender;

    public Message(String messageStuff, User sender) {
        this.messageStuff = messageStuff;
        this.sender = sender;
    }

    @Override
    public String toString() {
        return sender.getForename() + " sent: " + messageStuff;
    }
}
