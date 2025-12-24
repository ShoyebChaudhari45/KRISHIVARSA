package com.example.krishivarsa.models;

public class Entry {

    private int id;
    private String cropName;
    private String varietyName;
    private String contributor;
    private String threatLevel;

    public Entry(int id, String cropName, String varietyName,
                 String contributor, String threatLevel) {
        this.id = id;
        this.cropName = cropName;
        this.varietyName = varietyName;
        this.contributor = contributor;
        this.threatLevel = threatLevel;
    }

    public int getId() { return id; }
    public String getCropName() { return cropName; }
    public String getVarietyName() { return varietyName; }
    public String getContributor() { return contributor; }
    public String getThreatLevel() { return threatLevel; }
}
