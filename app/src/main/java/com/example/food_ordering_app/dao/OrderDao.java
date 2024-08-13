package com.example.food_ordering_app.dao;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.ItemModel;
import com.example.food_ordering_app.model.OrderModel;

public class OrderDao {
    private final Context context;
    private SQLiteDatabase database;
    private DbHandler dbHelper;

    public OrderDao(Context context) {
        this.context = context;
        dbHelper = new DbHandler(context);
        database = dbHelper.getWritableDatabase();
    }

    public void createOrder(OrderModel order){
        try{
            ContentValues values = new ContentValues();
            values.put("user_id", order.getUserId());
            values.put("order_date", order.getOrderDate());
            values.put("total_amount", order.getTotalAmount());
            values.put("order_status", order.getOrderStatus());
            values.put("delivery_address", order.getDeliveryAddress());

            long result = database.insert(DbHandler.TABLE_ORDERS, null, values);

            if (result == -1) {
                // Insert operation failed
                Log.e(TAG, "Failed to create order: " + order.getOrderId());
                Toast.makeText(dbHelper.getContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                // Insert operation succeeded
                Toast.makeText(dbHelper.getContext(), "Order create successfully.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            // Handle SQL exceptions
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(dbHelper.getContext(), "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // Handle any other exceptions
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(dbHelper.getContext(), "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        } finally {
            // Close the database
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }
}
