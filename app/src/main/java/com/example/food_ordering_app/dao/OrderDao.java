package com.example.food_ordering_app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.ItemModel;
import com.example.food_ordering_app.model.OrderModel;

public class OrderDao {
    private DbHandler dbHandler;

    public OrderDao(Context context) {
        this.dbHandler = new DbHandler(context);
    }

    public long createOrder(OrderModel order) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        long orderId = -1;

        try {
            db.beginTransaction();

            ContentValues orderValues = new ContentValues();
            orderValues.put(DbHandler.COLUMN_USER_ID, order.getUserId());
            orderValues.put(DbHandler.COLUMN_ORDER_DATE, order.getOrderDate());
            orderValues.put(DbHandler.COLUMN_TOTAL_AMOUNT, order.getTotalAmount());
            orderValues.put(DbHandler.COLUMN_ORDER_STATUS, order.getOrderStatus());
            orderValues.put(DbHandler.COLUMN_DELIVERY_ADDRESS, order.getDeliveryAddress());

            orderId = db.insert(DbHandler.TABLE_ORDERS, null, orderValues);

            if (orderId != -1) {
                for (ItemModel item : order.getOrderItems()) {
                    ContentValues itemValues = new ContentValues();
                    itemValues.put(DbHandler.COLUMN_ORDER_ID, orderId);
                    itemValues.put(DbHandler.COLUMN_MENU_ITEM_ID, item.getItemID());
                    itemValues.put(DbHandler.COLUMN_QUANTITY, item.getQuantity());
                    itemValues.put(DbHandler.COLUMN_TOTAL_ITEM_PRICE, item.getPrice() * item.getQuantity());

                    long orderItemId = db.insert(DbHandler.TABLE_ORDER_ITEMS, null, itemValues);
                    if (orderItemId == -1) {
                        throw new SQLException("Failed to insert order item");
                    }
                }
            }

            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            orderId = -1;
        } finally {
            db.endTransaction();
        }

        return orderId;
    }
}