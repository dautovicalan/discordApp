package com.alan.models;

public class Settings {

    private ResolutionType resolutionType;

    public String prepareForFile(){
        return resolutionType.toString();
    }

}
