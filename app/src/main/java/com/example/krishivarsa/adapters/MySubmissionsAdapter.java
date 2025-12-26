package com.example.krishivarsa.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.models.VarietyModel;

import java.util.ArrayList;
import java.util.List;
public class MySubmissionsAdapter
        extends RecyclerView.Adapter<MySubmissionsAdapter.VH> {

    private List<VarietyModel> list;

    public MySubmissionsAdapter(List<VarietyModel> list) {
        this.list = (list != null) ? list : new ArrayList<>();
    }

    class VH extends RecyclerView.ViewHolder {
        TextView name, badge, reason;
        VH(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            badge = v.findViewById(R.id.tvStatus);
            reason = v.findViewById(R.id.tvReason);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();   // 🔒 NOW SAFE
    }

    @Override
    public VH onCreateViewHolder(ViewGroup p, int v) {
        return new VH(LayoutInflater.from(p.getContext())
                .inflate(R.layout.row_my_submission, p, false));
    }

    @Override
    public void onBindViewHolder(VH h, int i) {
        VarietyModel m = list.get(i);
        h.name.setText(m.name);

        switch (m.verificationStatus) {
            case "pending":
                h.badge.setText("Pending");
                h.badge.setBackgroundColor(Color.YELLOW);
                break;

            case "approved":
                h.badge.setText("Approved");
                h.badge.setBackgroundColor(Color.GREEN);
                break;

            case "rejected":
                h.badge.setText("Rejected");
                h.badge.setBackgroundColor(Color.RED);
//                h.reason.setText(m.rejectionReason);
                h.reason.setVisibility(View.VISIBLE);
                break;
        }
    }
}
