package com.alan.rmi;

import com.alan.models.Message;
import com.alan.models.User;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface PictureService extends Remote {

    void sendPicture(Message message) throws RemoteException;
    List<Message> receiveAllSentPictures() throws RemoteException;
}
