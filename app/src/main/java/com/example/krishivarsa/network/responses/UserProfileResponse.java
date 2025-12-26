package com.example.krishivarsa.network.responses;


import java.util.List;

public class UserProfileResponse {
    public boolean success;
    public User user;
    public List<Variety> contributedVarieties;

    public static class User {
        public String id;
        public String name;
        public String email;
        public String role;
        public String userType;
        public boolean isApproved;
        public Location location;
    }

    public static class Location {
        public String village;
        public String district;
        public String state;
    }

    public static class Variety {
        public String _id;
        public String name;
    }
}
