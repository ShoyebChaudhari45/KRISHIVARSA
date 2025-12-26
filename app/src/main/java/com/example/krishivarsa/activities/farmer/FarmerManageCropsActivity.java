package com.example.krishivarsa.activities.farmer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.CropAdapter;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.GetCropsResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerManageCropsActivity extends AppCompatActivity
        implements CropAdapter.OnCropClickListener {

    RecyclerView recyclerView;
    ApiService api;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ⚠️ MUST BE THIS LAYOUT
        setContentView(R.layout.activity_farmer_manage_crops);

        recyclerView = findViewById(R.id.recycler);

        // ✅ SAFETY CHECK (optional but recommended)
        if (recyclerView == null) {
            Toast.makeText(this, "RecyclerView not found in layout", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
                                && response.body() != null) {

                            recyclerView.setAdapter(
                                    new CropAdapter(
                                            response.body().crops,
                                            FarmerManageCropsActivity.this
                                    )
                            );



                        }
                    }

                    @Override
                    public void onFailure(Call<GetCropsResponse> call, Throwable t) {
                        Toast.makeText(
                                FarmerManageCropsActivity.this,
                                "Failed to load crops",
                                Toast.LENGTH_SHORT
                        ).show();
                    }
                });
    }

    @Override
    public void onCropClick(GetCropsResponse.Crop crop) {

        Intent i = new Intent(
                FarmerManageCropsActivity.this,
                AddLandraceVarietyActivity.class
        );

        // ✅ USE _id
        i.putExtra("crop_id", crop._id);
        i.putExtra("crop_name", crop.name);

        startActivity(i);
    }

}
