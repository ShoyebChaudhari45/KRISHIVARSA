package com.example.krishivarsa.activities.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.PendingAdapter;
import com.example.krishivarsa.models.VarietyModel;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.PendingVarietiesResponse;
import com.example.krishivarsa.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminManageVarietiesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ApiService api;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_admin_manage_varieties);

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        api = ApiClient.getClient().create(ApiService.class);
        session = new SessionManager(this);

        loadPendingVarieties();
    }

    private void loadPendingVarieties() {

        api.getPendingVarieties("Bearer " + session.getToken())
                .enqueue(new Callback<PendingVarietiesResponse>() {

                    @Override
                    public void onResponse(Call<PendingVarietiesResponse> call,
                                           Response<PendingVarietiesResponse> response) {

                        if (response.isSuccessful()
                                && response.body() != null
                                && response.body().varieties != null) {

                            recyclerView.setAdapter(
                                    new PendingAdapter(response.body().varieties)
                            );

                        } else {
                            Toast.makeText(
                                    AdminManageVarietiesActivity.this,
                                    "Failed to load pending varieties",
                                    Toast.LENGTH_LONG
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PendingVarietiesResponse> call, Throwable t) {
                        Toast.makeText(
                                AdminManageVarietiesActivity.this,
                                "Server error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }

}
