package com.example.food_ordering_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.adapter.AdminItemAdapter;
import com.example.food_ordering_app.dao.ItemDao;
import com.example.food_ordering_app.model.ItemModel;
import com.example.food_ordering_app.model.SpacesItemDecoration;

import java.util.List;

public class AdminActivity extends AppCompatActivity {
    private EditText editTextItemName, editTextItemDescription, editTextItemPrice, editTextItemCategory, editTextItemImage;
    private CheckBox checkBoxItemAvailability;
    private AdminItemAdapter itemAdapter;
    private ItemDao itemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemDescription = findViewById(R.id.editTextItemDescription);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemCategory = findViewById(R.id.editTextItemCategory);
        editTextItemImage = findViewById(R.id.editTextItemImage);
        checkBoxItemAvailability = findViewById(R.id.checkBoxItemAvailability);

        RecyclerView recyclerViewItems = findViewById(R.id.recyclerViewItems);
        recyclerViewItems.setLayoutManager(new LinearLayoutManager(this));

        // Set the space between cards (e.g., 16dp)
        int spaceInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
        recyclerViewItems.addItemDecoration(new SpacesItemDecoration(spaceInPixels));

        itemAdapter = new AdminItemAdapter();
        recyclerViewItems.setAdapter(itemAdapter);

        itemDao = new ItemDao(this);

        itemAdapter.setOnDeleteListener(new AdminItemAdapter.OnDeleteListener() {
            @Override
            public void onDelete(int itemId) {
                itemDao.deleteItem(itemId);
                loadItems();
            }
        });

        itemAdapter.setOnEditListener(new AdminItemAdapter.OnEditListener() {
            @Override
            public void onEdit(ItemModel item) {
                showEditItemDialog(item);
            }
        });

        // Set up the back button (ImageView)
        ImageView backButton = findViewById(R.id.icon_manage_items);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Navigate back to DashBoardActivity
                Intent intent = new Intent(AdminActivity.this, DashboardActivity.class);
                startActivity(intent);
                finish(); // Optional: finish the current activity to remove it from the back stack
            }
        });

        loadItems();
    }

    public void handleAddItem(View view) {
        String itemName = editTextItemName.getText().toString().trim();
        String itemDescription = editTextItemDescription.getText().toString().trim();
        double itemPrice = Double.parseDouble(editTextItemPrice.getText().toString().trim());
        String itemCategory = editTextItemCategory.getText().toString().trim();
        String itemImage = editTextItemImage.getText().toString().trim();
        boolean itemAvailability = checkBoxItemAvailability.isChecked();

        ItemModel itemModel = new ItemModel(itemName, itemDescription, itemPrice, itemAvailability, itemCategory, itemImage);
        itemDao.addItem(itemModel);

        clearInputs(); // Clear inputs after adding item
        loadItems();
    }

    private void clearInputs() {
        editTextItemName.setText("");
        editTextItemDescription.setText("");
        editTextItemPrice.setText("");
        editTextItemCategory.setText("");
        editTextItemImage.setText("");
        checkBoxItemAvailability.setChecked(false);
    }

    private void showEditItemDialog(ItemModel item) {
        EditItemDialog editItemDialog = new EditItemDialog(this, item);
        editItemDialog.setOnItemEditedListener(new EditItemDialog.OnItemEditedListener() {
            @Override
            public void onItemEdited(ItemModel updatedItem) {
                itemDao.updateItem(updatedItem);
                loadItems(); // Refresh the list
                Toast.makeText(AdminActivity.this, "Item updated successfully.", Toast.LENGTH_SHORT).show();
            }
        });
        editItemDialog.show();
    }

    private void loadItems() {
        List<ItemModel> items = itemDao.getAllItems();
        itemAdapter.setItemList(items);
    }
}