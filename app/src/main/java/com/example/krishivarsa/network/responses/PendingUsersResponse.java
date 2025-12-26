package com.example.krishivarsa.network.responses;

import com.example.krishivarsa.models.PendingUser;
import java.util.List;

public class PendingUsersResponse {
    private boolean success;
    private int count;
    private List<PendingUser> users;

    public List<PendingUser> getUsers() {
        return users;
    }
}
