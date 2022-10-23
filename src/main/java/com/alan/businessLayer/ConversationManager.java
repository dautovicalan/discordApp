package com.alan.businessLayer;

import com.alan.models.Conversation;
import com.alan.models.Message;
import com.alan.utils.JAXBUtils;

import javax.xml.bind.JAXBException;
import javax.xml.bind.annotation.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ConversationManager {
    private ConversationManager() {
    }
    private static Conversation conversation = new Conversation();

    public static void addMessage(Message message){
        conversation.getAllMessages().add(message);
    }

    public static int getMessagesSize(){return conversation.getAllMessages().size();}

    public static void saveConversation() throws JAXBException, IOException {
        if (conversation.getAllMessages().size() != 0){
            try(ObjectOutputStream serializer = new ObjectOutputStream(new FileOutputStream("savedMessages.ser"))){
                serializer.writeObject(conversation);
            }
        }
    }

    public static Conversation loadConversation() throws IOException, ClassNotFoundException {
        try(ObjectInputStream deserialzer = new ObjectInputStream(new FileInputStream("savedMessages.ser"))){
            conversation = (Conversation) deserialzer.readObject();
        }
        return conversation;
    }


}
