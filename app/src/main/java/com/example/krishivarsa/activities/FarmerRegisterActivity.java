package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.requests.AddressRequest;
import com.example.krishivarsa.network.requests.FarmerRegisterRequest;
import com.example.krishivarsa.network.responses.FarmerRegisterResponse;
import com.google.gson.Gson;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FarmerRegisterActivity extends AppCompatActivity {

    EditText etName, etMobile, etEmail, etPassword, etAge;
    EditText etVillage, etDistrict, etState, etPincode;
    EditText etExperience, etLand, etCrops;
    RadioGroup rgGender;

    Button btnSubmit;
    ProgressBar progressRegister;

    String[] cropOptions = {
            "Rice", "Wheat", "Sugarcane", "Millet", "Maize", "Pulses"
    };
    boolean[] selectedCrops;
    ArrayList<String> selectedCropList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farmer_register);

        initViews();

        selectedCrops = new boolean[cropOptions.length];

        etCrops.setOnClickListener(v -> showCropDialog());
        btnSubmit.setOnClickListener(v -> submitRegistration());
    }

    private void initViews() {
        etName = findViewById(R.id.etName);
        etMobile = findViewById(R.id.etMobile);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etAge = findViewById(R.id.etAge);

        etVillage = findViewById(R.id.etVillage);
        etDistrict = findViewById(R.id.etDistrict);
        etState = findViewById(R.id.etState);
        etPincode = findViewById(R.id.etPincode);

        etExperience = findViewById(R.id.etExperience);
        etLand = findViewById(R.id.etLand);
        etCrops = findViewById(R.id.etCrops);

        rgGender = findViewById(R.id.rgGender);
        btnSubmit = findViewById(R.id.btnSubmit);
        progressRegister = findViewById(R.id.progressRegister);
    }

    /* ---------------- CROPS MULTI SELECT ---------------- */

    private void showCropDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Crops Grown");

        builder.setMultiChoiceItems(
                cropOptions,
                selectedCrops,
                (dialog, which, isChecked) -> {
                    if (isChecked) {
                        selectedCropList.add(cropOptions[which]);
                    } else {
                        selectedCropList.remove(cropOptions[which]);
                    }
                });

        builder.setPositiveButton("OK", (dialog, which) ->
                etCrops.setText(TextUtils.join(", ", selectedCropList)));

        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    /* ---------------- SUBMIT ---------------- */

    private void submitRegistration() {

        String name = etName.getText().toString().trim();
        String mobile = etMobile.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String ageStr = etAge.getText().toString().trim();

        String village = etVillage.getText().toString().trim();
        String district = etDistrict.getText().toString().trim();
        String state = etState.getText().toString().trim();
        String pincode = etPincode.getText().toString().trim();

        String expStr = etExperience.getText().toString().trim();
        String landStr = etLand.getText().toString().trim();

        int genderId = rgGender.getCheckedRadioButtonId();

        /* ---------- VALIDATION ---------- */

        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(mobile)
                || TextUtils.isEmpty(email) || TextUtils.isEmpty(password)
                || TextUtils.isEmpty(ageStr) || genderId == -1
                || TextUtils.isEmpty(village) || TextUtils.isEmpty(district)
                || TextUtils.isEmpty(state) || TextUtils.isEmpty(pincode)
                || TextUtils.isEmpty(expStr) || TextUtils.isEmpty(landStr)
                || selectedCropList.isEmpty()) {

            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mobile.length() != 10) {
            Toast.makeText(this, "Enter valid 10-digit mobile number", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton rb = findViewById(genderId);
        String gender = rb.getText().toString();

        /* ---------- DISABLE BUTTON + SHOW LOADER ---------- */

        btnSubmit.setEnabled(false);
        progressRegister.setVisibility(View.VISIBLE);

        AddressRequest address = new AddressRequest(
                village, district, state, pincode
        );

        FarmerRegisterRequest request = new FarmerRegisterRequest(
                email,
                mobile,
                password,
                name,
                Integer.parseInt(ageStr),
                gender,
                address,
                Integer.parseInt(expStr),
                Integer.parseInt(landStr),
                selectedCropList
        );

        // DEBUG (optional)
        Log.d("REGISTER_REQ", new Gson().toJson(request));

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        apiService.registerFarmer(request)
                .enqueue(new Callback<FarmerRegisterResponse>() {

                    @Override
                    public void onResponse(Call<FarmerRegisterResponse> call,
                                           Response<FarmerRegisterResponse> response) {

                        btnSubmit.setEnabled(true);
                        progressRegister.setVisibility(View.GONE);

                        if (response.isSuccessful() && response.body() != null) {

                            Toast.makeText(
                                    FarmerRegisterActivity.this,
                                    response.body().getMessage(),
                                    Toast.LENGTH_LONG
                            ).show();

                            clearForm();   // ✅ CLEAR FORM

                            startActivity(new Intent(
                                    FarmerRegisterActivity.this,
                                    LoginActivity.class
                            ));
                            finish();

                        } else if (response.code() == 409) {
                            // ✅ EMAIL ALREADY EXISTS
                            Toast.makeText(
                                    FarmerRegisterActivity.this,
                                    "Email already registered. Please login.",
                                    Toast.LENGTH_LONG
                            ).show();
                        } else {
                            Toast.makeText(
                                    FarmerRegisterActivity.this,
                                    "Registration failed. Try again.",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<FarmerRegisterResponse> call, Throwable t) {

                        btnSubmit.setEnabled(true);
                        progressRegister.setVisibility(View.GONE);

                        Toast.makeText(
                                FarmerRegisterActivity.this,
                                "Server error: " + t.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();

                        t.printStackTrace();
                    }
                });
    }

    /* ---------------- CLEAR FORM ---------------- */

    private void clearForm() {

        etName.setText("");
        etMobile.setText("");
        etEmail.setText("");
        etPassword.setText("");
        etAge.setText("");
        etVillage.setText("");
        etDistrict.setText("");
        etState.setText("");
        etPincode.setText("");
        etExperience.setText("");
        etLand.setText("");
        etCrops.setText("");

        rgGender.clearCheck();

        selectedCropList.clear();
        selectedCrops = new boolean[cropOptions.length];
    }
}
