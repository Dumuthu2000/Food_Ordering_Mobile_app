package com.example.food_ordering_app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.food_ordering_app.dao.UserItemDao;
import com.example.food_ordering_app.model.ItemModel;

public class AddItemActivity extends AppCompatActivity {

    private EditText editTextItemName, editTextItemDescription, editTextItemPrice,
            editTextItemCategory, editTextItemImage;
    private CheckBox checkBoxItemAvailability;
    private Button buttonSubmitItem;
    private UserItemDao itemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        // Initialize views
        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemDescription = findViewById(R.id.editTextItemDescription);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemCategory = findViewById(R.id.editTextItemCategory);
        editTextItemImage = findViewById(R.id.editTextItemImage);
        checkBoxItemAvailability = findViewById(R.id.checkBoxItemAvailability);
        buttonSubmitItem = findViewById(R.id.buttonSubmitItem);

        // Initialize ItemDao
        itemDao = new UserItemDao(this);

        // Set up button click listener
        buttonSubmitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleAddItem();
            }
        });
    }

    public void handleAddItem() {
        // Get values from input fields
        String name = editTextItemName.getText().toString().trim();
        String description = editTextItemDescription.getText().toString().trim();
        String priceString = editTextItemPrice.getText().toString().trim();
        String category = editTextItemCategory.getText().toString().trim();
        String image = editTextItemImage.getText().toString().trim();
        boolean availability = checkBoxItemAvailability.isChecked();

        // Validate input
        if (name.isEmpty() || description.isEmpty() || priceString.isEmpty() || category.isEmpty()) {
            Toast.makeText(this, "Please fill all required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Parse price
        double price;
        try {
            price = Double.parseDouble(priceString);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid price format", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create ItemModel object
        ItemModel newItem = new ItemModel(name, description, price, availability, category, image);

        // Add item to database
        itemDao.addItem(newItem);

        // Clear input fields after successful addition
        clearInputFields();
    }

    private void clearInputFields() {
        editTextItemName.setText("");
        editTextItemDescription.setText("");
        editTextItemPrice.setText("");
        editTextItemCategory.setText("");
        editTextItemImage.setText("");
        checkBoxItemAvailability.setChecked(false);
    }
}