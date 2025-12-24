package com.example.krishivarsa.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.activities.LoginActivity;
import com.example.krishivarsa.utils.SessionManager;

public class AdminDashboardActivity extends AppCompatActivity {

    TextView tvTotalCrops, tvTotalEntries;
    Button btnApproveUsers, btnManageEntries, btnAnalytics, btnCertificates, btnLogout;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sessionManager = new SessionManager(this);

        tvTotalCrops = findViewById(R.id.tvTotalCrops);
        tvTotalEntries = findViewById(R.id.tvTotalEntries);

        btnApproveUsers = findViewById(R.id.btnApproveUsers);
        btnManageEntries = findViewById(R.id.btnManageEntries);
        btnAnalytics = findViewById(R.id.btnAnalytics);
        btnLogout = findViewById(R.id.btnLogout);

        // 🔧 TEMP dummy stats (API later)
        tvTotalCrops.setText("Total Crops: 42");
        tvTotalEntries.setText("Total Entries: 128");

        btnApproveUsers.setOnClickListener(v ->
                startActivity(new Intent(this, ApproveUsersActivity.class))
        );

        btnManageEntries.setOnClickListener(v ->
                startActivity(new Intent(this, ManageEntriesActivity.class))
        );

        btnAnalytics.setOnClickListener(v ->
                startActivity(new Intent(this, AnalyticsActivity.class))
        );



        btnLogout.setOnClickListener(v -> {

            sessionManager.logout();

            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }
}
