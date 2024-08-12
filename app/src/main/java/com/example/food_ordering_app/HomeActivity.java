package com.example.food_ordering_app;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.ItemAdapter;
import com.example.food_ordering_app.adapter.ItemCardAdapter;
import com.example.food_ordering_app.dao.ItemDao;
import com.example.food_ordering_app.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ItemAdapter itemCardAdapter;
    private List<ItemModel> itemList;
    private TextView cartCountView;
    private int cartCount = 0;
    private ItemDao itemDao; // Declare ItemDao

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        cartCountView = findViewById(R.id.cartCount);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize ItemDao
        itemDao = new ItemDao(this);

        // Fetch items from the database and update RecyclerView
        loadItemsFromDatabase();

        // Initialize the adapter and set it to the RecyclerView
        ItemAdapter ItemAdapter = new ItemAdapter(itemList, this::updateCartCount);
        recyclerView.setAdapter(itemCardAdapter);
    }

    private void updateCartCount(ItemModel itemModel) {
        cartCount++;
        cartCountView.setText(String.valueOf(cartCount));
    }

    private void loadItemsFromDatabase() {
        // Retrieve items from the database
        itemList = itemDao.getAllItems();

        // Check if itemList is null and initialize if necessary
        if (itemList == null) {
            itemList = new ArrayList<>();
        }
    }
}
