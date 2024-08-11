package com.example.food_ordering_app.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.UserModel;

public class UserDao {
    private static final String TAG = "UserDao";
    DbHandler handler;
    SQLiteDatabase database;

    // Register new user
    public void registerUser(UserModel userModel) {
        try {
            // Open the database for writing
            database = handler.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(handler.COLUMN_USERNAME, userModel.getUsername());
            values.put(handler.COLUMN_EMAIL, userModel.getEmail());
            values.put(handler.COLUMN_PASSWORD, userModel.getPassword());
            values.put(handler.COLUMN_PROFILE_PICTURE, userModel.getProfilePicture());

            long result = database.insert(DbHandler.TABLE_USERS, null, values);

            if (result == -1) {
                // Insert operation failed
                Log.e(TAG, "Failed to register user: " + userModel.getUsername());
                Toast.makeText(handler.getContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                // Insert operation succeeded
                Toast.makeText(handler.getContext(), "User registered successfully.", Toast.LENGTH_SHORT).show();
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

    //Login user
    public UserModel loginUser(String email, String password){
        try {
            Cursor cursor = database.query(DbHandler.TABLE_USERS,
                    new String[]{DbHandler.COLUMN_EMAIL, DbHandler.COLUMN_PASSWORD},
                    DbHandler.COLUMN_EMAIL + "=? AND " + DbHandler.COLUMN_PASSWORD + "=?",
                    new String[]{email, password}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                UserModel user = new UserModel(cursor.getString(0), cursor.getString(1));
                Toast.makeText(handler.getContext(), "User loging successfully.", Toast.LENGTH_SHORT).show();
                cursor.close();
                return user;
            }
            return null;
        }catch (SQLException e) {
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
        return null;
    }
}
