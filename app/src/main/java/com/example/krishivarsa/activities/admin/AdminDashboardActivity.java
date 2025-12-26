package com.example.krishivarsa.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.activities.LoginActivity;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.AdminDashboardResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminDashboardActivity extends AppCompatActivity {

    TextView tvTotalCrops, tvTotalEntries;
    Button btnApproveUsers, btnAddCrop, btnAddVariety,
            btnManageEntries, btnAddNotice, btnAnalytics, btnLogout;

    SessionManager sessionManager;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        tvTotalCrops = findViewById(R.id.tvTotalCrops);
        tvTotalEntries = findViewById(R.id.tvTotalEntries);

        btnApproveUsers = findViewById(R.id.btnApproveUsers);
        btnAddCrop = findViewById(R.id.btnAddCrop);
        btnAddVariety = findViewById(R.id.btnAddVariety);
        btnManageEntries = findViewById(R.id.btnManageEntries);
        btnAddNotice = findViewById(R.id.btnAddNotice);
        btnAnalytics = findViewById(R.id.btnAnalytics);
        btnLogout = findViewById(R.id.btnLogout);

        loadDashboardStats();

        btnApproveUsers.setOnClickListener(v ->
                startActivity(new Intent(this, ApproveUsersActivity.class)));

        btnAddCrop.setOnClickListener(v ->
                startActivity(new Intent(this, AdminAddCropActivity.class)));
        btnAddVariety.setOnClickListener(v ->
                startActivity(new Intent(this, AdminManageCropsActivity.class)));


        btnManageEntries.setOnClickListener(v ->
                startActivity(new Intent(this, AdminManageVarietiesActivity.class)));

        btnAddNotice.setOnClickListener(v ->
                startActivity(new Intent(this, AdminAddNoticeActivity.class)));

        btnAnalytics.setOnClickListener(v ->
                startActivity(new Intent(this, AnalyticsActivity.class)));

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            Intent i = new Intent(this, LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
        });
    }

    private void loadDashboardStats() {
        apiService.getAdminDashboardStats(
                "Bearer " + sessionManager.getToken()
        ).enqueue(new Callback<AdminDashboardResponse>() {

            @Override
            public void onResponse(Call<AdminDashboardResponse> call,
                                   Response<AdminDashboardResponse> response) {

                if (response.isSuccessful()
                        && response.body() != null
                        && response.body().success) {

                    AdminDashboardResponse.Stats s = response.body().stats;
                    tvTotalCrops.setText("Total Crops: " + s.totalCrops);
                    tvTotalEntries.setText("Total Varieties: " + s.totalVarieties);
                }
            }

            @Override
            public void onFailure(Call<AdminDashboardResponse> call, Throwable t) {
                Toast.makeText(AdminDashboardActivity.this,
                        "Server error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
