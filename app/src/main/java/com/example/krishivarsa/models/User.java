package com.example.krishivarsa.models;

public class User {

    private int id;
    private String name;
    private String role;
    private String location;

    public User(int id, String name, String role, String location) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.location = location;
    }

    public int getId() { return id; }

    public String getName() { return name; }

    public String getRole() { return role; }

    public String getLocation() { return location; }
}
