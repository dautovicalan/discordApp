package com.alan.businessLayer;

import com.alan.models.Conversation;
import com.alan.models.TextMessage;
import com.alan.models.User;
import com.alan.utils.AlertUtils;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class ConversationManager {
    private ConversationManager() {
    }
    private static List<Conversation> allConversations = new ArrayList<>();

    public static void addMessage(TextMessage message) {
        User loggedInUser = UserManager.getLoggedInUser();
        Conversation userConversation = allConversations.stream().filter(e -> e.getUserConversation().equals(loggedInUser))
                .findAny().orElse(null);
        if (userConversation == null){
            userConversation = new Conversation(loggedInUser);
            allConversations.add(userConversation);
        }
        userConversation.addNewMessage(message);
    }

    public static void addConversation(Conversation conversation){
        allConversations.add(conversation);
    }

    public static int getMessagesSize() {return allConversations.size();}

    public static void saveCurrentUserConversations(User currentUser) throws IOException {
        Conversation userConversations = allConversations
                .stream()
                .filter(conversation -> conversation.getUserConversation().equals(currentUser))
                .findAny()
                .orElse(null);
        if (userConversations == null){
            AlertUtils.showInfoMessage("Currently you do not have conversation");
            return;
        }
        try(ObjectOutputStream serializer = new ObjectOutputStream(
                new FileOutputStream( "savedChats/" + currentUser.getFirstName() + "_" + LocalDateTime.now() + ".ser"))){
            serializer.writeObject(userConversations);
            AlertUtils.showInfoMessage("Successfully saved conversation");
        }
    }

    public static Optional<Stream<Conversation>> getUserConversations(User currentUser){
        Stream<Conversation> userConversation =
                allConversations
                        .stream()
                        .filter(c -> c.getUserConversation().equals(currentUser));
        return userConversation != null ? Optional.of(userConversation) : Optional.empty();
    }

    public static List<Conversation> loadAllConversations(File[] savedChats) throws IOException, ClassNotFoundException {

        if (allConversations.size() != 0) {
            return  allConversations;
        }

        for (File singleFile : savedChats) {
            Conversation conversation;
            try(ObjectInputStream deserializer = new ObjectInputStream(new FileInputStream(singleFile))){
                conversation = (Conversation) deserializer.readObject();
                allConversations.add(conversation);
            }
        }
        return allConversations;
    }
}
