package com.example.food_ordering_app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_ordering_app.adapter.UserOrdersAdapter;
import com.example.food_ordering_app.dao.UserDao;
import com.example.food_ordering_app.dao.UserOrdersDao;
import com.example.food_ordering_app.model.OrderModel;
import com.example.food_ordering_app.model.UserModel;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    private TextView nameTextView;
    private TextView emailTextView;
    private TextView passwordTextView;
    private RecyclerView ordersRecyclerView;
    private UserOrdersAdapter orderAdapter;
    private UserOrdersDao userOrdersDao;
    private UserDao userDao;
    private static final String TAG = "UserProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        // Initialize UI elements
        nameTextView = findViewById(R.id.textView6);
        emailTextView = findViewById(R.id.emailText);
        passwordTextView = findViewById(R.id.passwordText);
        ordersRecyclerView = findViewById(R.id.orderRecycler);
        ordersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Retrieve user ID from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userId", -1); // Default value is -1 if not found

        if (userId == -1) {
            // Handle the case where user ID is not found
            // For example, show an error message or redirect to login
            return;
        }

        // Initialize DAOs
        userDao = new UserDao(this);
        userOrdersDao = new UserOrdersDao(this);

        // Fetch and display user details
        userDao.open();
        UserModel user = userDao.getUserById(userId);
        userDao.close();
        if (user != null) {
            nameTextView.setText(user.getUsername());
            emailTextView.setText(user.getEmail());
            passwordTextView.setText(user.getPassword()); // Avoid showing passwords in plaintext for security
        }

        // Fetch and display user orders
        userOrdersDao.open();
        List<OrderModel> userOrders = userOrdersDao.getOrdersByUserId(userId);
        userOrdersDao.close();

        // Log each order's details to Logcat for debugging purposes
        for (OrderModel order : userOrders) {
            Log.d(TAG, "Order ID: " + order.getOrderId());
            Log.d(TAG, "Order Date: " + order.getOrderDate());
            Log.d(TAG, "Order Total: " + order.getTotalAmount());
        }

        // Set up the adapter with the fetched orders
        orderAdapter = new UserOrdersAdapter(userOrders, this);
        ordersRecyclerView.setAdapter(orderAdapter);
    }
}
