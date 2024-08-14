package com.example.food_ordering_app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.AdminModel;

public class AdminDao {
    private static final String TAG = "AdminDao";
    private final Context context;
    DbHandler handler;
    SQLiteDatabase database;

    public AdminDao(Context context) {
        this.context = context;
        handler = new DbHandler(context);
        database = handler.getWritableDatabase();
    }

    // Register new admin
    public void registerAdmin(AdminModel adminModel) {
        SQLiteDatabase database = null;
        try {
            // Open the database for writing
            database = handler.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(handler.COLUMN_ADMIN_USERNAME, adminModel.getUsername().trim());
            values.put(handler.COLUMN_ADMIN_EMAIL, adminModel.getEmail().trim());
            values.put(handler.COLUMN_ADMIN_PASSWORD, adminModel.getPassword().trim());

            long result = database.insert(DbHandler.TABLE_ADMIN_USERS, null, values);

            if (result == -1) {
                // Insert operation failed
                Log.e(TAG, "Failed to register admin: " + adminModel.getUsername());
                Toast.makeText(handler.getContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                // Insert operation succeeded
                Toast.makeText(handler.getContext(), "Admin registered successfully.", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            // Handle SQL exceptions
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(handler.getContext(), "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            // Handle any other exceptions
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(handler.getContext(), "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        } finally {
            // Close the database
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    //Login admin
    public AdminModel loginAdmin(String email, String password) {
        Cursor cursor = null;
        try {
            cursor = database.query(DbHandler.TABLE_ADMIN_USERS,
                    new String[]{DbHandler.COLUMN_ADMIN_EMAIL, DbHandler.COLUMN_ADMIN_PASSWORD},
                    DbHandler.COLUMN_ADMIN_EMAIL + "=? AND " + DbHandler.COLUMN_ADMIN_PASSWORD + "=?",
                    new String[]{email, password}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                AdminModel admin = new AdminModel(cursor.getString(0), cursor.getString(1));
                Toast.makeText(handler.getContext(), "Admin logged in successfully.", Toast.LENGTH_SHORT).show();
                return admin;
            } else {
                Toast.makeText(handler.getContext(), "Invalid email or password.", Toast.LENGTH_SHORT).show();
                return null;
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
        }
        return null;
    }

    public void saveAdminToSharedPreferences(AdminModel admin) {
    }
}