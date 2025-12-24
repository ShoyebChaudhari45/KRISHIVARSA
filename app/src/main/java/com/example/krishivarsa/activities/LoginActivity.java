package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.activities.admin.AdminDashboardActivity;
import com.example.krishivarsa.activities.farmer.FarmerDashboardActivity;
import com.example.krishivarsa.activities.institution.InstitutionDashboardActivity;
import com.example.krishivarsa.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnLogin;
    TextView tvRegister;

    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        sessionManager = new SessionManager(this);

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

        /*
         * 🔗 API CALL (Flask)
         * POST /api/auth/login
         * body: { email, password }
         *
         * Response:
         * {
         *   token: "...",
         *   role: "ADMIN" | "FARMER" | "INSTITUTION"
         * }
         */

        // 🔧 TEMP DUMMY RESPONSE
        String token = "dummy_jwt_token";
        String role = "FARMER"; // change to ADMIN / INSTITUTION for testing

        sessionManager.saveLogin(token, role);

        navigateByRole(role);
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
