package com.example.krishivarsa.activities.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.UserApprovalAdapter;
import com.example.krishivarsa.models.User;

import java.util.ArrayList;
import java.util.List;

public class ApproveUsersActivity extends AppCompatActivity {

    RecyclerView recyclerUsers;
    UserApprovalAdapter adapter;
    List<User> pendingUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_users);

        recyclerUsers = findViewById(R.id.recyclerUsers);
        recyclerUsers.setLayoutManager(new LinearLayoutManager(this));

        pendingUsers = new ArrayList<>();

        // 🔧 Dummy pending users (API later)
        pendingUsers.add(new User(1, "Ramesh Patil", "Farmer", "Maharashtra"));
        pendingUsers.add(new User(2, "Agro Seed Lab", "Institution", "Punjab"));

        adapter = new UserApprovalAdapter(this, pendingUsers);
        recyclerUsers.setAdapter(adapter);
    }
}
