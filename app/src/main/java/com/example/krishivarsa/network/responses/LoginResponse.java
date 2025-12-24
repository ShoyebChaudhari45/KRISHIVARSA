package com.example.krishivarsa.network.responses;

public class LoginResponse {

    private String message;
    private String token;
    private User user;

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }

    public User getUser() {
        return user;
    }

    // 🔽 Nested User class
    public static class User {

        private String id;
        private String email;
        private String role;
        private String status;

        public String getId() {
            return id;
        }

        public String getEmail() {
            return email;
        }

        public String getRole() {
            return role;
        }

        public String getStatus() {
            return status;
        }
    }
}
