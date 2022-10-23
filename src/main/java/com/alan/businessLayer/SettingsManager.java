package com.alan.businessLayer;

import com.alan.models.ResolutionType;
import com.alan.models.Settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SettingsManager {

    private static final String PATH = "settings.txt";

    private SettingsManager() {
    }

    public static void saveSettings(Settings settings) throws IOException {
        Path filePath = Paths.get(PATH);
        Files.write(filePath, settings.prepareForFile().getBytes(StandardCharsets.UTF_8));
    }

    public static Settings loadSettings() throws IOException {
        // TODO: 14.10.2022. FINISH LOADING
        Path filePath = Paths.get(PATH);
        List<String> settingsList = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        System.out.println(settingsList);
        return new Settings(ResolutionType.SMALL);
    }

}
