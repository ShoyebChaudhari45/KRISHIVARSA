package com.example.krishivarsa.network.requests;

public class RegisterRequest {

    private String name;
    private String email;
    private String password;
    private String contactNumber;
    private String role;
    private String userType;
    private LocationRequest location;

    public RegisterRequest(
            String name,
            String email,
            String password,
            String contactNumber,
            String role,
            String userType,
            LocationRequest location
    ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.contactNumber = contactNumber;
        this.role = role;
        this.userType = userType;
        this.location = location;
    }

    // 🔥 ADD GETTERS (MANDATORY)
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getContactNumber() { return contactNumber; }
    public String getRole() { return role; }
    public String getUserType() { return userType; }
    public LocationRequest getLocation() { return location; }
}
