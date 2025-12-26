package com.example.krishivarsa.activities.admin;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.PendingAdapter;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.PendingVarietiesResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminPendingVarietiesActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ApiService api;
    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_pending_varieties);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);

        session = new SessionManager(this);
        api = ApiClient.getClient().create(ApiService.class);

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

                            recycler.setAdapter(
                                    new PendingAdapter(response.body().varieties)
                            );

                        } else {
                            Toast.makeText(
                                    AdminPendingVarietiesActivity.this,
                                    "No pending varieties",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PendingVarietiesResponse> call, Throwable t) {
                        Toast.makeText(
                                AdminPendingVarietiesActivity.this,
                                t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });

    }
}
