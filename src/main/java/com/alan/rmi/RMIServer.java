package com.alan.rmi;

import com.alan.jndi.JndiHelper;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

    private static final String RMI_PORT_KEY = "rmi.port";
    private static final String RMI_HOST_KEY = "rmi.host";
    private static final int RANDOM_PORT_HINT_IMAGES = 0;
    private static final int RANDOM_PORT_HINT_VOICE = 1;

    public static void main(String[] args) {
        try {
            String rmiPortString = JndiHelper.getValueFromConfiguration(RMI_PORT_KEY);
            //Creating picture service object
            PictureServiceImpl pictureServiceImpl = new PictureServiceImpl();
            VoiceServiceImpl voiceServiceImpl = new VoiceServiceImpl();

            // Export created objects
            PictureService pictureService = (PictureService) UnicastRemoteObject.exportObject(pictureServiceImpl, RANDOM_PORT_HINT_IMAGES);
            VoiceService voiceService = (VoiceService) UnicastRemoteObject.exportObject(voiceServiceImpl, RANDOM_PORT_HINT_VOICE);

            //Register the exported class in RMI registry with some name, client will use that name to get
            //the reference of those exported objects

            //Get the registry to register objects
            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(rmiPortString));

            registry.rebind("pictureService", pictureService);
            registry.rebind("voiceService", voiceService);

            System.err.println("RMI Server Ready on Port: " + rmiPortString);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getHostName() throws Exception {
        return JndiHelper.getValueFromConfiguration(RMI_HOST_KEY);
    }

    public static Integer getPortNumber() throws Exception {
        return Integer.valueOf(JndiHelper.getValueFromConfiguration(RMI_PORT_KEY));
    }
}
