package com.alan.rmi;

import com.alan.models.ImageMessage;
import com.alan.models.Message;
import com.alan.models.User;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PictureServiceImpl implements PictureService{

    private List<ImageMessage> allSentPictures = new ArrayList<>();

    @Override
    public void sendPicture(ImageMessage message) throws RemoteException {
        allSentPictures.add(message);
    }

    @Override
    public List<ImageMessage> receiveAllSentPictures() throws RemoteException {
        return allSentPictures;
    }
}
