package com.alan.discordapp;

import com.alan.businessLayer.ClientHandler;
import com.alan.jndi.JndiHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    private static final String SERVER_HOST_KEY = "server.host";
    private static final String SERVER_PORT_KEY = "server.port";
    public static final String HOST;
    public static final int PORT;

    static {
        try {
            HOST = JndiHelper.getValueFromConfiguration(SERVER_HOST_KEY);
            PORT = Integer.parseInt(JndiHelper.getValueFromConfiguration(SERVER_PORT_KEY));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



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
