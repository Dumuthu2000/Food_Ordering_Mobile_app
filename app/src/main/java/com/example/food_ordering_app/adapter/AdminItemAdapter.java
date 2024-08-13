package com.example.food_ordering_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.R;
import com.example.food_ordering_app.model.ItemModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdminItemAdapter extends RecyclerView.Adapter<AdminItemAdapter.ViewHolder> {
    private List<ItemModel> itemList = new ArrayList<>();
    private OnDeleteListener onDeleteListener;
    private OnEditListener onEditListener; // Added for edit functionality

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.itemName.setText(item.getName());
        holder.itemDescription.setText(item.getDescription());
        holder.itemPrice.setText(String.valueOf(item.getPrice()));
        holder.bind(item); // Bind item to ViewHolder
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setItemList(List<ItemModel> itemList) {
        this.itemList = itemList;
        notifyDataSetChanged();
    }

    // Method to set the delete listener
    public void setOnDeleteListener(OnDeleteListener listener) {
        this.onDeleteListener = listener;
    }

    // Method to set the edit listener
    public void setOnEditListener(OnEditListener listener) {
        this.onEditListener = listener;
    }

    // Interface for delete listener
    public interface OnDeleteListener {
        void onDelete(int itemId);
    }

    // Interface for edit listener
    public interface OnEditListener {
        void onEdit(ItemModel item);
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView itemName, itemDescription, itemPrice;
        Button buttonDelete, buttonEdit; // Added buttonEdit
        ImageView itemImageView; // Added ImageView for item image

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName = itemView.findViewById(R.id.textViewItemName);
            itemDescription = itemView.findViewById(R.id.textViewItemDescription);
            itemPrice = itemView.findViewById(R.id.textViewItemPrice);
            buttonDelete = itemView.findViewById(R.id.buttonDeleteItem);
            buttonEdit = itemView.findViewById(R.id.buttonEditItem); // Initialize buttonEdit
            itemImageView = itemView.findViewById(R.id.imageViewItem); // Initialize ImageView
        }

        public void bind(final ItemModel item) {
            buttonDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onDeleteListener != null) {
                        onDeleteListener.onDelete(item.getItemID());
                    }
                }
            });

            buttonEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onEditListener != null) {
                        onEditListener.onEdit(item);
                    }
                }
            });

            // Load the image using Picasso
            Picasso.get()
                    .load(item.getImage()) // URL of the image
                    .placeholder(R.drawable.placeholder_image) // Optional: placeholder image while loading
                    .error(R.drawable.placeholder_image) // Optional: error image if loading fails
                    .into(itemImageView); // Target ImageView
            }
    }
}