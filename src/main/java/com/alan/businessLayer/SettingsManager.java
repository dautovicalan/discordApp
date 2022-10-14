package com.alan.businessLayer;

import com.alan.models.Settings;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class SettingsManager {

    private static final String SETTINGS_PATH = "/app_settings/settings.txt";

    private SettingsManager() {
    }

    public static void saveSettings(Settings settings){
        // TODO: 14.10.2022. FINISH SAVING 
        // Files.write(settings.prepareForFile())
        return;
    }

    public static Settings loadSettings(){
        // TODO: 14.10.2022. FINISH LOADING 
        return new Settings();
    }

}
