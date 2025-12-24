package com.example.krishivarsa.activities.admin;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.krishivarsa.R;

public class AnalyticsActivity extends AppCompatActivity {

    TextView tvTotalCrops, tvTotalVarieties;
    TextView tvCritical, tvEndangered, tvVulnerable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        tvTotalCrops = findViewById(R.id.tvTotalCrops);
        tvTotalVarieties = findViewById(R.id.tvTotalVarieties);
        tvCritical = findViewById(R.id.tvCritical);
        tvEndangered = findViewById(R.id.tvEndangered);
        tvVulnerable = findViewById(R.id.tvVulnerable);

        loadAnalytics();
    }

    private void loadAnalytics() {

        /*
         * 🔗 API CALL (Flask)
         * GET /api/admin/stats
         *
         * Response:
         * {
         *   total_crops,
         *   total_varieties,
         *   threat_levels: {
         *      critical,
         *      endangered,
         *      vulnerable
         *   }
         * }
         */

        // 🔧 Dummy data for now
        tvTotalCrops.setText("Total Crops: 42");
        tvTotalVarieties.setText("Total Varieties / Landraces: 128");
        tvCritical.setText("Critically Endangered: 18");
        tvEndangered.setText("Endangered: 47");
        tvVulnerable.setText("Vulnerable: 63");
    }
}
