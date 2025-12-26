package com.example.krishivarsa.activities.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.activities.LoginActivity;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.FarmerStatsResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerDashboardActivity extends AppCompatActivity {

    TextView tvName, tvLocation, tvTotalVarieties, btnLogout;
    Button btnAddVariety, btnMyVarieties;

    SessionManager sessionManager;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_dashboard);

        btnAddVariety = findViewById(R.id.btnAddVariety);
        btnMyVarieties = findViewById(R.id.btnMyVarieties);
        tvName = findViewById(R.id.tvName);
        tvLocation = findViewById(R.id.tvLocation);
        tvTotalVarieties = findViewById(R.id.tvTotalVarieties);
        btnLogout = findViewById(R.id.btnLogout);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        loadStats();

        btnAddVariety.setOnClickListener(v ->
                startActivity(new Intent(this, FarmerManageCropsActivity.class))
        );

        btnMyVarieties.setOnClickListener(v ->
                startActivity(new Intent(this, MySubmissionsActivity.class))
        );

        btnLogout.setOnClickListener(v -> {
            sessionManager.logout();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void loadStats() {

        apiService.getMyVarietyStats("Bearer " + sessionManager.getToken())
                .enqueue(new Callback<FarmerStatsResponse>() {

                    @Override
                    public void onResponse(Call<FarmerStatsResponse> call,
                                           Response<FarmerStatsResponse> response) {

                        if (response.isSuccessful() && response.body() != null) {
                            tvTotalVarieties.setText(
                                    String.valueOf(response.body().total)
                            );
                        }
                    }

                    @Override
                    public void onFailure(Call<FarmerStatsResponse> call, Throwable t) {
                        Toast.makeText(
                                FarmerDashboardActivity.this,
                                "Failed to load stats",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }
}
