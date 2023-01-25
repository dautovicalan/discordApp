package com.alan.businessLayer;

import com.alan.models.ResolutionType;
import com.alan.models.Settings;

public class SettingsManager {

    private SettingsManager() {
    }

    private static Settings currenSettings;

    public static void changeCurrentSettings(Settings settings){
        currenSettings = settings;
    }

    public static Settings getCurrenSettings(){
        if (currenSettings == null){
            return new Settings(ResolutionType.SMALL);
        }
        return currenSettings;
    }

}
