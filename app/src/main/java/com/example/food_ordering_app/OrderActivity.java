package com.example.food_ordering_app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class OrderActivity extends AppCompatActivity {
    private RadioGroup radioGroupPaymentMethod;
    private LinearLayout layoutCardDetails;
    private Spinner spinnerCardType;
    private TextView heading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        radioGroupPaymentMethod = findViewById(R.id.radioGroupPaymentMethod);
        layoutCardDetails = findViewById(R.id.layoutCardDetails);
        spinnerCardType = findViewById(R.id.spinnerCardType);
        heading = findViewById(R.id.heading);  // Make sure this ID matches your XML

        setupPaymentMethodSelection();
        setupCardTypeSpinner();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1);
        String userName = sharedPreferences.getString("userName", "");
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            heading.setText("Welcome, " + userName + "!");
        } else {
            heading.setText("Your Order");  // Default text if not logged in
        }

        // For debugging:
        System.out.println("User ID: " + userId);
        System.out.println("User Name: " + userName);
        System.out.println("Is Logged In: " + isLoggedIn);
    }

    private void setupPaymentMethodSelection() {
        radioGroupPaymentMethod.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonCard) {
                layoutCardDetails.setVisibility(View.VISIBLE);
            } else {
                layoutCardDetails.setVisibility(View.GONE);
            }
        });
    }

    private void setupCardTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.card_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCardType.setAdapter(adapter);
    }
}