package com.example.krishivarsa.activities.farmer;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.MySubmissionsAdapter;
import com.example.krishivarsa.models.VarietyModel;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.ProfileResponse;
import com.example.krishivarsa.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;public class MySubmissionsActivity extends AppCompatActivity {

    RecyclerView recycler;
    ApiService api;
    SessionManager session;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_my_submissions);

        recycler = findViewById(R.id.recycler);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        session = new SessionManager(this);
        api = ApiClient.getClient().create(ApiService.class);

        load();
    }

    private void load() {
        api.getMyVarieties("Bearer " + session.getToken())
                .enqueue(new Callback<List<VarietyModel>>() {
                    @Override
                    public void onResponse(Call<List<VarietyModel>> call,
                                           Response<List<VarietyModel>> response) {

                        List<VarietyModel> list =
                                response.body() != null
                                        ? response.body()
                                        : new ArrayList<>();

                        recycler.setAdapter(new MySubmissionsAdapter(list));
                    }

                    @Override
                    public void onFailure(Call<List<VarietyModel>> call, Throwable t) {
                        Toast.makeText(MySubmissionsActivity.this,
                                t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                    }

}
