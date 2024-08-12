package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class HeaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header); // Ensure this matches your layout name
    }

    // Handle the click on the Home icon
    public void handleHomeIcon(View view) {
        Intent intent = new Intent(HeaderActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    // Handle the click on the Cart icon
    public void handleCartIcon(View view) {
        Intent intent = new Intent(HeaderActivity.this, CartActivity.class);
        startActivity(intent);
    }
}
