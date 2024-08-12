package com.example.food_ordering_app.adapter;

import android.annotation.SuppressLint;
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
import com.bumptech.glide.Glide;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {
    private List<ItemModel> itemList;
    private final OnAddToCartListener listener;

    public ItemAdapter(List<ItemModel> itemList, OnAddToCartListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_cart_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.textViewAvailability.setText(item.isAvailability() ? "Available" : "Out of Stock");

        // Load image using Glide
        Glide.with(holder.imageViewItem.getContext())
                .load(item.getImage())
                .into(holder.imageViewItem);

        holder.buttonAddToCart.setOnClickListener(v -> {
            listener.onAddToCart(item);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnAddToCartListener {
        void onAddToCart(ItemModel itemModel);
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageViewItem;
        public TextView textViewName;
        public TextView textViewDescription;
        public TextView textViewPrice;
        public TextView textViewAvailability;
        public Button buttonAddToCart;

        @SuppressLint("WrongViewCast")
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
            buttonAddToCart = itemView.findViewById(R.id.buttonAddToCart);
        }
    }
}
