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
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.requests.LoginRequest;
import com.example.krishivarsa.network.responses.LoginResponse;
import com.example.krishivarsa.utils.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;
    ProgressBar progressLogin;

    SessionManager sessionManager;

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

                if (response.isSuccessful() && response.body() != null) {

                    // 🔒 STATUS CHECK
                    if (!response.body().getUser().getStatus().equalsIgnoreCase("active")) {
                        Toast.makeText(
                                LoginActivity.this,
                                "Your account is pending admin approval",
                                Toast.LENGTH_LONG
                        ).show();
                        return;
                    }

                    String token = response.body().getToken();
                    String role = response.body().getUser().getRole().toUpperCase();

                    sessionManager.saveLogin(token, role);

                    Toast.makeText(LoginActivity.this,
                            "Login successful",
                            Toast.LENGTH_SHORT).show();

                    navigateByRole(role);

                } else {
                    Toast.makeText(LoginActivity.this,
                            "Invalid email or password",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressLogin.setVisibility(View.GONE);
                btnLogin.setEnabled(true);

                Toast.makeText(LoginActivity.this,
                        "Server error: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void navigateByRole(String role) {

        if (role.equals("ADMIN")) {
            startActivity(new Intent(this, AdminDashboardActivity.class));
        }
        else if (role.equals("FARMER")) {
            startActivity(new Intent(this, FarmerDashboardActivity.class));
        }
        else if (role.equals("INSTITUTION")) {
            startActivity(new Intent(this, InstitutionDashboardActivity.class));
        }

        finish();
    }
}
