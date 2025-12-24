package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;

public class SplashActivity extends AppCompatActivity {

    // Splash screen duration (3 seconds)
    private static final int SPLASH_TIME = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(
                    SplashActivity.this,
                    PublicHomeActivity.class
            );
            startActivity(intent);
            finish();
        }, SPLASH_TIME);
    }
}
