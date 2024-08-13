package com.example.food_ordering_app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class AdminUserDao {
    private static final String TAG = "UserDao";
    private final Context context;
    DbHandler handler;
    SQLiteDatabase database;

    public AdminUserDao(Context context) {
        this.context = context;
        handler = new DbHandler(context);
        database = handler.getWritableDatabase();
    }

    // Register new user
    public void registerUser(UserModel userModel) {
        SQLiteDatabase database = null;
        try {
            database = handler.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(handler.COLUMN_USERNAME, userModel.getUsername().trim());
            values.put(handler.COLUMN_EMAIL, userModel.getEmail().trim());
            values.put(handler.COLUMN_PASSWORD, userModel.getPassword().trim());
            values.put(handler.COLUMN_PROFILE_PICTURE, userModel.getProfilePicture());

            long result = database.insert(DbHandler.TABLE_USERS, null, values);

            if (result == -1) {
                Log.e(TAG, "Failed to register user: " + userModel.getUsername());
                Toast.makeText(handler.getContext(), "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(handler.getContext(), "User registered successfully.", Toast.LENGTH_SHORT).show();
            }

        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(handler.getContext(), "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(handler.getContext(), "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        } finally {
            if (database != null && database.isOpen()) {
                database.close();
            }
        }
    }

    // Login user
    public UserModel loginUser(String email, String password) {
        Cursor cursor = null;
        try {
            cursor = database.query(DbHandler.TABLE_USERS,
                    new String[]{DbHandler.COLUMN_EMAIL, DbHandler.COLUMN_PASSWORD},
                    DbHandler.COLUMN_EMAIL + "=? AND " + DbHandler.COLUMN_PASSWORD + "=?",
                    new String[]{email, password}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                UserModel user = new UserModel(cursor.getString(0), cursor.getString(1));
                Toast.makeText(handler.getContext(), "User logged in successfully.", Toast.LENGTH_SHORT).show();
                return user;
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


    // Get all users
    public List<UserModel> getAllUsers() {
        List<UserModel> userList = new ArrayList<>();
        Cursor cursor = null;
        try {
            // Fetch all columns including userId
            cursor = database.query(DbHandler.TABLE_USERS,
                    null, null, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Fetch all user details including userId
                    int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_USER_ID));
                    String username = cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_USERNAME));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_EMAIL));
                    String profilePicture = cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_PROFILE_PICTURE));

                    // Create UserModel with userId
                    UserModel user = new UserModel(userId, username, email, profilePicture);
                    userList.add(user);
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
        }
        return userList;
    }

    public void deleteUser(int userId) {
        SQLiteDatabase db = null;
        try {
            db = handler.getWritableDatabase();
            int rowsAffected = db.delete(DbHandler.TABLE_USERS,
                    DbHandler.COLUMN_USER_ID + "=?",
                    new String[]{String.valueOf(userId)});

            if (rowsAffected > 0) {
                Toast.makeText(context, "User deleted successfully.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Failed to delete user.", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Log.e(TAG, "SQL Error: " + e.getMessage());
            Toast.makeText(context, "Database error. Please contact support.", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.e(TAG, "Error: " + e.getMessage());
            Toast.makeText(context, "Unexpected error. Please try again.", Toast.LENGTH_LONG).show();
        } finally {
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

}