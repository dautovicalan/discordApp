package com.alan.discordapp;

import com.alan.businessLayer.ClientHandler;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final String HOST = "localhost";
    public static final int PORT = 1989;

    public static void main(String[] args) {
        acceptRequests();
    }

    private static void acceptRequests() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.err.println("Server listening on port: " + serverSocket.getLocalPort());

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                System.err.println("Client connected from port: " + clientSocket.getPort());
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
            }
        }  catch (IOException e) {
            e.printStackTrace();
        }
    }
}
