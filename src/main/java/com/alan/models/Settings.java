package com.alan.models;

public class Settings {
    public ResolutionType getResolutionType() {
        return resolutionType;
    }
    private ResolutionType resolutionType;
    public Settings(ResolutionType resolutionType) {
        this.resolutionType = resolutionType;
    }
}
