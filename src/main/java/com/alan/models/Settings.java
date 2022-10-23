package com.alan.models;

public class Settings {

    private ResolutionType resolutionType;

    public Settings(ResolutionType resolutionType) {
        this.resolutionType = resolutionType;
    }

    public String prepareForFile(){
        return resolutionType.toString();
    }

}
