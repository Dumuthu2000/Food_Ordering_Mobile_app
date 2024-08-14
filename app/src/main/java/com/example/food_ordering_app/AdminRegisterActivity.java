package com.example.food_ordering_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_ordering_app.dao.AdminDao;
import com.example.food_ordering_app.model.AdminModel;

public class AdminRegisterActivity extends AppCompatActivity {
    EditText adminUsernameTxt, adminEmailText, adminPasswordText;
    ImageView profileImage;
    AdminDao adminDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        adminUsernameTxt = findViewById(R.id.textAdminUsername);
        adminEmailText = findViewById(R.id.textAdminEmail);
        adminPasswordText = findViewById(R.id.textAdminPassword);
        profileImage = findViewById(R.id.profileImage);

        // Initialize adminDao here
        adminDao = new AdminDao(this);
    }

    public void handleRegisterAdminBtn(View view){
        String username = adminUsernameTxt.getText().toString();
        String email = adminEmailText.getText().toString();
        String password = adminPasswordText.getText().toString();
        String imageUrl = "url"; // Replace with actual image URL handling if needed

        AdminModel adminModel = new AdminModel(username, email, password, imageUrl);
        adminDao.registerAdmin(adminModel);

        Intent intent = new Intent(this, AdminLogin.class);
        startActivity(intent);
    }
}
