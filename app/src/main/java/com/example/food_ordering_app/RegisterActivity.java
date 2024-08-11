package com.example.food_ordering_app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.food_ordering_app.dao.UserDao;
import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.UserModel;

public class RegisterActivity extends AppCompatActivity {
    EditText usernameTxt, emailText, passwordText;
    ImageView profileImage;
    UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        usernameTxt = findViewById(R.id.textUsername);
        emailText = findViewById(R.id.textEmail);
        passwordText = findViewById(R.id.textPassword);
        profileImage = findViewById(R.id.profileImage);


    }

    public void handleRegisterBtn(View view){
        String username = usernameTxt.getText().toString();
        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();
        String imageUrl = "url";

        UserModel userModel = new UserModel(username, email, password, imageUrl);
        userDao.registerUser(userModel);
    }
}