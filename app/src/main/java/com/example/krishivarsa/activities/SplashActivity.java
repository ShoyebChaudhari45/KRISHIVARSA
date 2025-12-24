package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.utils.SessionManager;

public class SplashActivity extends AppCompatActivity {

    // Splash screen duration (3 seconds)
    private static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SessionManager sessionManager = new SessionManager(this);

        new Handler().postDelayed(() -> {

            if (sessionManager.isLoggedIn()) {

                String role = sessionManager.getRole();

                if ("ADMIN".equals(role)) {
                    startActivity(new Intent(this,
                            com.example.krishivarsa.activities.admin.AdminDashboardActivity.class));
                } else if ("FARMER".equals(role)) {
                    startActivity(new Intent(this,
                            com.example.krishivarsa.activities.farmer.FarmerDashboardActivity.class));
                } else if ("INSTITUTION".equals(role)) {
                    startActivity(new Intent(this,
                            com.example.krishivarsa.activities.institution.InstitutionDashboardActivity.class));
                }

            } else {
                // Not logged in → public view
                startActivity(new Intent(this, PublicHomeActivity.class));
            }

            finish();

        }, 2000); // 2 sec splash

        setContentView(R.layout.activity_splash);
    }
}
