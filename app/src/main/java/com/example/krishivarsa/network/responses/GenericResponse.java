package com.example.krishivarsa.network.responses;

public class GenericResponse {

    public boolean success;
    public String message;
    public Object variety;

    // 🔹 OPTIONAL getters (safe)
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
