package com.alan.businessLayer;

import com.alan.models.Conversation;
import com.alan.models.Message;
import com.alan.models.TextMessage;

import javax.xml.bind.JAXBException;
import java.io.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConversationManager {
    private ConversationManager() {
    }
    private static Conversation conversation = new Conversation();

    public static void addMessage(TextMessage message) {
        conversation.getAllMessages().add(message);
    }

    public static int getMessagesSize() {return conversation.getAllMessages().size();}

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

    public static String getStatistic(){
        StringBuilder stringBuilder = new StringBuilder();

        AtomicInteger textCounter = new AtomicInteger(0);
        AtomicInteger imageCounter = new AtomicInteger(0);
        conversation.getAllMessages().forEach(message -> {

        });


        stringBuilder.append("Your stats: \n");
        stringBuilder.append("Messages sent: " + (imageCounter.get() + textCounter.get()) + "\n");
        stringBuilder.append("Images part: " + imageCounter.get() + "\n");
        stringBuilder.append("Real messages: " + textCounter.get() + "\n");

        return stringBuilder.toString();
    }

}
