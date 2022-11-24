package com.alan.models;

public class Settings {

    public ResolutionType getResolutionType() {
        return resolutionType;
    }

    public void setResolutionType(ResolutionType resolutionType) {
        this.resolutionType = resolutionType;
    }

    private ResolutionType resolutionType;

    public Settings(ResolutionType resolutionType) {
        this.resolutionType = resolutionType;
    }

    public String prepareForFile(){
        return resolutionType.toString();
    }

}
