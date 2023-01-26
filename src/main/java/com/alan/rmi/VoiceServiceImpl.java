package com.alan.rmi;

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

    public static final String LOOKUP_NAME = "voiceService";
}
