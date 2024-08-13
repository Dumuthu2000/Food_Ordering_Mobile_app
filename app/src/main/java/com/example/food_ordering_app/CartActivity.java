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
    private CartItemAdapter cartItemAdapter;
    private RecyclerView recyclerView;
    private TextView itemTotalView, discountView, deliveryChargesView, totalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        // Retrieve the cart items from the intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("myCartItems")) {
            myCartItems = (ArrayList<ItemModel>) intent.getSerializableExtra("myCartItems");

            if (myCartItems == null) {
                myCartItems = new ArrayList<>();
                Toast.makeText(this, "No items in cart", Toast.LENGTH_SHORT).show();
            }

            // Initialize RecyclerView
            recyclerView = findViewById(R.id.recyclerView);
            itemTotalView = findViewById(R.id.itemsTotalView);
            discountView = findViewById(R.id.discountView);
            deliveryChargesView = findViewById(R.id.deliveryChargesView);
            totalView = findViewById(R.id.totalView);

            cartItemAdapter = new CartItemAdapter(myCartItems, this::updateTotals);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(cartItemAdapter);

            updateTotals(myCartItems); // Initial update
        } else {
            Toast.makeText(this, "No cart data available", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotals(List<ItemModel> cartItems) {
        double itemTotal = 0.0;
        for (ItemModel item : cartItems) {
            itemTotal += item.getPrice() * item.getQuantity();
        }

        double discount = 0.00; // You can calculate this based on your logic
        double deliveryCharges = 300.00; // Example delivery charge
        double grandTotal = itemTotal - discount + deliveryCharges;

        itemTotalView.setText(String.format("Rs%.2f", itemTotal));
        discountView.setText(String.format("Rs%.2f", discount));
        deliveryChargesView.setText(String.format("Rs%.2f", deliveryCharges));
        totalView.setText(String.format("Rs%.2f", grandTotal));
    }
}
