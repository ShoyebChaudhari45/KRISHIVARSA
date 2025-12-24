package com.example.krishivarsa.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;

public class InstitutionRegisterActivity extends AppCompatActivity {

    EditText etInstitutionName, etContactPerson, etContactNumber, etEmail, etLocation;
    Spinner spInstitutionType;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_institution_register);

        etInstitutionName = findViewById(R.id.etInstitutionName);
        spInstitutionType = findViewById(R.id.spInstitutionType);
        etContactPerson = findViewById(R.id.etContactPerson);
        etContactNumber = findViewById(R.id.etContactNumber);
        etEmail = findViewById(R.id.etEmail);
        etLocation = findViewById(R.id.etLocation);
        btnSubmit = findViewById(R.id.btnSubmit);

        setupInstitutionTypeSpinner();

        btnSubmit.setOnClickListener(v -> submitRegistration());
    }

    private void setupInstitutionTypeSpinner() {
        String[] types = {
                "Select Institution Type",
                "Public",
                "Private",
                "NGO",
                "Seed Bank"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                types
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spInstitutionType.setAdapter(adapter);
    }

    private void submitRegistration() {

        String institutionName = etInstitutionName.getText().toString().trim();
        String institutionType = spInstitutionType.getSelectedItem().toString();
        String contactPerson = etContactPerson.getText().toString().trim();
        String contactNumber = etContactNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String location = etLocation.getText().toString().trim();

        if (TextUtils.isEmpty(institutionName)
                || institutionType.equals("Select Institution Type")
                || TextUtils.isEmpty(contactPerson)
                || TextUtils.isEmpty(contactNumber)
                || TextUtils.isEmpty(email)
                || TextUtils.isEmpty(location)) {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         * 🔗 API CALL (Flask)
         * POST /api/register/institution
         *
         * body:
         * {
         *   institution_name,
         *   institution_type,
         *   contact_person,
         *   contact_number,
         *   email,
         *   location
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
