package com.example.krishivarsa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.models.Entry;

import java.util.List;

public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.ViewHolder> {

    private Context context;
    private List<Entry> entryList;

    public EntryAdapter(Context context, List<Entry> entryList) {
        this.context = context;
        this.entryList = entryList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Entry entry = entryList.get(position);

        holder.tvCrop.setText("Crop: " + entry.getCropName());
        holder.tvVariety.setText("Variety/Landrace: " + entry.getVarietyName());
        holder.tvContributor.setText("Contributor: " + entry.getContributor());
        holder.tvThreat.setText("Threat: " + entry.getThreatLevel());

        holder.btnApprove.setOnClickListener(v ->
                Toast.makeText(context,
                        "Approved entry " + entry.getId(),
                        Toast.LENGTH_SHORT).show()
        );

        holder.btnDelete.setOnClickListener(v ->
                Toast.makeText(context,
                        "Deleted entry " + entry.getId(),
                        Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return entryList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvCrop, tvVariety, tvContributor, tvThreat;
        Button btnApprove, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCrop = itemView.findViewById(R.id.tvCrop);
            tvVariety = itemView.findViewById(R.id.tvVariety);
            tvContributor = itemView.findViewById(R.id.tvContributor);
            tvThreat = itemView.findViewById(R.id.tvThreat);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
