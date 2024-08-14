package com.example.food_ordering_app.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.ItemModel;
import com.example.food_ordering_app.model.OrderModel;
import java.util.ArrayList;
import java.util.List;

public class UserOrdersDao {
    private SQLiteDatabase database;
    private DbHandler dbHandler;

    public UserOrdersDao(Context context) {
        dbHandler = new DbHandler(context);
    }

    public void open() {
        database = dbHandler.getWritableDatabase();
    }

    public void close() {
        dbHandler.close();
    }

    public List<OrderModel> getOrdersByUserId(int userId) {
        List<OrderModel> orders = new ArrayList<>();
        String query = "SELECT * FROM " + DbHandler.TABLE_ORDERS + " WHERE " + DbHandler.COLUMN_USER_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            do {
                int orderIdIndex = cursor.getColumnIndex(DbHandler.COLUMN_ORDER_ID);
                int orderDateIndex = cursor.getColumnIndex(DbHandler.COLUMN_ORDER_DATE);
                int totalAmountIndex = cursor.getColumnIndex(DbHandler.COLUMN_TOTAL_AMOUNT);
                int orderStatusIndex = cursor.getColumnIndex(DbHandler.COLUMN_ORDER_STATUS);
                int deliveryAddressIndex = cursor.getColumnIndex(DbHandler.COLUMN_DELIVERY_ADDRESS);

                // Check if all required columns are present
                if (orderIdIndex != -1 && orderDateIndex != -1 && totalAmountIndex != -1 &&
                        orderStatusIndex != -1 && deliveryAddressIndex != -1) {
                    int orderId = cursor.getInt(orderIdIndex);
                    String orderDate = cursor.getString(orderDateIndex);
                    double totalAmount = cursor.getDouble(totalAmountIndex);
                    String orderStatus = cursor.getString(orderStatusIndex);
                    String deliveryAddress = cursor.getString(deliveryAddressIndex);

                    // Fetch items for this order
                    List<ItemModel> orderItems = getOrderItems(orderId);

                    OrderModel order = new OrderModel(orderId, userId, orderDate, totalAmount, orderStatus, deliveryAddress, orderItems);
                    orders.add(order);
                } else {
                    // Handle missing columns
                    throw new RuntimeException("One or more required columns are missing in the query result.");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    private List<ItemModel> getOrderItems(int orderId) {
        List<ItemModel> items = new ArrayList<>();
        String query = "SELECT * FROM " + DbHandler.TABLE_ORDER_ITEMS + " WHERE " + DbHandler.COLUMN_ORDER_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(orderId)});

        if (cursor.moveToFirst()) {
            do {
                int itemIdIndex = cursor.getColumnIndex(DbHandler.COLUMN_MENU_ITEM_ID);
                int quantityIndex = cursor.getColumnIndex(DbHandler.COLUMN_QUANTITY);
                int totalPriceIndex = cursor.getColumnIndex(DbHandler.COLUMN_TOTAL_ITEM_PRICE);

                // Check if all required columns are present
                if (itemIdIndex != -1 && quantityIndex != -1 && totalPriceIndex != -1) {
                    int itemId = cursor.getInt(itemIdIndex);
                    int quantity = cursor.getInt(quantityIndex);
                    double totalPrice = cursor.getDouble(totalPriceIndex);

                    // Fetch item details
                    ItemModel item = getItemById(itemId);
                    if (item != null) {
                        item.setQuantity(quantity);
                        items.add(item);
                    }
                } else {
                    // Handle missing columns
                    throw new RuntimeException("One or more required columns are missing in the query result.");
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }

    private ItemModel getItemById(int itemId) {
        ItemModel item = null;
        String query = "SELECT * FROM " + DbHandler.TABLE_ITEMS + " WHERE " + DbHandler.COLUMN_ITEM_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(itemId)});

        if (cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(DbHandler.COLUMN_ITEM_NAME);
            int descriptionIndex = cursor.getColumnIndex(DbHandler.COLUMN_ITEM_DESCRIPTION);
            int priceIndex = cursor.getColumnIndex(DbHandler.COLUMN_ITEM_PRICE);
            int availabilityIndex = cursor.getColumnIndex(DbHandler.COLUMN_ITEM_AVAILABILITY);
            int categoryIndex = cursor.getColumnIndex(DbHandler.COLUMN_ITEM_CATEGORY);
            int imageIndex = cursor.getColumnIndex(DbHandler.COLUMN_ITEM_IMAGE);

            // Check if all required columns are present
            if (nameIndex != -1 && descriptionIndex != -1 && priceIndex != -1 &&
                    availabilityIndex != -1 && categoryIndex != -1 && imageIndex != -1) {
                String name = cursor.getString(nameIndex);
                String description = cursor.getString(descriptionIndex);
                double price = cursor.getDouble(priceIndex);
                String category = cursor.getString(categoryIndex);
                String image = cursor.getString(imageIndex);

                item = new ItemModel(itemId, name, description, price, category, image);
            } else {
                // Handle missing columns
                throw new RuntimeException("One or more required columns are missing in the query result.");
            }
        }
        cursor.close();
        return item;
    }
}
