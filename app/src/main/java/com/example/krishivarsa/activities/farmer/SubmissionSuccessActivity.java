package com.example.krishivarsa.activities.farmer;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.krishivarsa.R;
public class SubmissionSuccessActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_submission_success);

        findViewById(R.id.btnMySubmissions)
                .setOnClickListener(v ->
                        startActivity(new Intent(this, MySubmissionsActivity.class))
                );
    }
}
