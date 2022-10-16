package com.alan.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static void createDirHierarchy(String destination) throws IOException {
        String dir = destination.substring(0, destination.lastIndexOf(File.separator));
        Path pathDir = Paths.get(dir);
        if (!Files.exists(pathDir)) {
            Files.createDirectories(pathDir);
        }
    }

}
