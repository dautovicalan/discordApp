package com.alan.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {
    private List<TextMessage> allMessages;
    private User userConversation;

    public Conversation(List<TextMessage> textMessages, User userConversation) {
        this.allMessages = textMessages;
        this.userConversation = userConversation;
    }

    public Conversation(User userConversation) {
        this.userConversation = userConversation;
        this.allMessages = new ArrayList<>();
    }

    public List<TextMessage> getAllMessages() {
        return allMessages;
    }

    public void addNewMessage(TextMessage message){
        this.allMessages.add(message);
    }

    public User getUserConversation() {
        return userConversation;
    }

    @Override
    public String toString() {
        return "Conversation from " + userConversation.getFirstName();
    }
}
