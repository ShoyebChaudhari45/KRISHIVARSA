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
import com.example.krishivarsa.models.VarietyModel;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.GenericResponse;
import com.example.krishivarsa.utils.SessionManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminVarietyAdapter
        extends RecyclerView.Adapter<AdminVarietyAdapter.VH> {

    private List<VarietyModel> list;
    private ApiService api;

    public AdminVarietyAdapter(List<VarietyModel> l) {
        this.list = (l != null) ? l : new ArrayList<>();
        this.api = ApiClient.getClient().create(ApiService.class);
    }

    // ---------------- VIEW HOLDER ----------------
    static class VH extends RecyclerView.ViewHolder {
        TextView tvName, tvStatus;
        Button btnApprove, btnReject;

        VH(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvStatus = v.findViewById(R.id.tvStatus);
            btnApprove = v.findViewById(R.id.btnApprove);
            btnReject = v.findViewById(R.id.btnReject);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_admin_variety, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {

        VarietyModel v = list.get(position);

        h.tvName.setText(v.name);
        h.tvStatus.setText(v.verificationStatus);

        h.btnApprove.setOnClickListener(btn ->
                verify(btn.getContext(), v.id, "approved", "")
        );

        h.btnReject.setOnClickListener(btn ->
                verify(btn.getContext(), v.id, "rejected", "Invalid data")
        );
    }

    // ---------------- VERIFY API ----------------
    private void verify(Context context,
                        String varietyId,
                        String status,
                        String reason) {

        SessionManager session = new SessionManager(context);

        Map<String, String> body = new HashMap<>();
        body.put("status", status);
        body.put("reason", reason);

        api.verifyVariety(
                "Bearer " + session.getToken(),
                varietyId,
                body
        ).enqueue(new Callback<GenericResponse>() {

            @Override
            public void onResponse(Call<GenericResponse> call,
                                   Response<GenericResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(context,
                            "Variety " + status,
                            Toast.LENGTH_SHORT).show();
                    removeItem(varietyId);
                } else {
                    Toast.makeText(context,
                            "Action failed",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(context,
                        t.getMessage(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    // ---------------- REMOVE ITEM ----------------
    private void removeItem(String id) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id.equals(id)) {
                list.remove(i);
                notifyItemRemoved(i);
                break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
