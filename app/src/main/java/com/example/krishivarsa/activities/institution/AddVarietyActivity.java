package com.example.krishivarsa.activities.institution;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;

public class AddVarietyActivity extends AppCompatActivity {

    EditText etCropName, etVarietyName, etLocation, etCharacteristics;
    Spinner spVarietyType, spThreatLevel;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_variety);

        etCropName = findViewById(R.id.etCropName);
        etVarietyName = findViewById(R.id.etVarietyName);
        etLocation = findViewById(R.id.etLocation);
        etCharacteristics = findViewById(R.id.etCharacteristics);

        spVarietyType = findViewById(R.id.spVarietyType);
        spThreatLevel = findViewById(R.id.spThreatLevel);

        btnSubmit = findViewById(R.id.btnSubmit);

        setupVarietyTypeSpinner();
        setupThreatLevelSpinner();

        btnSubmit.setOnClickListener(v -> submitVariety());
    }

    private void setupVarietyTypeSpinner() {
        String[] types = {
                "Select Variety Type",
                "Hybrid",
                "Improved Variety"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                types
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spVarietyType.setAdapter(adapter);
    }

    private void setupThreatLevelSpinner() {
        String[] threatLevels = {
                "Select Threat Level",
                "Critically Endangered",
                "Endangered",
                "Vulnerable"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                threatLevels
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThreatLevel.setAdapter(adapter);
    }

    private void submitVariety() {

        String cropName = etCropName.getText().toString().trim();
        String varietyName = etVarietyName.getText().toString().trim();
        String varietyType = spVarietyType.getSelectedItem().toString();
        String location = etLocation.getText().toString().trim();
        String characteristics = etCharacteristics.getText().toString().trim();
        String threatLevel = spThreatLevel.getSelectedItem().toString();

        if (TextUtils.isEmpty(cropName)
                || TextUtils.isEmpty(varietyName)
                || varietyType.equals("Select Variety Type")
                || TextUtils.isEmpty(location)
                || TextUtils.isEmpty(characteristics)
                || threatLevel.equals("Select Threat Level")) {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         * 🔗 API CALL (Flask)
         * POST /api/institution/variety/add
         *
         * body:
         * {
         *   crop_name,
         *   variety_name,
         *   variety_type,   // Hybrid / Improved
         *   location,
         *   characteristics,
         *   threat_level
         * }
         *
         * status = PENDING (Admin Review)
         */

        Toast.makeText(
                this,
                "Variety submitted for review",
                Toast.LENGTH_LONG
        ).show();

        finish();
    }
}
