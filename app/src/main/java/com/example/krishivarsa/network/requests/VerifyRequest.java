package com.example.krishivarsa.network.requests;
public class VerifyRequest {
    public String status;   // approved | rejected
    public String reason;   // optional

    public VerifyRequest(String status, String reason) {
        this.status = status;
        this.reason = reason;
    }
}
