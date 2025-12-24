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
import com.example.krishivarsa.models.User;

import java.util.List;

public class UserApprovalAdapter extends RecyclerView.Adapter<UserApprovalAdapter.ViewHolder> {

    private Context context;
    private List<User> userList;

    public UserApprovalAdapter(Context context, List<User> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_user_approval, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        User user = userList.get(position);

        holder.tvName.setText(user.getName());
        holder.tvRole.setText("Role: " + user.getRole());
        holder.tvLocation.setText("Location: " + user.getLocation());

        holder.btnApprove.setOnClickListener(v -> {
            // POST /api/admin/approve-user
            Toast.makeText(context, "Approved " + user.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.btnReject.setOnClickListener(v -> {
            // POST /api/admin/reject-user
            Toast.makeText(context, "Rejected " + user.getName(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvRole, tvLocation;
        Button btnApprove, btnReject;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvRole = itemView.findViewById(R.id.tvRole);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            btnApprove = itemView.findViewById(R.id.btnApprove);
            btnReject = itemView.findViewById(R.id.btnReject);
        }
    }
}
