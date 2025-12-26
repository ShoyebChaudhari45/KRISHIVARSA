package com.example.krishivarsa.network.requests;

public class LocationRequest {

    private String village;
    private String district;
    private String state;

    public LocationRequest(String village, String district, String state) {
        this.village = village;
        this.district = district;
        this.state = state;
    }

    // Gson needs getters (IMPORTANT)
    public String getVillage() {
        return village;
    }

    public String getDistrict() {
        return district;
    }

    public String getState() {
        return state;
    }
}
