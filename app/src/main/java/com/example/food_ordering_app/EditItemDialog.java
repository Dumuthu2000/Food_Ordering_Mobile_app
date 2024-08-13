package com.example.food_ordering_app;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.food_ordering_app.model.ItemModel;

public class EditItemDialog extends Dialog {

    private EditText editTextItemName, editTextItemDescription, editTextItemPrice, editTextItemCategory, editTextItemImage;
    private CheckBox checkBoxItemAvailability;
    private Button buttonSaveChanges, buttonCancel;
    private ItemModel item;

    private OnItemEditedListener onItemEditedListener;

    public EditItemDialog(@NonNull Context context, ItemModel item) {
        super(context);
        this.item = item;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_item);

        // Initialize views
        editTextItemName = findViewById(R.id.editTextItemName);
        editTextItemDescription = findViewById(R.id.editTextItemDescription);
        editTextItemPrice = findViewById(R.id.editTextItemPrice);
        editTextItemCategory = findViewById(R.id.editTextItemCategory);
        editTextItemImage = findViewById(R.id.editTextItemImage);
        checkBoxItemAvailability = findViewById(R.id.checkBoxItemAvailability);
        buttonSaveChanges = findViewById(R.id.buttonSaveChanges);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Set current item details
        populateFields();

        // Set up button listeners
        buttonSaveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveChanges();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss(); // Close dialog
            }
        });

        // Set dialog width
        setDialogWidth();
    }

    private void populateFields() {
        editTextItemName.setText(item.getName());
        editTextItemDescription.setText(item.getDescription());
        editTextItemPrice.setText(String.valueOf(item.getPrice()));
        editTextItemCategory.setText(item.getCategory());
        editTextItemImage.setText(item.getImage());
        checkBoxItemAvailability.setChecked(item.isAvailability());
    }

    private void saveChanges() {
        // Update item with new values
        item.setName(editTextItemName.getText().toString().trim());
        item.setDescription(editTextItemDescription.getText().toString().trim());
        item.setPrice(Double.parseDouble(editTextItemPrice.getText().toString().trim()));
        item.setCategory(editTextItemCategory.getText().toString().trim());
        item.setImage(editTextItemImage.getText().toString().trim());
        item.setAvailability(checkBoxItemAvailability.isChecked());

        if (onItemEditedListener != null) {
            onItemEditedListener.onItemEdited(item);
        }

        dismiss(); // Close dialog
    }

    public void setOnItemEditedListener(OnItemEditedListener listener) {
        this.onItemEditedListener = listener;
    }

    public interface OnItemEditedListener {
        void onItemEdited(ItemModel item);
    }

    private void setDialogWidth() {
        // Get the window and set its width
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams layoutParams = window.getAttributes();
            layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT; // Set width to match parent
            window.setAttributes(layoutParams);
        }
    }
}