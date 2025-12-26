package com.example.krishivarsa.models;

public class PendingUser {

    public String _id;
    public String name;
    public String email;
    public String role;
    public String userType;
    public boolean isApproved;
    public String status;
    public Location location;

    public static class Location {
        public String village;
        public String district;
        public String state;
    }
}
