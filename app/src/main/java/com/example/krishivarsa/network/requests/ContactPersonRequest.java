package com.example.krishivarsa.network.requests;

public class ContactPersonRequest {
    private String name;
    private String designation;
    private String email;
    private String phone;

    public ContactPersonRequest(String name, String designation,
                                String email, String phone) {
        this.name = name;
        this.designation = designation;
        this.email = email;
        this.phone = phone;
    }
}
