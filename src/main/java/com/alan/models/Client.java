package com.alan.models;

import com.alan.businessLayer.ConversationManager;
import com.alan.discordapp.ChatScreenController;
import com.alan.discordapp.Server;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private Socket socket;
    private ObjectInputStream dataInputStream;
    private ObjectOutputStream dataOutputStream;
    private User user;

    public Client(User user){
        try{
            this.socket = new Socket(Server.HOST, Server.PORT);
            this.user = user;
            dataOutputStream = new ObjectOutputStream(socket.getOutputStream());
            dataInputStream = new ObjectInputStream(socket.getInputStream());
            System.err.println("Client is connecting to " + socket.getInetAddress() + ":" + socket.getPort());
            dataOutputStream.writeObject(user);
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything();
        }
    }

    public void sendMessage(TextMessage message) throws IOException {
        if (this.socket.isConnected()){
            dataOutputStream.writeObject(message);
        }
    }

    public void listenForMessage(VBox vBoxMessage){
        new Thread(new Runnable() {
            @Override
            public void run() {
                TextMessage messageFromChat;

                while (socket.isConnected()){
                    try{
                        messageFromChat = (TextMessage) dataInputStream.readObject();
                        System.out.println(messageFromChat.getMessageContent());
                        ChatScreenController.addMessageToComponent(messageFromChat.getMessageContent(), vBoxMessage);
                        ConversationManager.addMessage(messageFromChat);
                    } catch (Exception e) {
                        System.out.println("Error on client run method");
                        e.printStackTrace();
                        closeEverything();
                    }
                }
            }
        }).start();
    }

    private void closeEverything(){
        try {
            if (dataInputStream != null){
                dataInputStream.close();
            }
            if (dataOutputStream != null){
                dataOutputStream.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}