package com.example.krishivarsa.activities.farmer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.GenericResponse;
import com.example.krishivarsa.utils.SessionManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddLandraceVarietyActivity extends AppCompatActivity {

    // UI
    EditText etLandraceName, etCharacteristics;
    Button btnImage, btnSubmit;
    ProgressBar progress;
    Spinner spThreatLevel;

    // Data
    Uri imageUri;
    String cropId;
    String selectedThreatLevel = "not_threatened";

    // Network
    SessionManager session;
    ApiService api;

    private static final int PICK_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_landrace_variety);

        // -------- INIT VIEWS --------
        etLandraceName = findViewById(R.id.etLandraceName);
        etCharacteristics = findViewById(R.id.etCharacteristics);
        btnImage = findViewById(R.id.btnImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        progress = findViewById(R.id.progress);
        spThreatLevel = findViewById(R.id.spThreatLevel);

        // -------- GET CROP ID --------
        cropId = getIntent().getStringExtra("crop_id");
        Log.d("CROP_DEBUG", "CropId = " + cropId);

        if (cropId == null) {
            Toast.makeText(this, "Crop not selected", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        // -------- SESSION & API --------
        session = new SessionManager(this);
        api = ApiClient.getClient().create(ApiService.class);

        // -------- SPINNER --------
        setupThreatSpinner();

        // -------- CLICKS --------
        btnImage.setOnClickListener(v -> pickImage());
        btnSubmit.setOnClickListener(v -> submit());
    }

    // ---------------- THREAT LEVEL SPINNER ----------------
    private void setupThreatSpinner() {

        String[] threatLevels = {
                "critically_endangered",
                "endangered",
                "vulnerable",
                "not_threatened"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                threatLevels
        );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spThreatLevel.setAdapter(adapter);
        spThreatLevel.setSelection(3);

        spThreatLevel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedThreatLevel = threatLevels[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    // ---------------- IMAGE PICK ----------------
    private void pickImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*"); // ONLY IMAGES
        startActivityForResult(intent, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    // ---------------- SUBMIT ----------------
    private void submit() {

        String name = etLandraceName.getText().toString().trim();
        String description = etCharacteristics.getText().toString().trim();

        if (name.isEmpty()) {
            etLandraceName.setError("Landrace name required");
            etLandraceName.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            etCharacteristics.setError("Description required");
            etCharacteristics.requestFocus();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Select image", Toast.LENGTH_SHORT).show();
            return;
        }

        String mime = getContentResolver().getType(imageUri);
        if (mime == null || !mime.startsWith("image/")) {
            Toast.makeText(this, "Only image files are allowed", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);

        try {
            MultipartBody.Part image = prepareImage(imageUri);

            List<RequestBody> characteristics =
                    Arrays.asList(rb(description));

            api.addVariety(
                    "Bearer " + session.getToken(),

                    rb(cropId),
                    rb(name),

                    rb("traditional_landrace"),
                    rb("traditional_landraces"),

                    rb("Village"),
                    rb("District"),
                    rb("State"),

                    rb("+919999999999"),

                    characteristics,
                    rb("Farmer submitted"),
                    rb(description),
                    rb(selectedThreatLevel),

                    image
            ).enqueue(new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call,
                                       Response<GenericResponse> response) {

                    progress.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        Toast.makeText(
                                AddLandraceVarietyActivity.this,
                                "Submitted for admin approval",
                                Toast.LENGTH_LONG
                        ).show();
                        finish();
                    } else {
                        Toast.makeText(
                                AddLandraceVarietyActivity.this,
                                "Failed: " + response.code(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(
                            AddLandraceVarietyActivity.this,
                            t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

        } catch (Exception e) {
            progress.setVisibility(View.GONE);
            Toast.makeText(this, "Image upload error", Toast.LENGTH_SHORT).show();
        }
    }

    // ---------------- IMAGE PREP ----------------
    private MultipartBody.Part prepareImage(Uri uri) throws Exception {

        String mimeType = getContentResolver().getType(uri);

        InputStream is = getContentResolver().openInputStream(uri);
        File file = new File(
                getCacheDir(),
                "upload_" + System.currentTimeMillis() + ".jpg"
        );

        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[4096];
        int len;
        while ((len = is.read(buffer)) != -1) {
            fos.write(buffer, 0, len);
        }

        fos.close();
        is.close();

        RequestBody req = RequestBody.create(
                file,
                MediaType.parse(mimeType)
        );

        return MultipartBody.Part.createFormData(
                "image",
                file.getName(),
                req
        );
    }

    private RequestBody rb(String value) {
        return RequestBody.create(
                value,
                MediaType.parse("text/plain")
        );
    }
}
