package com.example.food_ordering_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HeaderActivity extends AppCompatActivity {

    private TextView cartCountView;
    private static final String PREFS_NAME = "CartPrefs";
    private static final String KEY_CART_COUNT = "cartCount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_header); // Ensure this matches your layout name

        cartCountView = findViewById(R.id.cartCount);

        // Load cart count from SharedPreferences
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        int cartCount = prefs.getInt(KEY_CART_COUNT, 0);
        cartCountView.setText(String.valueOf(cartCount));
    }

    // Handle the click on the Home icon
    public void handleHomeIcon(View view) {
        Intent intent = new Intent(HeaderActivity.this, HomeActivity.class);
        startActivity(intent);
    }

    // Method to update cart count
//    public void updateCartCount(int count) {
//        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt(KEY_CART_COUNT, count);
//        editor.apply();
//        cartCountView.setText(String.valueOf(count));
//    }
}
