package com.example.krishivarsa.network.responses;

import com.example.krishivarsa.models.User;

public class LoginResponse {

    private boolean success;
    private String token;
    private User user;

    public boolean isSuccess() {
        return success;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }
}
