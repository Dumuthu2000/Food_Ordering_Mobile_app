package com.example.food_ordering_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.dao.OrderDao;
import com.example.food_ordering_app.model.ItemModel;
import com.example.food_ordering_app.model.OrderModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class OrderActivity extends AppCompatActivity {
    private RadioGroup radioGroupPaymentMethod;
    private LinearLayout layoutCardDetails;
    private Spinner spinnerCardType;
    private TextView heading;
    private TextView orderSummary;
    private Button confirmOrderButton;
    private EditText editTextDeliveryAddress;
    private int userId;
    private double totalAmount;
    private ArrayList<Integer> itemIds;
    private ArrayList<String> itemNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        radioGroupPaymentMethod = findViewById(R.id.radioGroupPaymentMethod);
        layoutCardDetails = findViewById(R.id.layoutCardDetails);
        spinnerCardType = findViewById(R.id.spinnerCardType);
        heading = findViewById(R.id.heading);
        orderSummary = findViewById(R.id.textViewTotalPrice);
        confirmOrderButton = findViewById(R.id.buttonConfirmOrder);
        editTextDeliveryAddress = findViewById(R.id.editTextDeliveryAddress);

        setupPaymentMethodSelection();
        setupCardTypeSpinner();

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userId = sharedPreferences.getInt("userId", -1);
        String userName = sharedPreferences.getString("userName", "");
        boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            heading.setText("Welcome, " + userName + "!");
        } else {
            heading.setText("Your Order");
        }

        Intent intent = getIntent();
        if (intent != null) {
            itemNames = intent.getStringArrayListExtra("itemNames");
            itemIds = intent.getIntegerArrayListExtra("itemIds");
            totalAmount = intent.getDoubleExtra("totalAmount", 0.0);

            StringBuilder summary = new StringBuilder("Order Summary:\n\n");
            if (itemNames != null && itemIds != null) {
                for (int i = 0; i < itemNames.size(); i++) {
                    summary.append(itemNames.get(i)).append(" (ID: ").append(itemIds.get(i)).append(")\n");
                }
            }
            summary.append("\nTotal Amount: Rs").append(String.format("%.2f", totalAmount));

            orderSummary.setText(summary.toString());
        }

        confirmOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmOrder();
            }
        });

        // For debugging:
        System.out.println("User ID: " + userId);
        System.out.println("User Name: " + userName);
        System.out.println("Is Logged In: " + isLoggedIn);
    }

    private void setupPaymentMethodSelection() {
        radioGroupPaymentMethod.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioButtonCard) {
                    layoutCardDetails.setVisibility(View.VISIBLE);
                } else {
                    layoutCardDetails.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setupCardTypeSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.card_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCardType.setAdapter(adapter);
    }

    private void confirmOrder() {
        if (!validateOrder()) {
            return;
        }

        final String deliveryAddress = editTextDeliveryAddress.getText().toString().trim();
        if (deliveryAddress.isEmpty()) {
            Toast.makeText(this, "Please enter a delivery address", Toast.LENGTH_LONG).show();
            return;
        }

        OrderModel order = new OrderModel();
        order.setUserId(userId);
        order.setOrderDate(getCurrentDate());
        order.setTotalAmount(totalAmount);
        order.setOrderStatus("Pending");
        order.setDeliveryAddress(deliveryAddress);

        List<ItemModel> orderItems = new ArrayList<>();
        for (int i = 0; i < itemIds.size(); i++) {
            ItemModel item = new ItemModel();
            item.setItemID(itemIds.get(i));
            item.setName(itemNames.get(i));
            orderItems.add(item);
        }
        order.setOrderItems(orderItems);

        OrderDao orderDao = new OrderDao(this);

        // Run database operation on a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                final boolean success = orderDao.createOrder(order);

                // Update UI on the main thread
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (success) {
                            Toast.makeText(OrderActivity.this, "Order placed successfully!", Toast.LENGTH_LONG).show();
                            editTextDeliveryAddress.setText(""); // Clear the address field
                            navigateToConfirmationScreen();
                        } else {
                            Toast.makeText(OrderActivity.this, "Failed to place order. Please try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        }).start();
    }

    private boolean validateOrder() {
        if (userId == -1) {
            Toast.makeText(this, "Please log in to place an order", Toast.LENGTH_LONG).show();
            return false;
        }
        if (itemIds == null || itemIds.isEmpty()) {
            Toast.makeText(this, "Your order is empty", Toast.LENGTH_LONG).show();
            return false;
        }
        // Add more validation as needed
        return true;
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return sdf.format(new Date());
    }

    private void navigateToConfirmationScreen() {
        // Implement navigation to a confirmation screen
        // For example:
        // Intent intent = new Intent(this, ConfirmationActivity.class);
        // startActivity(intent);
        // finish();
    }
}