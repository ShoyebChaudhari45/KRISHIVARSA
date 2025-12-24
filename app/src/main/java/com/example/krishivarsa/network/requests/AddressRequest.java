package com.example.krishivarsa.network.requests;

public class AddressRequest {
    public String village;
    public String district;
    public String state;
    public String pincode;

    public AddressRequest(String village, String district,
                          String state, String pincode) {
        this.village = village;
        this.district = district;
        this.state = state;
        this.pincode = pincode;
    }
}
