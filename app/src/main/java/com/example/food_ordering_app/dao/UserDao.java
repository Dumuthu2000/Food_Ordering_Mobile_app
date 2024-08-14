package com.example.food_ordering_app.dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.food_ordering_app.database.DbHandler;
import com.example.food_ordering_app.model.UserModel;

public class UserDao {
    private static final String TAG = "UserDao";
    private final Context context;
    DbHandler handler;
    SQLiteDatabase database;

    public UserDao(Context context) {
        this.context = context;
        handler = new DbHandler(context);
        database = handler.getWritableDatabase();
    }

    public void open() {
        database = handler.getWritableDatabase();
    }

    public void close() {
        handler.close();
    }

    // Register new user
    public void registerUser(UserModel userModel) {
        SQLiteDatabase database = null;
        try {
            // Open the database for writing
            database = handler.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(handler.COLUMN_USERNAME, userModel.getUsername().trim());
            values.put(handler.COLUMN_EMAIL, userModel.getEmail().trim());
            values.put(handler.COLUMN_PASSWORD, userModel.getPassword().trim());
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
    public UserModel loginUser(String email, String password) {
        Cursor cursor = null;
        UserModel user = null;
        try {
            // Query to fetch user details based on email and password
            cursor = database.query(
                    DbHandler.TABLE_USERS,
                    new String[]{DbHandler.COLUMN_USER_ID, DbHandler.COLUMN_USERNAME, DbHandler.COLUMN_EMAIL},
                    DbHandler.COLUMN_EMAIL + "=? AND " + DbHandler.COLUMN_PASSWORD + "=?",
                    new String[]{email, password},
                    null, null, null
            );

            if (cursor != null && cursor.moveToFirst()) {
                // Extract user details from cursor
                int userId = cursor.getInt(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_USER_ID));
                String username = cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_USERNAME));
                String userEmail = cursor.getString(cursor.getColumnIndexOrThrow(DbHandler.COLUMN_EMAIL));

                // Create a UserModel instance with fetched details
                user = new UserModel(userId, username, userEmail);

                // Notify success
                Toast.makeText(handler.getContext(), "User logged in successfully.", Toast.LENGTH_SHORT).show();
            } else {
                // Notify failure
                Toast.makeText(handler.getContext(), "Invalid email or password.", Toast.LENGTH_SHORT).show();
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
        return user;
    }


    //Add shared preferences to store user data temporary
    public void saveUserToSharedPreferences(UserModel user) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Save user details to SharedPreferences
        editor.putInt("userId", user.getUserId());
        editor.putString("userName", user.getUsername());
        editor.putString("userEmail", user.getEmail());
        editor.putBoolean("isLoggedIn", true);
        editor.apply();

        // Log the user details
        Log.d("UserPrefs", "User ID: " + user.getUserId());
        Log.d("UserPrefs", "User Name: " + user.getUsername());
        Log.d("UserPrefs", "User Email: " + user.getEmail());
        Log.d("UserPrefs", "Is Logged In: true");
    }

    public UserModel getUserById(int userId) {
        UserModel user = null;
        String query = "SELECT * FROM " + DbHandler.TABLE_USERS + " WHERE " + DbHandler.COLUMN_USER_ID + " = ?";
        Cursor cursor = database.rawQuery(query, new String[]{String.valueOf(userId)});

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DbHandler.COLUMN_USER_ID);
            int usernameIndex = cursor.getColumnIndex(DbHandler.COLUMN_USERNAME);
            int emailIndex = cursor.getColumnIndex(DbHandler.COLUMN_EMAIL);
            int passwordIndex = cursor.getColumnIndex(DbHandler.COLUMN_PASSWORD);
            int profilePictureIndex = cursor.getColumnIndex(DbHandler.COLUMN_PROFILE_PICTURE);

            if (idIndex != -1 && usernameIndex != -1 && emailIndex != -1 && passwordIndex != -1) {
                int id = cursor.getInt(idIndex);
                String username = cursor.getString(usernameIndex);
                String email = cursor.getString(emailIndex);
                String password = cursor.getString(passwordIndex);
                String profilePicture = cursor.getString(profilePictureIndex);

                user = new UserModel(id, username, email, password, profilePicture);
            }
        }
        cursor.close();
        return user;
    }




}
