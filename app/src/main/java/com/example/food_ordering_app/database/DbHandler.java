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

    public static final String TABLE_ORDERS = "orders";
    public static final String COLUMN_ORDER_ID = "order_id";
    public static final String COLUMN_ORDER_DATE = "order_date";
    public static final String COLUMN_TOTAL_AMOUNT = "total_amount";
    public static final String COLUMN_ORDER_STATUS = "order_status";
    public static final String COLUMN_DELIVERY_ADDRESS = "delivery_address";

    public static final String TABLE_ORDER_ITEMS = "order_items";
    public static final String COLUMN_ORDER_ITEM_ID = "order_item_id";
    public static final String COLUMN_MENU_ITEM_ID = "menu_item_id";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTAL_ITEM_PRICE = "total_price";

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

    //SQL statements for order table
    private static final String CREATE_ORDERS_TABLE =
            "CREATE TABLE " + TABLE_ORDERS + " (" +
                    COLUMN_ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_USER_ID + " INTEGER, " +
                    COLUMN_ORDER_DATE + " TEXT, " +
                    COLUMN_TOTAL_AMOUNT + " REAL, " +
                    COLUMN_ORDER_STATUS + " TEXT, " +
                    COLUMN_DELIVERY_ADDRESS + " TEXT, " +
                    "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES " + TABLE_USERS + "(" + COLUMN_USER_ID + "))";

    //SQL statements for OrderItem table
    private static final String CREATE_ORDER_ITEMS_TABLE =
            "CREATE TABLE " + TABLE_ORDER_ITEMS + " (" +
                    COLUMN_ORDER_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_ORDER_ID + " INTEGER, " +
                    COLUMN_MENU_ITEM_ID + " INTEGER, " +
                    COLUMN_QUANTITY + " INTEGER, " +
                    COLUMN_TOTAL_ITEM_PRICE + " REAL, " +
                    "FOREIGN KEY (" + COLUMN_ORDER_ID + ") REFERENCES " + TABLE_ORDERS + "(" + COLUMN_ORDER_ID + "), " +
                    "FOREIGN KEY (" + COLUMN_MENU_ITEM_ID + ") REFERENCES " + TABLE_ITEMS + "(" + COLUMN_ITEM_ID + "))";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERS_TABLE);
        db.execSQL(CREATE_ITEMS_TABLE);
        db.execSQL(CREATE_ORDERS_TABLE);
        db.execSQL(CREATE_ORDER_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEMS);
        onCreate(db);
    }
}