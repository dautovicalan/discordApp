package com.alan.utils;

import com.alan.businessLayer.SettingsManager;
import com.alan.discordapp.MainApplication;
import com.alan.models.ResolutionType;
import com.alan.models.Settings;
import javafx.stage.Stage;

public final class ResolutionChangerUtil {

    private ResolutionChangerUtil(){
    }

    public static void changeResolution(ResolutionType resolutionType){
        Stage mainStage = MainApplication.getMainStage();
        switch (resolutionType){
            case SMALL -> {
                mainStage.setFullScreen(false);
                mainStage.setMinWidth(1200);
                mainStage.setMinHeight(600);
                SettingsManager.changeCurrentSettings(new Settings(ResolutionType.SMALL));
            }
            case MEDIUM -> {
                mainStage.setFullScreen(false);
                mainStage.setMinWidth(1400);
                mainStage.setMinHeight(700);
                SettingsManager.changeCurrentSettings(new Settings(ResolutionType.MEDIUM));
            }
            case FULLSCREEN -> {
                mainStage.setFullScreen(true);
                SettingsManager.changeCurrentSettings(new Settings(ResolutionType.FULLSCREEN));
            }
        }
    }

}
