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

public class PendingAdapter extends RecyclerView.Adapter<PendingAdapter.VH> {

    private final List<VarietyModel> list;

    public PendingAdapter(List<VarietyModel> list) {
        this.list = (list != null) ? list : new ArrayList<>();
    }

    // ---------------- VIEW HOLDER ----------------
    static class VH extends RecyclerView.ViewHolder {
        TextView name;
        Button approve, reject;

        VH(View v) {
            super(v);
            name = v.findViewById(R.id.tvName);
            approve = v.findViewById(R.id.btnApprove);
            reject = v.findViewById(R.id.btnReject);
        }
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_pending, parent, false);
        return new VH(v);
    }

    @Override
    public void onBindViewHolder(@NonNull VH h, int position) {

        VarietyModel m = list.get(position);
        h.name.setText(m.name);

        h.approve.setOnClickListener(v -> {

            if (m.id == null || m.id.isEmpty()) {
                Toast.makeText(v.getContext(),
                        "Variety ID missing from backend",
                        Toast.LENGTH_LONG).show();
                return;
            }

            verifyVariety(v.getContext(), m.id, "approved", "");
        });

        h.reject.setOnClickListener(v -> {

            if (m.id == null || m.id.isEmpty()) {
                Toast.makeText(v.getContext(),
                        "Variety ID missing from backend",
                        Toast.LENGTH_LONG).show();
                return;
            }

            verifyVariety(v.getContext(), m.id, "rejected", "Invalid data");
        });
    }


    // ---------------- API CALL ----------------
    private void verifyVariety(Context context,
                               String varietyId,
                               String status,
                               String reason) {

        ApiService api = ApiClient.getClient().create(ApiService.class);
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
                            "Failed to update variety",
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
            if (id.equals(list.get(i).id)) {
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
