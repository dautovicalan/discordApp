package com.alan.rmi;

import com.alan.models.ImageMessage;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface PictureService extends Remote {
    void sendPicture(ImageMessage message) throws RemoteException;
    List<ImageMessage> receiveAllSentPictures() throws RemoteException;
}
