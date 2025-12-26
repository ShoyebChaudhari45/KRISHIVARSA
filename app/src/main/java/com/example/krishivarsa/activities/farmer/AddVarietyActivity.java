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

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddVarietyActivity extends AppCompatActivity {

    EditText etVarietyName, etDescription;
    Spinner spType, spThreat;
    Button btnImage, btnSubmit;
    ProgressBar progress;

    Uri imageUri;
    SessionManager sessionManager;

    private static final int IMAGE_PICK = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_variety);

        etVarietyName = findViewById(R.id.etVarietyName);
        etDescription = findViewById(R.id.etDescription);
        spType = findViewById(R.id.spType);
        spThreat = findViewById(R.id.spThreat);
        btnImage = findViewById(R.id.btnImage);
        btnSubmit = findViewById(R.id.btnSubmit);
        progress = findViewById(R.id.progress);

        sessionManager = new SessionManager(this);

        setupSpinners();

        btnImage.setOnClickListener(v -> pickImage());
        btnSubmit.setOnClickListener(v -> submitVariety());
    }

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

    private void pickImage() {
        Intent i = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void submitVariety() {

        if (imageUri == null) {
            Toast.makeText(this, "Please select image", Toast.LENGTH_SHORT).show();
            return;
        }

        progress.setVisibility(View.VISIBLE);

        try {
            ApiService api = ApiClient.getClient().create(ApiService.class);
            MultipartBody.Part imagePart = prepareImage("image", imageUri);

            // ✅ ARRAY FIX (NO CAST)
            List<RequestBody> characteristics = new ArrayList<>();
            characteristics.add(rb("aroma"));
            characteristics.add(rb("long grain"));

            Log.d("ADD_VARIETY", "Submitting variety...");

            api.addVariety(
                    "Bearer " + sessionManager.getToken(),

                    rb("694ceaa656a257f27771cbd6"), // REAL crop id
                    rb(etVarietyName.getText().toString().trim()),
                    rb(spType.getSelectedItem().toString()),
                    rb(getGermplasm(spType.getSelectedItem().toString())),

                    rb("Dehradun"),
                    rb("Dehradun"),
                    rb("Uttarakhand"),

                    rb("+919876543210"),

                    characteristics, // ✅ CORRECT

                    rb("Traditional variety"),
                    rb(etDescription.getText().toString().trim()),
                    rb(spThreat.getSelectedItem().toString()),

                    imagePart
            ).enqueue(new Callback<GenericResponse>() {

                @Override
                public void onResponse(Call<GenericResponse> call,
                                       Response<GenericResponse> response) {

                    progress.setVisibility(View.GONE);

                    if (response.isSuccessful()) {
                        Toast.makeText(
                                AddVarietyActivity.this,
                                "Variety submitted successfully",
                                Toast.LENGTH_LONG
                        ).show();
                        finish();
                    } else {
                        try {
                            String errorBody = response.errorBody() != null
                                    ? response.errorBody().string()
                                    : "null";

                            Log.e("ADD_VARIETY_ERROR", "Code: " + response.code());
                            Log.e("ADD_VARIETY_ERROR", "ErrorBody: " + errorBody);

                            Toast.makeText(
                                    AddVarietyActivity.this,
                                    "Failed " + response.code(),
                                    Toast.LENGTH_LONG
                            ).show();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onFailure(Call<GenericResponse> call, Throwable t) {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(
                            AddVarietyActivity.this,
                            t.getMessage(),
                            Toast.LENGTH_LONG
                    ).show();
                }
            });

        } catch (Exception e) {
            progress.setVisibility(View.GONE);
            e.printStackTrace();
            Toast.makeText(this, "Upload error", Toast.LENGTH_SHORT).show();
        }
    }

    private String getGermplasm(String type) {
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

    private MultipartBody.Part prepareImage(String name, Uri uri) throws Exception {

        InputStream is = getContentResolver().openInputStream(uri);
        File file = new File(getCacheDir(), "upload.jpg");

        FileOutputStream fos = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int len;
        while ((len = is.read(buf)) > 0) {
            fos.write(buf, 0, len);
        }

        fos.close();
        is.close();

        RequestBody req =
                RequestBody.create(file, MediaType.parse("image/*"));

        return MultipartBody.Part.createFormData(name, file.getName(), req);
    }

    private RequestBody rb(String v) {
        return RequestBody.create(v, MediaType.parse("text/plain"));
    }
}
