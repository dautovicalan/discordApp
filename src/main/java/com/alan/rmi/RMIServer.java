package com.alan.rmi;

import com.alan.jndi.JndiHelper;
import com.alan.models.User;

import javax.naming.NamingException;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class RMIServer {

    private static final String RMI_PORT_KEY = "rmi.port";
    private static final int RANDOM_PORT_HINT = 0;
    private static final int RMI_PORT = 2000;


    public static void main(String[] args) {
        try {
            String rmiPortString = JndiHelper.getValueFromConfiguration(RMI_PORT_KEY);
            //Creating picture service object
            PictureServiceImpl pictureServiceImpl = new PictureServiceImpl();

            // Export created objects
            PictureService pictureService = (PictureService) UnicastRemoteObject.exportObject(pictureServiceImpl, RANDOM_PORT_HINT);

            //Register the exported class in RMI registry with some name, client will use that name to get
            //the reference of those exported objects

            //Get the registry to register objects
            Registry registry = LocateRegistry.createRegistry(Integer.parseInt(rmiPortString));

            registry.rebind("test", pictureService);

            System.err.println("RMI Server Ready on Port: " + rmiPortString);

        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
    }
}
