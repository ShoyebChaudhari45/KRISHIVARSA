package com.example.krishivarsa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.models.Crop;

import java.util.List;

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.CropViewHolder> {

    private Context context;
    private List<Crop> cropList;

    public CropAdapter(Context context, List<Crop> cropList) {
        this.context = context;
        this.cropList = cropList;
    }

    @NonNull
    @Override
    public CropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_crop, parent, false);
        return new CropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CropViewHolder holder, int position) {

        Crop crop = cropList.get(position);

        holder.tvCropName.setText(crop.getCropName());
        holder.tvVariety.setText("Variety: " + crop.getVarietyName());
        holder.tvLocation.setText("Location: " + crop.getLocation());
        holder.tvThreat.setText("Threat: " + crop.getThreatLevel());
    }

    @Override
    public int getItemCount() {
        return cropList.size();
    }

    static class CropViewHolder extends RecyclerView.ViewHolder {

        TextView tvCropName, tvVariety, tvLocation, tvThreat;

        public CropViewHolder(@NonNull View itemView) {
            super(itemView);

            tvCropName = itemView.findViewById(R.id.tvCropName);
            tvVariety = itemView.findViewById(R.id.tvVariety);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvThreat = itemView.findViewById(R.id.tvThreat);
        }
    }
}
