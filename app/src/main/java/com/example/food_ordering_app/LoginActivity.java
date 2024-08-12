package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_ordering_app.dao.UserDao;
import com.example.food_ordering_app.model.UserModel;

public class LoginActivity extends AppCompatActivity {
    EditText emailText, passwordText;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        emailText = findViewById(R.id.textEmail);
        passwordText = findViewById(R.id.textPassword);

        // Initialize userDao
        userDao = new UserDao(this);  // Pass context if needed
    }

    public void handleShiftToRegister(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void handleLoginBtn(View view){
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        UserModel user = userDao.loginUser(email, password);

        if (user != null) {
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);
        } else {
            // User login failed, no need to start MainActivity
            Toast.makeText(this, "Login failed. Please check your credentials.", Toast.LENGTH_SHORT).show();
        }
    }
}
