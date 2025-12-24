package com.example.krishivarsa.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.CropAdapter;
import com.example.krishivarsa.models.Crop;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class PublicHomeActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    RecyclerView recyclerCrops;
    Toolbar toolbar;

    CropAdapter cropAdapter;
    List<Crop> cropList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        recyclerCrops = findViewById(R.id.recyclerCrops);
        toolbar = findViewById(R.id.toolbar);

        // 🔥 Set toolbar
        setSupportActionBar(toolbar);

        // 🔥 Add hamburger toggle
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.open_drawer,
                R.string.close_drawer
        );

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        recyclerCrops.setLayoutManager(new LinearLayoutManager(this));
        cropList = new ArrayList<>();

        loadDummyCrops();
        cropAdapter = new CropAdapter(this, cropList);
        recyclerCrops.setAdapter(cropAdapter);

        setupDrawerClicks();
    }

    private void setupDrawerClicks() {
        navigationView.setNavigationItemSelectedListener(item -> {

            int id = item.getItemId();

            if (id == R.id.menu_home) {
                drawerLayout.closeDrawers();
            } else if (id == R.id.menu_about) {
                startActivity(new Intent(this, AboutActivity.class));
            } else if (id == R.id.menu_login) {
                startActivity(new Intent(this, LoginActivity.class));
            } else if (id == R.id.menu_register) {
                startActivity(new Intent(this, RegisterChoiceActivity.class));
            }

            drawerLayout.closeDrawers();
            return true;
        });
    }

    private void loadDummyCrops() {
        cropList.add(new Crop("Rice", "Indrayani", "Maharashtra", "Vulnerable"));
        cropList.add(new Crop("Wheat", "Lokwan", "Madhya Pradesh", "Endangered"));
        cropList.add(new Crop("Millet", "Bajra", "Rajasthan", "Critically Endangered"));
    }
}
