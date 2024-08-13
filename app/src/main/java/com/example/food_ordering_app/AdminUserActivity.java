package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.UserAdapter;
import com.example.food_ordering_app.dao.AdminUserDao;
import com.example.food_ordering_app.dao.UserDao;
import com.example.food_ordering_app.model.SpacesItemDecoration;
import com.example.food_ordering_app.model.UserModel;

import java.util.List;

public class AdminUserActivity extends AppCompatActivity {

    private RecyclerView recyclerViewUsers;
    private UserAdapter userAdapter;
    private AdminUserDao userDao;
    private ImageView iconManageUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_user);

        userDao = new AdminUserDao(this);

        recyclerViewUsers = findViewById(R.id.recyclerViewUsers);
        recyclerViewUsers.setLayoutManager(new LinearLayoutManager(this));

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerViewUsers.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        iconManageUsers = findViewById(R.id.iconManageUsers);
        iconManageUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navigate back to the DashBoardActivity when the back icon is clicked
                Intent intent = new Intent(AdminUserActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish(); // Optional: Call finish if you want to close the current activity
            }
        });

        loadUsers();
    }

    private void loadUsers() {
        List<UserModel> userList = userDao.getAllUsers();
        userAdapter = new UserAdapter(this, userList);
        recyclerViewUsers.setAdapter(userAdapter);
    }

    public void refreshUserList() {
        List<UserModel> updatedUserList = userDao.getAllUsers();
        userAdapter = new UserAdapter(this, updatedUserList);
        recyclerViewUsers.setAdapter(userAdapter);
        userAdapter.notifyDataSetChanged();
    }
}