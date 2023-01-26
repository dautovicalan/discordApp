package com.alan.rmi;

import com.alan.models.ImageMessage;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

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

    public static final String LOOKUP_NAME = "pictureService";
}
