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

public class CropAdapter extends RecyclerView.Adapter<CropAdapter.VH> {

    public interface OnCropClickListener {
        void onCropClick(GetCropsResponse.Crop crop);
    }

    private List<GetCropsResponse.Crop> list;
    private OnCropClickListener listener;

    public CropAdapter(List<GetCropsResponse.Crop> list,
                       OnCropClickListener listener) {
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_crop, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {

        GetCropsResponse.Crop crop = list.get(position);

        h.tvCropName.setText(crop.name);

        h.itemView.setOnClickListener(v ->
                listener.onCropClick(crop)
        );
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    static class VH extends RecyclerView.ViewHolder {

        TextView tvCropName;

        VH(View v) {
            super(v);
            tvCropName = v.findViewById(R.id.tvCropName);
        }
    }
}
