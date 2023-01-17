package com.alan.rmi;

import com.alan.models.Message;
import com.alan.models.User;

import java.io.File;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureServiceImpl implements PictureService{

    private List<Message> allSentPictures;

    @Override
    public void sendPicture(Message message) throws RemoteException {
        allSentPictures.add(message);
    }

    @Override
    public List<Message> receiveAllSentPictures() throws RemoteException {
        return allSentPictures;
    }
}
