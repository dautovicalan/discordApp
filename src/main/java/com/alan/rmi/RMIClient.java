package com.alan.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RMIClient {

    // TESTING INIT VERSION OF RMI CLIENT
    public static void main(String[] args) throws RemoteException, NotBoundException {

        try {
            // Locate the registry
            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 2000);
            // Get the reference of exported object from RMI registry
            PictureService pictureService = (PictureService) registry.lookup("test");
            // Now we can invoke the method of the referenced objects
            // System.out.println(pictureService.getPictureName());
        } catch (Exception e) {
            System.out.println("Something went wrong with client");
            e.printStackTrace();
        }
    }
}
