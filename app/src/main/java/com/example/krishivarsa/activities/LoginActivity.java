package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.activities.admin.AdminDashboardActivity;
import com.example.krishivarsa.activities.farmer.FarmerDashboardActivity;
import com.example.krishivarsa.activities.institution.InstitutionDashboardActivity;
import com.example.krishivarsa.models.User; // ✅ IMPORTANT
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.requests.LoginRequest;
import com.example.krishivarsa.network.responses.LoginResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private ProgressBar progressLogin;

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);
        progressLogin = findViewById(R.id.progressLogin);

        sessionManager = new SessionManager(this);

        // ✅ AUTO LOGIN
        if (sessionManager.isLoggedIn()) {
            navigateByRole(sessionManager.getRole());
            finish();
            return;
        }

        btnLogin.setOnClickListener(v -> loginUser());

        tvRegister.setOnClickListener(v ->
                startActivity(new Intent(this, RegisterChoiceActivity.class))
        );
    }

    private void loginUser() {

        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        progressLogin.setVisibility(View.VISIBLE);
        btnLogin.setEnabled(false);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        LoginRequest request = new LoginRequest(email, password);

        apiService.login(request).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call,
                                   Response<LoginResponse> response) {

                progressLogin.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                if (!response.isSuccessful() || response.body() == null) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                LoginResponse loginResponse = response.body();

                if (!loginResponse.isSuccess()) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Login failed",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }

                User user = loginResponse.getUser();

                // 🔒 ADMIN APPROVAL CHECK
                if (!user.isApproved()) {
                    Toast.makeText(
                            LoginActivity.this,
                            "Your account is pending admin approval",
                            Toast.LENGTH_LONG
                    ).show();
                    return;
                }

                // ✅ SAVE SESSION
                sessionManager.saveLogin(
                        loginResponse.getToken(),
                        user.getRole().toUpperCase()
                );

                navigateByRole(user.getRole().toUpperCase());
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressLogin.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                Toast.makeText(
                        LoginActivity.this,
                        "Server error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    private void navigateByRole(String role) {

        switch (role) {
            case "ADMIN":
                startActivity(new Intent(this, AdminDashboardActivity.class));
                break;

            case "FARMER":
                startActivity(new Intent(this, FarmerDashboardActivity.class));
                break;

            case "INSTITUTION":
                startActivity(new Intent(this, InstitutionDashboardActivity.class));
                break;

            default:
                Toast.makeText(this, "Invalid role", Toast.LENGTH_SHORT).show();
                return;
        }

        finish();
    }
}
