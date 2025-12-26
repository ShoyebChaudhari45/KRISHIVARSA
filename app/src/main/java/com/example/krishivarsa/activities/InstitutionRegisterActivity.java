package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.requests.LocationRequest;
import com.example.krishivarsa.network.requests.RegisterRequest;
import com.example.krishivarsa.network.responses.GenericResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InstitutionRegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etMobile;
    EditText etVillage, etDistrict, etState;
    Spinner spType;
    Button btnSubmit;
    ProgressBar progressRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_register);

        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etMobile = findViewById(R.id.etMobile);

        etVillage = findViewById(R.id.etVillage);
        etDistrict = findViewById(R.id.etDistrict);
        etState = findViewById(R.id.etState);

        spType = findViewById(R.id.spUserType);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressRegister = findViewById(R.id.progressRegister);

        setupSpinner();

        btnSubmit.setOnClickListener(v -> submitRegistration());
    }

    private void setupSpinner() {

        String[] types = {"public", "private"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                types
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spType.setAdapter(adapter);
    }

    private void submitRegistration() {

        String name = etName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String contact = etMobile.getText().toString().trim();
        String userType = spType.getSelectedItem().toString();

        String village = etVillage.getText().toString().trim();
        String district = etDistrict.getText().toString().trim();
        String state = etState.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(password) || TextUtils.isEmpty(contact)
                || TextUtils.isEmpty(village) || TextUtils.isEmpty(district)
                || TextUtils.isEmpty(state)) {
            if (password.length() < 6) {
                Toast.makeText(this,
                        "Password must be at least 6 characters",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            if (!contact.startsWith("+91")) {
                contact = "+91" + contact;
            }

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        btnSubmit.setEnabled(false);
        progressRegister.setVisibility(View.VISIBLE);

        LocationRequest location =
                new LocationRequest(village, district, state);

        RegisterRequest request =
                new RegisterRequest(
                        name,
                        email,
                        password,
                        contact,
                        "institution",
                        userType,
                        location
                );

        ApiService apiService =
                ApiClient.getClient().create(ApiService.class);

        apiService.registerUser(request)
                .enqueue(new Callback<GenericResponse>() {

                    @Override
                    public void onResponse(Call<GenericResponse> call,
                                           Response<GenericResponse> response) {

                        btnSubmit.setEnabled(true);
                        progressRegister.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null) {

                            Toast.makeText(
                                    InstitutionRegisterActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();

                            startActivity(new Intent(
                                    InstitutionRegisterActivity.this,
                                    LoginActivity.class
                            ));
                            finish();

                        } else if (response.code() == 409) {

                            Toast.makeText(
                                    InstitutionRegisterActivity.this,
                                    "Email already registered",
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {

                            Toast.makeText(
                                    InstitutionRegisterActivity.this,
                                    "Registration failed",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<GenericResponse> call, Throwable t) {

                        btnSubmit.setEnabled(true);
                        progressRegister.setVisibility(View.GONE);

                        Toast.makeText(
                                InstitutionRegisterActivity.this,
                                "Server error",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
    }
}
