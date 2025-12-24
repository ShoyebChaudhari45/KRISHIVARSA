package com.example.krishivarsa.models;

public class Crop {

    private String cropName;
    private String varietyName;
    private String location;
    private String threatLevel;

    // Constructor
    public Crop(String cropName, String varietyName, String location, String threatLevel) {
        this.cropName = cropName;
        this.varietyName = varietyName;
        this.location = location;
        this.threatLevel = threatLevel;
    }

    // Getters
    public String getCropName() {
        return cropName;
    }

    public String getVarietyName() {
        return varietyName;
    }

    public String getLocation() {
        return location;
    }

    public String getThreatLevel() {
        return threatLevel;
    }
}
