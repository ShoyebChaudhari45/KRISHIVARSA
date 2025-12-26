package com.example.krishivarsa.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.network.responses.GetCropsResponse;

import java.util.List;

public class AdminCropAdapter
        extends RecyclerView.Adapter<AdminCropAdapter.Holder> {

    public interface OnCropClickListener {
        void onCropClick(GetCropsResponse.Crop crop);
    }

    private final List<GetCropsResponse.Crop> crops;
    private final OnCropClickListener listener;

    public AdminCropAdapter(List<GetCropsResponse.Crop> crops,
                            OnCropClickListener listener) {
        this.crops = crops;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crop, parent, false);

        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder h, int position) {

        GetCropsResponse.Crop c = crops.get(position);

        // ✅ SAFE: tvCropName is guaranteed now
        h.tvCropName.setText(c.name);

        h.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCropClick(c);
            }
        });
    }

    @Override
    public int getItemCount() {
        return crops == null ? 0 : crops.size();
    }

    static class Holder extends RecyclerView.ViewHolder {

        TextView tvCropName;

        Holder(@NonNull View itemView) {
            super(itemView);

            // ⚠️ ID MUST MATCH XML
            tvCropName = itemView.findViewById(R.id.tvCropName);
        }
    }
}
