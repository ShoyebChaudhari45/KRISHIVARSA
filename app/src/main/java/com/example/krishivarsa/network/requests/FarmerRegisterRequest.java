package com.example.krishivarsa.network.requests;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class FarmerRegisterRequest {

    private String email;
    private String mobile;
    private String password;

    @SerializedName("fullName")   // 🔥 MOST IMPORTANT
    private String fullName;

    private int age;
    private String gender;
    private AddressRequest address;

    @SerializedName("farmingExperience")
    private int farmingExperience;

    @SerializedName("landSize")
    private int landSize;

    @SerializedName("cropsGrown")
    private List<String> cropsGrown;

    public FarmerRegisterRequest(
            String email,
            String mobile,
            String password,
            String fullName,
            int age,
            String gender,
            AddressRequest address,
            int farmingExperience,
            int landSize,
            List<String> cropsGrown
    ) {
        this.email = email;
        this.mobile = mobile;
        this.password = password;
        this.fullName = fullName;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.farmingExperience = farmingExperience;
        this.landSize = landSize;
        this.cropsGrown = cropsGrown;
    }
}
