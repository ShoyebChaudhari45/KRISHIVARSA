package com.example.krishivarsa.activities.admin;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.PendingUserAdapter;
import com.example.krishivarsa.models.PendingUser;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.utils.SessionManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApproveUsersActivity extends AppCompatActivity {

    RecyclerView recycler;
    TextView tvEmpty;

    SessionManager sessionManager;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_users);

        tvEmpty = findViewById(R.id.tvEmpty);
        recycler = findViewById(R.id.recyclerPending);

        recycler.setLayoutManager(new LinearLayoutManager(this));

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        loadPendingUsers();
    }

    private void loadPendingUsers() {

        apiService.getPendingUsers("Bearer " + sessionManager.getToken())
                .enqueue(new Callback<List<PendingUser>>() {

                    @Override
                    public void onResponse(Call<List<PendingUser>> call,
                                           Response<List<PendingUser>> response) {

                        if (response.isSuccessful() && response.body() != null) {

                            if (response.body().isEmpty()) {
                                tvEmpty.setVisibility(View.VISIBLE);
                                recycler.setVisibility(View.GONE);
                            } else {
                                tvEmpty.setVisibility(View.GONE);
                                recycler.setVisibility(View.VISIBLE);

                                recycler.setAdapter(
                                        new PendingUserAdapter(
                                                ApproveUsersActivity.this,
                                                response.body(),
                                                sessionManager.getToken()
                                        )
                                );
                            }

                        } else {
                            Toast.makeText(
                                    ApproveUsersActivity.this,
                                    "Failed to load users",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PendingUser>> call, Throwable t) {
                        Toast.makeText(
                                ApproveUsersActivity.this,
                                "Error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
