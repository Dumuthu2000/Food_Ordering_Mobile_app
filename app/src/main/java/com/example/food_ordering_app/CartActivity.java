package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.R;
import com.example.food_ordering_app.model.ItemModel;
import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    private List<ItemModel> myCartItems;

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

            // Set up RecyclerView or other UI elements to display the cart items
            // For example, initialize RecyclerView, Adapter, etc.
        } else {
            Toast.makeText(this, "No cart data available", Toast.LENGTH_SHORT).show();
        }
    }
}
