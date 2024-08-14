package com.example.food_ordering_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_ordering_app.dao.AdminDao;
import com.example.food_ordering_app.model.AdminModel;

public class AdminLogin extends AppCompatActivity {
    EditText adminEmailText, adminPasswordText;
    AdminDao adminDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adminEmailText = findViewById(R.id.textAdminEmail);
        adminPasswordText = findViewById(R.id.textAdminPassword);

        // Initialize adminDao
        adminDao = new AdminDao(this);  // Pass context if needed
    }

    public void handleShiftToRegisterAdmin(View view){
        Intent intent = new Intent(this, AdminRegisterActivity.class);
        startActivity(intent);
    }

    public void handleAdminLoginBtn(View view){
//        String email = adminEmailText.getText().toString().trim();
//        String password = adminPasswordText.getText().toString().trim();
//
//        AdminModel admin = adminDao.loginAdmin(email, password);

//        if (admin != null) {
//            adminDao.saveAdminToSharedPreferences(admin);
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
//        } else {
//            // Admin login failed, no need to start DashboardActivity
//            Toast.makeText(this, "Admin login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
//        }
    }
}
