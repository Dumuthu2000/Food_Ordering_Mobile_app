package com.example.food_ordering_app;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button manageUsersButton = findViewById(R.id.button_manage_users);
        Button manageItemsButton = findViewById(R.id.button_manage_items);
        Button manageOrdersButton = findViewById(R.id.button_manage_orders);

        // Navigate to AdminUserActivity
        manageUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AdminUserActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to AdminActivity (for managing items)
        manageItemsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

        // Navigate to MapAdminOrderActivity (for managing orders)
        manageOrdersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, MapAdminOrderActivity.class);
                startActivity(intent);
            }
        });
    }
}