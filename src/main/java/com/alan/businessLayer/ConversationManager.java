package com.alan.businessLayer;

import com.alan.models.Conversation;
import com.alan.models.Message;
import com.alan.utils.JAXBUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

public class ConversationManager {
    private ConversationManager() {
    }
    private static Conversation conversation = new Conversation();

    public static void addMessage(Message message){
        conversation.getAllMessages().add(message);
    }

    public static void saveConversation() throws JAXBException {
        if (conversation.getAllMessages().size() != 0){
            JAXBUtils.save(conversation, "messages.xml");
        }
    }


}
