package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.ItemAdapter;
import com.example.food_ordering_app.dao.UserItemDao;
import com.example.food_ordering_app.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity implements ItemAdapter.OnAddToCartListener {
    private List<ItemModel> myCart = new ArrayList<>(); // Separate list for selected items
    private RecyclerView recyclerView;
    private ItemAdapter itemAdapter;
    private List<ItemModel> itemList;
    private TextView cartCountView;
    private int cartCount = 0;
    private UserItemDao itemDao;
    private Button cartButton; // Reference to the cart button

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.recyclerView);
        cartCountView = findViewById(R.id.cartCount);
        cartButton = findViewById(R.id.cartButton); // Initialize the cart button
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        itemDao = new UserItemDao(this);

        // Fetch items from database
        itemList = itemDao.getAllItems();

        // Initialize the adapter and set it to the RecyclerView
        itemAdapter = new ItemAdapter(itemList, this);
        recyclerView.setAdapter(itemAdapter);

        // Set up the OnClickListener for the cart button
        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCartIcons(v);
            }
        });
    }

    @Override
    public void onAddToCart(ItemModel itemModel) {
        // Add the item to the myCart list
        myCart.add(itemModel);

        // Optionally, update the cart count view
        cartCount = myCart.size();
        cartCountView.setText(String.valueOf(cartCount));
    }

    public void handleCartIcons(View v) {
        Intent intent = new Intent(this, CartActivity.class);
        intent.putExtra("myCartItems", new ArrayList<>(myCart));
        startActivity(intent);
    }

    public void handleUserProfile(View view) {
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }
}
