package com.alan.rmi;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface VoiceService extends Remote {
    void sendVoiceMessage(File file) throws RemoteException;
    List<File> receiveAllVoiceMessages() throws RemoteException;
}
