package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;

public class RegisterChoiceActivity extends AppCompatActivity {

    Button btnFarmer, btnInstitution;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_choice);

        btnFarmer = findViewById(R.id.btnFarmer);
        btnInstitution = findViewById(R.id.btnInstitution);

        // Navigate to Farmer Registration
        btnFarmer.setOnClickListener(v ->
                startActivity(new Intent(this, FarmerRegisterActivity.class))
        );

        // Navigate to Institution Registration
        btnInstitution.setOnClickListener(v ->
                startActivity(new Intent(this, InstitutionRegisterActivity.class))
        );
    }
}
