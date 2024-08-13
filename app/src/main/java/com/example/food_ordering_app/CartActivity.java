package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.CartItemAdapter;
import com.example.food_ordering_app.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private List<ItemModel> myCartItems;
    private RecyclerView recyclerView;
    private CartItemAdapter cartItemAdapter;
    private TextView itemsTotalView, discountView, deliveryChargesView, totalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerView);
        itemsTotalView = findViewById(R.id.itemsTotalView);
        discountView = findViewById(R.id.discountView);
        deliveryChargesView = findViewById(R.id.deliveryChargesView);
        totalView = findViewById(R.id.totalView);

        // Retrieve the cart items from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("myCartItems")) {
            myCartItems = (ArrayList<ItemModel>) intent.getSerializableExtra("myCartItems");

            if (myCartItems == null) {
                myCartItems = new ArrayList<>();
                Toast.makeText(this, "No items in cart", Toast.LENGTH_SHORT).show();
            }

            // Set up RecyclerView
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            cartItemAdapter = new CartItemAdapter(myCartItems);
            recyclerView.setAdapter(cartItemAdapter);

            // Calculate and display totals
            updateTotals();
        } else {
            Toast.makeText(this, "No cart data available", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotals() {
        double itemTotal = 0.0;
        for (ItemModel item : myCartItems) {
            itemTotal += item.getPrice();
        }

        double discount = 0.0; // You can calculate or retrieve this from elsewhere
        double deliveryCharges = 300.00; // Fixed or calculated value
        double grandTotal = itemTotal - discount + deliveryCharges;

        itemsTotalView.setText(String.format("$%.2f", itemTotal));
        discountView.setText(String.format("$%.2f", discount));
        deliveryChargesView.setText(String.format("$%.2f", deliveryCharges));
        totalView.setText(String.format("$%.2f", grandTotal));
    }
}
