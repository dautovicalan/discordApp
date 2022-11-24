package com.alan.models;

import com.alan.discordapp.ChatScreenController;
import javafx.scene.layout.VBox;

import java.io.*;
import java.net.Inet4Address;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private ServerSocket serverSocket;
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;

    public Server(ServerSocket serverSocket) {

        try {
            this.serverSocket = serverSocket;
            System.out.println("Listening on port " + this.serverSocket.getLocalPort());
            this.socket = serverSocket.accept();
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        } catch (IOException e){
            System.out.println("Error creating server");
            e.printStackTrace();
            closeEveryConnection(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessageToClient(String message){
        try {
            bufferedWriter.write(message);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to client!");
            closeEveryConnection(socket, bufferedReader, bufferedWriter);
        }
    }

    public void receiveMessageFromClient(VBox vBox){
        new Thread(() -> {
            while (socket.isConnected()){
                try {
                    String messageFromClient = bufferedReader.readLine();
                    ChatScreenController.addMessageToComponent(messageFromClient, vBox);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error receivieng message from client");
                    closeEveryConnection(socket, bufferedReader, bufferedWriter);
                    break;
                }
            }
        }).start();
    }

    public void closeEveryConnection(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter){
        try {
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedReader.close();
            }
            if (socket != null){
                socket.close();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
