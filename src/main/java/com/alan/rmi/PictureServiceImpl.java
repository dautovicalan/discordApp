package com.alan.rmi;

import com.alan.discordapp.SendPictureScreenController;
import com.alan.models.ImageMessage;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;

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
}
