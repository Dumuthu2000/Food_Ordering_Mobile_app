package com.example.food_ordering_app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    private static final String TAG = "ItemDao";
    private final Context context;
    private final DbHandler handler;
    private SQLiteDatabase database;

    public ItemDao(Context context) {
        this.context = context;
        handler = new DbHandler(context);
        openDatabase();
    }

    private void openDatabase() {
        if (database == null || !database.isOpen()) {
            database = handler.getWritableDatabase();
            Log.d(TAG, "Database opened");
        }
    }

    private void closeDatabase() {
        if (database != null && database.isOpen()) {
            database.close();
            Log.d(TAG, "Database closed");
        }
    }

    public void addItem(ItemModel itemModel) {
        openDatabase();  // Ensure database is open
        try {
            ContentValues values = new ContentValues();
            values.put(handler.COLUMN_ITEM_NAME, itemModel.getName().trim());
            values.put(handler.COLUMN_ITEM_DESCRIPTION, itemModel.getDescription().trim());
            values.put(handler.COLUMN_ITEM_PRICE, itemModel.getPrice());
            values.put(handler.COLUMN_ITEM_AVAILABILITY, itemModel.isAvailability() ? 1 : 0);
            values.put(handler.COLUMN_ITEM_CATEGORY, itemModel.getCategory());
            values.put(handler.COLUMN_ITEM_IMAGE, itemModel.getImage());

            long result = database.insert(DbHandler.TABLE_ITEMS, null, values);
            if (result == -1) {
                Log.e(TAG, "Failed to Add Item: " + itemModel.getName());
                Toast.makeText(context, "Item not added. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Item added successfully.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(context, "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(context, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        }
    }

    public List<ItemModel> getAllItems() {
        List<ItemModel> itemList = new ArrayList<>();
        openDatabase();  // Ensure database is open
        Cursor cursor = null;
        try {
            cursor = database.query(DbHandler.TABLE_ITEMS, null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow(handler.COLUMN_ITEM_ID));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(handler.COLUMN_ITEM_NAME));
                    String description = cursor.getString(cursor.getColumnIndexOrThrow(handler.COLUMN_ITEM_DESCRIPTION));
                    double price = cursor.getDouble(cursor.getColumnIndexOrThrow(handler.COLUMN_ITEM_PRICE));
                    boolean availability = cursor.getInt(cursor.getColumnIndexOrThrow(handler.COLUMN_ITEM_AVAILABILITY)) > 0;
                    String category = cursor.getString(cursor.getColumnIndexOrThrow(handler.COLUMN_ITEM_CATEGORY));
                    String image = cursor.getString(cursor.getColumnIndexOrThrow(handler.COLUMN_ITEM_IMAGE));

                    ItemModel item = new ItemModel(name, description, price, availability, category, image);
                    item.setItemID(id);
                    itemList.add(item);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return itemList;
    }

    public void deleteItem(int itemId){
        try {
            // Define the selection criteria
            String selection = handler.COLUMN_ITEM_ID + " = ?";
            String[] selectionArgs = { String.valueOf(itemId) };

            // Perform the delete operation
            int rowsAffected = database.delete(DbHandler.TABLE_ITEMS, selection, selectionArgs);
            if (rowsAffected > 0) {
                // Deletion successful
                Toast.makeText(context, "Item deleted successfully.", Toast.LENGTH_SHORT).show();
            } else {
                // No rows affected
                Log.e(TAG, "Failed to Delete Item: ID = " + itemId);
                Toast.makeText(context, "Item not found. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(context, "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(context, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    public void updateItem(ItemModel itemModel) {
        openDatabase();  // Ensure database is open
        try {
            ContentValues values = new ContentValues();
            values.put(handler.COLUMN_ITEM_NAME, itemModel.getName().trim());
            values.put(handler.COLUMN_ITEM_DESCRIPTION, itemModel.getDescription().trim());
            values.put(handler.COLUMN_ITEM_PRICE, itemModel.getPrice());
            values.put(handler.COLUMN_ITEM_AVAILABILITY, itemModel.isAvailability() ? 1 : 0);
            values.put(handler.COLUMN_ITEM_CATEGORY, itemModel.getCategory());
            values.put(handler.COLUMN_ITEM_IMAGE, itemModel.getImage());

            // Define the selection criteria
            String selection = handler.COLUMN_ITEM_ID + " = ?";
            String[] selectionArgs = { String.valueOf(itemModel.getItemID()) };

            int rowsAffected = database.update(DbHandler.TABLE_ITEMS, values, selection, selectionArgs);
            if (rowsAffected > 0) {
                Toast.makeText(context, "Item updated successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Log.e(TAG, "Failed to Update Item: ID = " + itemModel.getItemID());
                Toast.makeText(context, "Item not found. Please try again.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(context, "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(context, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }
}