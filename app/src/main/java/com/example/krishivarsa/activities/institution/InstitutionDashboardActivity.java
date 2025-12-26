package com.example.krishivarsa.activities.institution;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.activities.LoginActivity;
import com.example.krishivarsa.activities.PublicHomeActivity;
import com.example.krishivarsa.activities.farmer.AddVarietyActivity;
import com.example.krishivarsa.utils.SessionManager;

public class InstitutionDashboardActivity extends AppCompatActivity {

    Button btnAddVariety, btnViewCrops, btnProfile, btnLogout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_dashboard);

        sessionManager = new SessionManager(this);

        btnAddVariety = findViewById(R.id.btnAddVariety);
        btnViewCrops = findViewById(R.id.btnViewCrops);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);

        // ➕ Add Hybrid / Improved Variety
        btnAddVariety.setOnClickListener(v ->
                startActivity(new Intent(this, AddVarietyActivity.class))
        );

        // 🌾 View Public Crop Database
        btnViewCrops.setOnClickListener(v ->
                startActivity(new Intent(this, PublicHomeActivity.class))
        );

        // 👤 Institution Profile (later)
//        btnProfile.setOnClickListener(v ->
//                startActivity(new Intent(this, InstitutionProfileActivity.class))
//        );

        // 🚪 Logout
        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
