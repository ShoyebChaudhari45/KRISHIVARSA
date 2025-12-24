package com.example.krishivarsa.activities.farmer;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;

public class AddLandraceActivity extends AppCompatActivity {

    EditText etCropName, etLandraceName, etLocation, etCharacteristics;
    Spinner spThreatLevel;
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_landrace);

        etCropName = findViewById(R.id.etCropName);
        etLandraceName = findViewById(R.id.etLandraceName);
        etLocation = findViewById(R.id.etLocation);
        etCharacteristics = findViewById(R.id.etCharacteristics);
        spThreatLevel = findViewById(R.id.spThreatLevel);
        btnSubmit = findViewById(R.id.btnSubmit);

        setupThreatLevelSpinner();

        btnSubmit.setOnClickListener(v -> submitLandrace());
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

    private void submitLandrace() {

        String cropName = etCropName.getText().toString().trim();
        String landraceName = etLandraceName.getText().toString().trim();
        String location = etLocation.getText().toString().trim();
        String characteristics = etCharacteristics.getText().toString().trim();
        String threatLevel = spThreatLevel.getSelectedItem().toString();

        if (TextUtils.isEmpty(cropName)
                || TextUtils.isEmpty(landraceName)
                || TextUtils.isEmpty(location)
                || TextUtils.isEmpty(characteristics)
                || threatLevel.equals("Select Threat Level")) {

            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        /*
         * 🔗 API CALL (Flask)
         * POST /api/farmer/landrace/add
         *
         * body:
         * {
         *   crop_name,
         *   landrace_name,
         *   location,
         *   characteristics,
         *   threat_level
         * }
         *
         * status = PENDING (Admin Review)
         */

        Toast.makeText(
                this,
                "Landrace submitted for review",
                Toast.LENGTH_LONG
        ).show();

        finish();
    }
}
