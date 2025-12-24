package com.example.krishivarsa.activities.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.activities.LoginActivity;
import com.example.krishivarsa.activities.PublicHomeActivity;
import com.example.krishivarsa.utils.SessionManager;

public class FarmerDashboardActivity extends AppCompatActivity {

    Button btnAddLandrace, btnViewCrops, btnProfile, btnLogout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ✅ CORRECT
        setContentView(R.layout.activity_farmer_dashboard);

        sessionManager = new SessionManager(this);

        btnAddLandrace = findViewById(R.id.btnAddLandrace);
        btnViewCrops = findViewById(R.id.btnViewCrops);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);

        btnAddLandrace.setOnClickListener(v ->
                startActivity(new Intent(this, AddLandraceActivity.class))
        );

        btnViewCrops.setOnClickListener(v ->
                startActivity(new Intent(this, PublicHomeActivity.class))
        );

//        btnProfile.setOnClickListener(v ->
//                startActivity(new Intent(this, FarmerProfileActivity.class))
//        );

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }
}
