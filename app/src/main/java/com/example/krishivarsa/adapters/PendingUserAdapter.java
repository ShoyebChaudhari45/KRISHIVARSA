package com.example.krishivarsa.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.krishivarsa.R;
import com.example.krishivarsa.models.PendingUser;
import com.example.krishivarsa.network.ApiClient;
import com.example.krishivarsa.network.ApiService;
import com.example.krishivarsa.network.responses.GenericResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingUserAdapter
        extends RecyclerView.Adapter<PendingUserAdapter.ViewHolder> {

    private final Context context;
    private final List<PendingUser> list;
    private final ApiService apiService;
    private final String token;

    public PendingUserAdapter(Context context,
                              List<PendingUser> list,
                              String token) {
        this.context = context;
        this.list = list;
        this.token = token;
        this.apiService = ApiClient.getClient().create(ApiService.class);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.item_pending_user, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        PendingUser user = list.get(position);

        holder.tvName.setText(user.name);
        holder.tvEmail.setText(user.email);
        holder.tvRole.setText("Role: " + user.role.toUpperCase());

        if (user.location != null) {
            holder.tvLocation.setText(
                    user.location.village + ", " +
                            user.location.district + ", " +
                            user.location.state
            );
        } else {
            holder.tvLocation.setText("N/A");
        }

        holder.btnApprove.setOnClickListener(v ->
                updateStatus(user._id, "approve")
        );

        holder.btnReject.setOnClickListener(v ->
                updateStatus(user._id, "reject")
        );
    }

    private void updateStatus(String userId, String action) {

        Map<String, String> body = new HashMap<>();
        body.put("action", action);

        apiService.approveOrRejectUser(
                "Bearer " + token,
                userId,
                body
        ).enqueue(new Callback<GenericResponse>() {

            @Override
            public void onResponse(Call<GenericResponse> call,
                                   Response<GenericResponse> response) {

                if (response.isSuccessful()) {

                    int index = -1;
                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i)._id.equals(userId)) {
                            index = i;
                            break;
                        }
                    }

                    if (index != -1) {
                        list.remove(index);
                        notifyItemRemoved(index);
                    }

                    Toast.makeText(
                            context,
                            "User " + action + "d successfully",
                            Toast.LENGTH_SHORT
                    ).show();
                } else {
                    Toast.makeText(
                            context,
                            "Action failed",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }

            @Override
            public void onFailure(Call<GenericResponse> call, Throwable t) {
                Toast.makeText(
                        context,
                        "Error: " + t.getMessage(),
                        Toast.LENGTH_LONG
                ).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvEmail, tvRole, tvLocation;
        Button btnApprove, btnReject;

        ViewHolder(View v) {
            super(v);
            tvName = v.findViewById(R.id.tvName);
            tvEmail = v.findViewById(R.id.tvEmail);
            tvRole = v.findViewById(R.id.tvRole);
            tvLocation = v.findViewById(R.id.tvLocation);
            btnApprove = v.findViewById(R.id.btnApprove);
            btnReject = v.findViewById(R.id.btnReject);
        }
    }
}
