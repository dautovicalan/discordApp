package com.alan.rmi;

import com.alan.models.ImageMessage;

import java.io.File;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class VoiceServiceImpl implements VoiceService{

    private List<File> allVoices = new ArrayList<>();

    @Override
    public void sendVoiceMessage(File file) throws RemoteException {
        allVoices.add(file);
    }

    @Override
    public List<File> receiveAllVoiceMessages() throws RemoteException {
        return allVoices;
    }
}
