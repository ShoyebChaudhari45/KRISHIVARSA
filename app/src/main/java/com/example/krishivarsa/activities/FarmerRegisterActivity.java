package com.example.krishivarsa.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;

public class FarmerRegisterActivity extends AppCompatActivity {

    EditText etName, etContact, etEmail, etVillage, etDistrict, etState;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_register);

        etName = findViewById(R.id.etName);
        etContact = findViewById(R.id.etContact);
        etEmail = findViewById(R.id.etEmail);
        etVillage = findViewById(R.id.etVillage);
        etDistrict = findViewById(R.id.etDistrict);
        etState = findViewById(R.id.etState);
        btnSubmit = findViewById(R.id.btnSubmit);

        btnSubmit.setOnClickListener(v -> submitRegistration());
    }

    private void submitRegistration() {

        String name = etName.getText().toString().trim();
        String contact = etContact.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String village = etVillage.getText().toString().trim();
        String district = etDistrict.getText().toString().trim();
        String state = etState.getText().toString().trim();

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(contact)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(village)
                || TextUtils.isEmpty(district) || TextUtils.isEmpty(state)) {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         * 🔗 API CALL (Flask)
         * POST /api/register/farmer
         *
         * body:
         * {
         *   name,
         *   contact,
         *   email,
         *   village,
         *   district,
         *   state
         * }
         *
         * Response:
         * {
         *   status: "PENDING"
         * }
         */

        Toast.makeText(
                this,
                "Registration submitted. Await admin approval.",
                Toast.LENGTH_LONG
        ).show();

        finish();
    }
}
