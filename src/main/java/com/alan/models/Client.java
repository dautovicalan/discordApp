package com.alan.models;

import com.alan.discordapp.Server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException {
        Socket socket1 = new Socket(Server.HOST, Server.PORT);
        Client client = new Client(socket1);
        client.sendMessage();
        client.listenForMessage();

    }

    private Socket socket;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    public Client(Socket socket){
        try{
            this.socket = socket;
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
            System.err.println("Client is connecting to " + socket.getInetAddress() + ":" + socket.getPort());
        } catch (IOException e) {
            e.printStackTrace();
            closeEverything();
        }
    }

    public void sendMessage() throws IOException {
        dataOutputStream.writeUTF("Nigger");

        while (this.socket.isConnected()){
            String temp = "Pozdrav ja sam random poruka " + Math.random();
            dataOutputStream.writeUTF("Nigger writte: " + temp);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void listenForMessage(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String messageFromChat;

                while (socket.isConnected()){
                    try{
                        messageFromChat = dataInputStream.readUTF();
                        System.out.println(messageFromChat);
                    } catch (IOException e) {
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