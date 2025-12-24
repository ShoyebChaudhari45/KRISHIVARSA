package com.example.krishivarsa.activities.admin;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.adapters.EntryAdapter;
import com.example.krishivarsa.models.Entry;

import java.util.ArrayList;
import java.util.List;

public class ManageEntriesActivity extends AppCompatActivity {

    RecyclerView recyclerEntries;
    EntryAdapter adapter;
    List<Entry> entryList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_entries);

        recyclerEntries = findViewById(R.id.recyclerEntries);
        recyclerEntries.setLayoutManager(new LinearLayoutManager(this));

        entryList = new ArrayList<>();

        // 🔧 Dummy entries (API later)
        entryList.add(new Entry(1, "Rice", "Indrayani", "Farmer", "Vulnerable"));
        entryList.add(new Entry(2, "Wheat", "Lokwan", "Institution", "Endangered"));

        adapter = new EntryAdapter(this, entryList);
        recyclerEntries.setAdapter(adapter);
    }
}
