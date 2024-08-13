package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.AdminUserActivity;
import com.example.food_ordering_app.dao.AdminUserDao;
import com.example.food_ordering_app.model.UserModel;
import com.squareup.picasso.Picasso;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<UserModel> userList;
    private Context context;

    public UserAdapter(Context context, List<UserModel> userList) {
        this.context = context;
        this.userList = userList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_card, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        UserModel user = userList.get(position);

        holder.usernameTextView.setText(user.getUsername());
        holder.userIdTextView.setText("ID: " + user.getUserId());
        holder.emailTextView.setText(user.getEmail());

        Picasso.get()
                .load(user.getProfilePicture())
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.profileImageView);

        holder.deleteButton.setOnClickListener(v -> {
            AdminUserDao userDao = new AdminUserDao(context);
            userDao.deleteUser(user.getUserId());

            userList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, userList.size());

            Toast.makeText(context, "User deleted.", Toast.LENGTH_SHORT).show();

            if (context instanceof AdminUserActivity) {
                ((AdminUserActivity) context).refreshUserList();
            }
        });

        // Add click listeners for viewButton and orderHistoryButton if needed
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView usernameTextView, userIdTextView, emailTextView;
        ImageView profileImageView;
        Button deleteButton, orderHistoryButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.textViewUserName);
            userIdTextView = itemView.findViewById(R.id.textViewUserId);
            emailTextView = itemView.findViewById(R.id.textViewUserEmail);
            profileImageView = itemView.findViewById(R.id.imageViewProfilePicture);
            deleteButton = itemView.findViewById(R.id.buttonRemoveUser);
            orderHistoryButton = itemView.findViewById(R.id.buttonOrderHistory);
        }
    }
}