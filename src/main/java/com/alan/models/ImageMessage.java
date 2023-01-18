package com.alan.models;

import java.io.*;

public class ImageMessage implements Serializable, Externalizable{

    private File sentFile;
    private User messageSender;

    public ImageMessage() {
    }

    public File getSentFile() {
        return sentFile;
    }

    public void setSentFile(File sentFile) {
        this.sentFile = sentFile;
    }

    public ImageMessage(User messageSender, File sentFile) {
        this.messageSender = messageSender;
        this.sentFile = sentFile;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(sentFile);
        out.writeObject(messageSender);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        sentFile = (File) in.readObject();
        messageSender = (User) in.readObject();
    }
}
