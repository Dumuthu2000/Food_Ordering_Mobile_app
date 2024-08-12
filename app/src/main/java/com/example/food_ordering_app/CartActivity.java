package com.example.food_ordering_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.CartAdapter;
import com.example.food_ordering_app.model.CartModel;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CartAdapter cartAdapter;
    private List<CartModel> cartItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the cart item list and add some sample data
        cartItemList = new ArrayList<>();
        cartItemList.add(new CartModel("Pizza", 12.99, 2, 25.98));
        cartItemList.add(new CartModel("Burger", 8.99, 1, 8.99));
        cartItemList.add(new CartModel("Burger", 8.99, 1, 8.99));
        cartItemList.add(new CartModel("Burger", 8.99, 1, 8.99));
        cartItemList.add(new CartModel("Burger", 8.99, 1, 8.99));
        cartItemList.add(new CartModel("Burger", 8.99, 1, 8.99));

        // Set up the adapter
        cartAdapter = new CartAdapter(cartItemList);
        recyclerView.setAdapter(cartAdapter);

        // Update the total amount
        updateTotalAmount();
    }

    private void updateTotalAmount() {
        double total = 0;
        for (CartModel item : cartItemList) {
            total += item.getTotalAmount();
        }

        TextView totalView = findViewById(R.id.totalView);
        totalView.setText(String.format("$%.2f", total));
    }
}
