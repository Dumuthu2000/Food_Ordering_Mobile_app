package com.example.food_ordering_app.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FoodManagement.db";
    private static final int DATABASE_VERSION = 2;

    // Table and column names
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_PASSWORD = "password";
    public static final String COLUMN_PROFILE_PICTURE = "profile_picture";

    // New table and column names for items
    public static final String TABLE_ITEMS = "items";
    public static final String COLUMN_ITEM_ID = "item_id";
    public static final String COLUMN_ITEM_NAME = "name";
    public static final String COLUMN_ITEM_DESCRIPTION = "description";
    public static final String COLUMN_ITEM_PRICE = "price";
    public static final String COLUMN_ITEM_AVAILABILITY = "availability";
    public static final String COLUMN_ITEM_CATEGORY = "category";
    public static final String COLUMN_ITEM_IMAGE = "image";

    // Add a Context field
    private final Context context;

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // Method to get context
    public Context getContext() {
        return context;
    }

    // SQL statements to create tables
    private static final String CREATE_USERS_TABLE =
            "CREATE TABLE " + TABLE_USERS + " (" +
                    COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USERNAME + " TEXT, " +
                    COLUMN_EMAIL + " TEXT, " +
                    COLUMN_PASSWORD + " TEXT, " +
                    COLUMN_PROFILE_PICTURE + " TEXT);";

    // SQL statement to create items table
    private static final String CREATE_ITEMS_TABLE =
            "CREATE TABLE " + TABLE_ITEMS + " (" +
                    COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ITEM_NAME + " TEXT, " +
                    COLUMN_ITEM_DESCRIPTION + " TEXT, " +
                    COLUMN_ITEM_PRICE + " REAL, " +
                    COLUMN_ITEM_AVAILABILITY + " INTEGER, " +
                    COLUMN_ITEM_CATEGORY + " TEXT, " +
                    COLUMN_ITEM_IMAGE + " TEXT);";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}