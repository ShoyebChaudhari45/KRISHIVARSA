package com.example.krishivarsa.activities.admin;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

public class AdminAddVarietyActivity extends AppCompatActivity {

    EditText etName, etDescription;
    Spinner spType, spThreat;
    Button btnImage, btnSubmit;
    ProgressBar progress;

    Uri imageUri;
    SessionManager session;
    ApiService api;

    String cropId; // 🔴 PASSED FROM PREVIOUS SCREEN

    private static final int PICK_IMAGE = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_variety);

        etName = findViewById(R.id.etName);
        etDescription = findViewById(R.id.etDescription);
        spType = findViewById(R.id.spType);
        spThreat = findViewById(R.id.spThreat);
        btnImage = findViewById(R.id.btnImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        progress = findViewById(R.id.progress);

        session = new SessionManager(this);
        api = ApiClient.getClient().create(ApiService.class);

        // ✅ GET cropId from previous activity
        cropId = getIntent().getStringExtra("crop_id");

        if (cropId == null) {
            Toast.makeText(this, "Crop not selected", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        setupSpinners();

        btnImage.setOnClickListener(v -> pickImage());
        btnSubmit.setOnClickListener(v -> submitVariety());
    }

    // ---------------- SPINNERS ----------------

    private void setupSpinners() {

        spType.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{
                        "traditional_landrace",
                        "improved_variety",
                        "hybrid",
                        "wild_relative"
                }
        ));

        spThreat.setAdapter(new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{
                        "critically_endangered",
                        "endangered",
                        "vulnerable",
                        "not_threatened"
                }
        ));
    }

    // ---------------- IMAGE PICK ----------------

    private void pickImage() {
        Intent intent = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
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

    private void submitVariety() {

        String name = etName.getText().toString().trim();
        String description = etDescription.getText().toString().trim();

        // ✅ VALIDATIONS
        if (name.isEmpty()) {
            etName.setError("Variety name required");
            etName.requestFocus();
            return;
        }

        if (description.isEmpty()) {
            etDescription.setError("Description required");
            etDescription.requestFocus();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);

        try {

            MultipartBody.Part imagePart = prepareImage(imageUri);

            List<RequestBody> characteristics = Arrays.asList(
                    rb("admin"),
                    rb("verified-source")
            );

            api.addVariety(
                    "Bearer " + session.getToken(),

                    rb(cropId),
                    rb(name),
                    rb(spType.getSelectedItem().toString()),
                    rb(mapGermplasm(spType.getSelectedItem().toString())),

                    rb("Village"),
                    rb("District"),
                    rb("State"),

                    rb("+919999999999"),

                    characteristics,

                    rb("Added by admin"),
                    rb(description),
                    rb(spThreat.getSelectedItem().toString()),

                    imagePart

            ).enqueue(new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call,
                                       Response<GenericResponse> response) {

                    progress.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        Toast.makeText(
                                AdminAddVarietyActivity.this,
                                "Variety added successfully",
                                Toast.LENGTH_LONG
                        ).show();
                        finish();
                    } else if (response.code() == 400) {
                        Toast.makeText(
                                AdminAddVarietyActivity.this,
                                "Variety already exists or invalid data",
                                Toast.LENGTH_LONG
                        ).show();
                    } else if (response.code() == 401) {
                        Toast.makeText(
                                AdminAddVarietyActivity.this,
                                "Session expired. Login again",
                                Toast.LENGTH_LONG
                        ).show();
                    } else {
                        Toast.makeText(
                                AdminAddVarietyActivity.this,
                                "Server error: " + response.code(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }

                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(
                            AdminAddVarietyActivity.this,
                            "Network error: " + t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

        } catch (Exception e) {
            progress.setVisibility(View.GONE);
            Toast.makeText(this, "Image processing error", Toast.LENGTH_LONG).show();
        }
    }

    // ---------------- HELPERS ----------------

    private MultipartBody.Part prepareImage(Uri uri) throws Exception {

        InputStream is = getContentResolver().openInputStream(uri);
        File file = new File(getCacheDir(), "upload.jpg");

        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[1024];
        int len;
        while ((len = is.read(buffer)) > 0) {
            fos.write(buffer, 0, len);
        }

        fos.close();
        is.close();

        RequestBody req =
                RequestBody.create(file, MediaType.parse("image/*"));

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

    private String mapGermplasm(String type) {
        switch (type) {
            case "traditional_landrace":
                return "traditional_landraces";
            case "improved_variety":
                return "improved_varieties";
            case "hybrid":
                return "hybrids";
            default:
                return "wild_relatives";
        }
    }
}
