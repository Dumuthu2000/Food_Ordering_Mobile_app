package com.example.food_ordering_app;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize item list and add some data
        itemList = new ArrayList<>();
        itemList.add(new ItemModel("Pizza", "Delicious pizza with extra cheese", 12.99, true, "Main Course", "pizza_image_url"));
        itemList.add(new ItemModel("Burger", "Juicy beef burger with lettuce and tomato", 9.99, true, "Main Course", "burger_image_url"));
        itemList.add(new ItemModel("Pasta", "Creamy Alfredo pasta with mushrooms", 14.99, true, "Main Course", "pasta_image_url"));
        itemList.add(new ItemModel("Pasta", "Creamy Alfredo pasta with mushrooms", 14.99, true, "Main Course", "pasta_image_url"));
        itemList.add(new ItemModel("Pasta", "Creamy Alfredo pasta with mushrooms", 14.99, true, "Main Course", "pasta_image_url"));
        itemList.add(new ItemModel("Pasta", "Creamy Alfredo pasta with mushrooms", 14.99, false, "Main Course", "pasta_image_url"));
        itemList.add(new ItemModel("Pasta", "Creamy Alfredo pasta with mushrooms", 14.99, false, "Main Course", "pasta_image_url"));

        // Set up the adapter
        itemCardAdapter = new ItemCardAdapter(itemList);
        recyclerView.setAdapter(itemCardAdapter);

    }
}