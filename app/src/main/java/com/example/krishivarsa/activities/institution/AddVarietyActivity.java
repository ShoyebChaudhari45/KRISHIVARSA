//package com.example.krishivarsa.activities.institution;
//
//import android.content.Intent;
//import android.net.Uri;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.krishivarsa.R;
//import com.example.krishivarsa.network.ApiClient;
//import com.example.krishivarsa.network.ApiService;
//import com.example.krishivarsa.network.responses.GenericResponse;
//import com.example.krishivarsa.utils.SessionManager;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.RequestBody;
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//
//public class AddVarietyActivity extends AppCompatActivity {
//
//    EditText etName, etDescription;
//    Spinner spType, spThreat;
//    Button btnSelectImage, btnSubmit;
//
//    Uri imageUri;
//    SessionManager sessionManager;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_add_variety_institution);
//
//        etName = findViewById(R.id.etName);
//        etDescription = findViewById(R.id.etDescription);
//        spType = findViewById(R.id.spType);
//        spThreat = findViewById(R.id.spThreat);
//        btnSelectImage = findViewById(R.id.btnSelectImage);
//        btnSubmit = findViewById(R.id.btnSubmit);
//
//        sessionManager = new SessionManager(this);
//
//        setupSpinners();
//
//        btnSelectImage.setOnClickListener(v -> pickImage());
//        btnSubmit.setOnClickListener(v -> submitVariety());
//    }
//
//    private void setupSpinners() {
//        spType.setAdapter(new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_dropdown_item,
//                new String[]{"Seed", "Grain", "Fruit", "Vegetable"}
//        ));
//
//        spThreat.setAdapter(new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_dropdown_item,
//                new String[]{"Low", "Medium", "High"}
//        ));
//    }
//
//    private void pickImage() {
//        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("image/*");
//        startActivityForResult(intent, 101);
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 101 && resultCode == RESULT_OK) {
//            imageUri = data.getData();
//            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    private void submitVariety() {
//
//        if (imageUri == null) {
//            Toast.makeText(this, "Select image first", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        try {
//            File file = createTempFileFromUri(imageUri);
//
//            RequestBody name = RequestBody.create(
//                    etName.getText().toString(),
//                    MediaType.parse("text/plain")
//            );
//
//            RequestBody description = RequestBody.create(
//                    etDescription.getText().toString(),
//                    MediaType.parse("text/plain")
//            );
//
//            RequestBody type = RequestBody.create(
//                    spType.getSelectedItem().toString(),
//                    MediaType.parse("text/plain")
//            );
//
//            RequestBody threat = RequestBody.create(
//                    spThreat.getSelectedItem().toString(),
//                    MediaType.parse("text/plain")
//            );
//
//            MultipartBody.Part imagePart =
//                    MultipartBody.Part.createFormData(
//                            "image",
//                            file.getName(),
//                            RequestBody.create(file, MediaType.parse("image/*"))
//                    );
//
//            ApiService apiService =
//                    ApiClient.getClient().create(ApiService.class);
//
//            apiService.addVariety(
//                    "Bearer " + sessionManager.getToken(),
//                    name,
//                    description,
//                    type,
//                    threat,
//                    imagePart
//            ).enqueue(new Callback<GenericResponse>() {
//                @Override
//                public void onResponse(Call<GenericResponse> call,
//                                       Response<GenericResponse> response) {
//
//                    if (response.isSuccessful()) {
//                        Toast.makeText(com.example.krishivarsa.activities.institution.AddVarietyActivity.this,
//                                "Variety submitted successfully",
//                                Toast.LENGTH_LONG).show();
//                        finish();
//                    } else {
//                        Toast.makeText(com.example.krishivarsa.activities.institution.AddVarietyActivity.this,
//                                "Failed: " + response.code(),
//                                Toast.LENGTH_LONG).show();
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<GenericResponse> call, Throwable t) {
//                    Toast.makeText(com.example.krishivarsa.activities.institution.AddVarietyActivity.this,
//                            t.getMessage(),
//                            Toast.LENGTH_LONG).show();
//                }
//            });
//
//        } catch (Exception e) {
//            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//    }
//
//    // 🔥 MOST IMPORTANT FIX (NO FileUtils)
//    private File createTempFileFromUri(Uri uri) throws Exception {
//
//        InputStream inputStream = getContentResolver().openInputStream(uri);
//        File file = new File(getCacheDir(), "upload.jpg");
//
//        FileOutputStream outputStream = new FileOutputStream(file);
//        byte[] buffer = new byte[1024];
//        int read;
//
//        while ((read = inputStream.read(buffer)) != -1) {
//            outputStream.write(buffer, 0, read);
//        }
//
//        outputStream.close();
//        inputStream.close();
//
//        return file;
//    }
//}
