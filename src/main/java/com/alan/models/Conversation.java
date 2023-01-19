package com.alan.models;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Conversation implements Serializable {

    private List<TextMessage> allMessages;

    public Conversation() {
        this.allMessages = new ArrayList<>();
    }

    public List<TextMessage> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<TextMessage> allMessages) {
        this.allMessages = allMessages;
    }
}
