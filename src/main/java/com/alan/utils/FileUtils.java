package com.alan.utils;

import javafx.scene.control.Alert;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class FileUtils {
    private FileUtils(){
    }

    public static void copyFileContentToAnotherFile(File source, File destination) throws IOException {
        FileInputStream in = new FileInputStream(source);
        FileOutputStream out = new FileOutputStream(destination);
        try {
            int n;
            while ((n = in.read()) != -1){
                out.write(n);
            }
        } catch (IOException e) {
            AlertUtils.showErrorMessage("Something went wrong while saving file");
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }
    }

}
