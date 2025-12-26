package com.example.krishivarsa.activities.farmer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.network.responses.MyVarietiesResponse;

import java.util.List;

public class MyVarietiesAdapter
        extends RecyclerView.Adapter<MyVarietiesAdapter.ViewHolder> {

    List<MyVarietiesResponse.Variety> list;

    public MyVarietiesAdapter(List<MyVarietiesResponse.Variety> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_variety, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(list.get(position).name);
        holder.tvStatus.setText(list.get(position).verificationStatus);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus;
        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvStatus = v.findViewById(R.id.tvStatus);
        }
    }
}
