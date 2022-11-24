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
import java.util.Set;

public class SettingsManager {

    private static final String PATH = "settings.txt";

    private SettingsManager() {
    }

    private static Settings currenSettings;

    public static void saveSettings() throws IOException {
        Path filePath = Paths.get(PATH);
        Files.write(filePath, currenSettings.prepareForFile().getBytes(StandardCharsets.UTF_8));
    }

    public static void changeCurrentSettings(Settings settings){
        currenSettings = settings;
    }

    public static void loadSettings() throws IOException {
        Path filePath = Paths.get(PATH);
        List<String> settingsList = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        System.out.println(settingsList);
        currenSettings = new Settings(ResolutionType.valueOf(settingsList
                .stream()
                .findFirst()
                .get()));
    }

    public static Settings getCurrenSettings(){
        if (currenSettings == null){
            return new Settings(ResolutionType.SMALL);
        }
        return currenSettings;
    }

}
