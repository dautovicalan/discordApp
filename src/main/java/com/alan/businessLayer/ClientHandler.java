package com.alan.businessLayer;

import com.alan.models.Message;
import com.alan.models.TextMessage;
import com.alan.models.User;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable{

 public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
 private Socket socket;
 private ObjectInputStream dataInputStream;
 private ObjectOutputStream dataOutputStream;

 private User user;

 public ClientHandler(Socket socket){
  try {
   this.socket = socket;
   dataInputStream = new ObjectInputStream(socket.getInputStream());
   dataOutputStream = new ObjectOutputStream(socket.getOutputStream());

   this.user = (User) dataInputStream.readObject();

   clientHandlers.add(this);

   System.out.println(user + " connected to the chat");
   //broadcastNewConnectedClient(this.user);

  } catch (Exception e) {
   e.printStackTrace();
   closeEverything();
  }
 }

 @Override
 public void run() {
  TextMessage messageFromClient;

  while (socket.isConnected()){
   try {
    messageFromClient = (TextMessage) dataInputStream.readObject();
    System.out.println(messageFromClient.getMessageSender() + ": " +  messageFromClient.getMessageContent());
    broadcastMessage(messageFromClient);
   } catch (Exception e) {
    e.printStackTrace();
    closeEverything();
    break;
   }
  }
 }

 private void broadcastNewConnectedClient(User user) {
  for (ClientHandler clientHandler :
          clientHandlers) {
   try {
    if (clientHandler.user.getUserId() != user.getUserId()){
     clientHandler.dataOutputStream.writeObject(user);
    }
   } catch (Exception e) {
    e.printStackTrace();
   }
  }
 }

 public void broadcastMessage(TextMessage message){
  for (ClientHandler clientHandler : clientHandlers){
   try {
    if (clientHandler.user.getUserId() != user.getUserId()){
     clientHandler.dataOutputStream.writeObject(message);
    }
   } catch (IOException e) {
    e.printStackTrace();
   }
  }
 }

 public void removeClientHandlerFromArray(){
  clientHandlers.remove(this);
 }

 private void closeEverything(){
  removeClientHandlerFromArray();
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
