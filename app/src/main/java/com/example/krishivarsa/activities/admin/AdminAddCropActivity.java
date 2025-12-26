package com.example.krishivarsa.activities.admin;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.requests.AddCropRequest;
import com.example.krishivarsa.network.responses.AddCropResponse;
import com.example.krishivarsa.network.responses.GenericResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminAddCropActivity extends AppCompatActivity {

    EditText etCropName, etCategory;
    ApiService api;
    SessionManager session;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_admin_add_crop);

        etCropName = findViewById(R.id.etCropName);
        etCategory = findViewById(R.id.etCategory);

        api = ApiClient.getClient().create(ApiService.class);
        session = new SessionManager(this);

        findViewById(R.id.btnAddCrop).setOnClickListener(v -> addCrop());
    }

    private void addCrop() {

        String name = etCropName.getText().toString().trim();
        String category = etCategory.getText().toString().trim();

        // ✅ CLIENT SIDE VALIDATION
        if (name.isEmpty()) {
            etCropName.setError("Crop name required");
            return;
        }

        if (category.isEmpty()) {
            etCategory.setError("Category required");
            return;
        }

        AddCropRequest request = new AddCropRequest(name, category);

        api.addCrop(
                "Bearer " + session.getToken(),
                request
        ).enqueue(new Callback<GenericResponse>() {

            @Override
            public void onResponse(Call<GenericResponse> call,
                                   Response<GenericResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(AdminAddCropActivity.this,
                            "Crop added successfully",
                            Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(AdminAddCropActivity.this,
                            "Failed: " + response.code(),
                            Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(AdminAddCropActivity.this,
                        t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}