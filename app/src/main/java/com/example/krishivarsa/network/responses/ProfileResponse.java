package com.example.krishivarsa.network.responses;

import com.example.krishivarsa.models.VarietyModel;
import java.util.List;

public class ProfileResponse {
    public boolean success;
    public User user;
    public List<VarietyModel> contributedVarieties;

    public static class User {
        public String id;
        public String name;
        public String email;
        public String role;
        public String contactNumber;
        public Location location;
    }

    public static class Location {
        public String village;
        public String district;
        public String state;
    }
}
