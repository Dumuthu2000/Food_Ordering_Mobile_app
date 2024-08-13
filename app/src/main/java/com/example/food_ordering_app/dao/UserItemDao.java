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

public class UserItemDao {
    private static final String TAG = "ItemDao";
    private final Context context;
    private final DbHandler handler;

    public UserItemDao(Context context) {
        this.context = context;
        this.handler = new DbHandler(context);
    }

    // Add new item
    public void addItem(ItemModel itemModel) {
        try (SQLiteDatabase database = handler.getWritableDatabase()) {
            ContentValues values = new ContentValues();
            values.put(DbHandler.COLUMN_ITEM_NAME, itemModel.getName().trim());
            values.put(DbHandler.COLUMN_ITEM_DESCRIPTION, itemModel.getDescription().trim());
            values.put(DbHandler.COLUMN_ITEM_PRICE, itemModel.getPrice());
            values.put(DbHandler.COLUMN_ITEM_AVAILABILITY, itemModel.isAvailability() ? 1 : 0);
            values.put(DbHandler.COLUMN_ITEM_CATEGORY, itemModel.getCategory().trim());
            values.put(DbHandler.COLUMN_ITEM_IMAGE, itemModel.getImage());

            long result = database.insert(DbHandler.TABLE_ITEMS, null, values);

            if (result == -1) {
                Log.e(TAG, "Failed to add item: " + itemModel.getName());
                Toast.makeText(context, "Failed to add item. Please try again.", Toast.LENGTH_SHORT).show();
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

    // Fetch all items
    public List<ItemModel> getAllItems() {
        List<ItemModel> itemList = new ArrayList<>();
        SQLiteDatabase database = null;
        Cursor cursor = null;

        try {
            database = handler.getReadableDatabase();

            String[] projection = {
                    DbHandler.COLUMN_ITEM_ID,
                    DbHandler.COLUMN_ITEM_NAME,
                    DbHandler.COLUMN_ITEM_DESCRIPTION,
                    DbHandler.COLUMN_ITEM_PRICE,
                    DbHandler.COLUMN_ITEM_AVAILABILITY,
                    DbHandler.COLUMN_ITEM_CATEGORY,
                    DbHandler.COLUMN_ITEM_IMAGE
            };

            cursor = database.query(
                    DbHandler.TABLE_ITEMS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    ItemModel itemModel = new ItemModel(
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_ITEM_NAME)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_ITEM_DESCRIPTION)),
                            cursor.getDouble(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_ITEM_PRICE)),
                            cursor.getInt(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_ITEM_AVAILABILITY)) > 0,
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_ITEM_CATEGORY)),
                            cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_ITEM_IMAGE))
                    );
                    itemModel.setItemID(cursor.getInt(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_ITEM_ID)));
                    itemList.add(itemModel);
                } while (cursor.moveToNext());
            }

        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(handler.getContext(), "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(handler.getContext(), "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (database != null && database.isOpen()) {
                database.close();
            }
        }

        return itemList;
    }

}
