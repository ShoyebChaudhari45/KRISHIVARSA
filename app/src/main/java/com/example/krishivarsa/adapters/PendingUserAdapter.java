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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingUserAdapter extends RecyclerView.Adapter<PendingUserAdapter.ViewHolder> {

    Context context;
    List<PendingUser> list;
    ApiService apiService;
    String token;

    public PendingUserAdapter(Context context, List<PendingUser> list, String token) {
        this.context = context;
        this.list = list;
        this.token = token;
        apiService = ApiClient.getClient().create(ApiService.class);
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

        holder.tvName.setText(user.profileId.fullName);
        holder.tvEmail.setText(user.email);
        holder.tvRole.setText("Role: " + user.role.toUpperCase());
        holder.tvLocation.setText(
                user.profileId.address.village + ", " +
                        user.profileId.address.district + ", " +
                        user.profileId.address.state
        );

        holder.btnApprove.setOnClickListener(v ->
                updateStatus(user._id, "approve")
        );

        holder.btnReject.setOnClickListener(v ->
                updateStatus(user._id, "reject")
        );
    }

    // 🔥 SAFE METHOD (NO POSITION USED)
    private void updateStatus(String userId, String action) {

        Map<String, String> body = new HashMap<>();
        body.put("action", action);

        apiService.approveOrRejectUser("Bearer " + token, userId, body)
                .enqueue(new Callback<Void>() {

                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {

                        if (response.isSuccessful()) {

                            int indexToRemove = -1;

                            for (int i = 0; i < list.size(); i++) {
                                if (list.get(i)._id.equals(userId)) {
                                    indexToRemove = i;
                                    break;
                                }
                            }

                            if (indexToRemove != -1) {
                                list.remove(indexToRemove);
                                notifyItemRemoved(indexToRemove);
                            }

                            Toast.makeText(
                                    context,
                                    "User " + action + "d",
                                    Toast.LENGTH_SHORT
                            ).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
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
