package com.alan.models;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "bigAssConvo")
public class Conversation implements Serializable {
    @XmlElementWrapper
    @XmlElement(name = "messages")
    private List<Message> allMessages;

    public Conversation() {
        this.allMessages = new ArrayList<>();
    }

    public List<Message> getAllMessages() {
        return allMessages;
    }

    public void setAllMessages(List<Message> allMessages) {
        this.allMessages = allMessages;
    }
}
