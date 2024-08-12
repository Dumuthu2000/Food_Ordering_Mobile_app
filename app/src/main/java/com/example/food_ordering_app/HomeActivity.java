package com.example.food_ordering_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.ItemCardAdapter;
import com.example.food_ordering_app.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemCardAdapter itemCardAdapter;
    private List<ItemModel> itemList;
    private TextView cartCountView;
    private int cartCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        cartCountView = findViewById(R.id.cartCount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize item list and add some data
        itemList = new ArrayList<>();
        itemList.add(new ItemModel("Pizza", "Delicious pizza with extra cheese", 12.99, true, "Main Course", "pizza_image_url"));
        itemList.add(new ItemModel("Burger", "Juicy beef burger with lettuce and tomato", 9.99, true, "Main Course", "burger_image_url"));
        itemList.add(new ItemModel("Pasta", "Creamy Alfredo pasta with mushrooms", 14.99, true, "Main Course", "pasta_image_url"));

        // Set up the adapter with the listener for adding to the cart
        itemCardAdapter = new ItemCardAdapter(itemList, this::updateCartCount);
        recyclerView.setAdapter(itemCardAdapter);
    }

    private void updateCartCount() {
        cartCount++;
        cartCountView.setText(String.valueOf(cartCount));
    }
}
