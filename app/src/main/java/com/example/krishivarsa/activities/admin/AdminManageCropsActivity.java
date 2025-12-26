package com.example.krishivarsa.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.AdminCropAdapter;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.GetCropsResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageCropsActivity extends AppCompatActivity {

    RecyclerView recycler;
    ApiService api;
    SessionManager session;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_admin_manage_crops);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);

        api = ApiClient.getClient().create(ApiService.class);
        session = new SessionManager(this);

        loadCrops();
    }

    private void loadCrops() {

        api.getAllCrops("Bearer " + session.getToken())
                .enqueue(new Callback<GetCropsResponse>() {

                    @Override
                    public void onResponse(Call<GetCropsResponse> call,
                                           Response<GetCropsResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().success) {

                            recycler.setAdapter(
                                    new AdminCropAdapter(
                                            response.body().crops,
                                            crop -> {
                                                Intent i = new Intent(
                                                        AdminManageCropsActivity.this,
                                                        AdminAddVarietyActivity.class
                                                );
                                                i.putExtra("crop_id", crop._id);
                                                i.putExtra("crop_name", crop.name);
                                                startActivity(i);
                                            }
                                    )
                            );

                        } else {
                            Toast.makeText(
                                    AdminManageCropsActivity.this,
                                    "Failed to load crops",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GetCropsResponse> call, Throwable t) {
                        Toast.makeText(
                                AdminManageCropsActivity.this,
                                "Server error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
