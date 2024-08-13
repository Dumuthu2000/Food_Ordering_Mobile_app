package com.example.food_ordering_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.R;
import com.example.food_ordering_app.model.ItemModel;

import java.util.List;

public class CartItemAdapter extends RecyclerView.Adapter<CartItemAdapter.CartItemViewHolder> {
    private List<ItemModel> cartItems;

    public CartItemAdapter(List<ItemModel> cartItems) {
        this.cartItems = cartItems;
    }

    @NonNull
    @Override
    public CartItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cart_card, parent, false);
        return new CartItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemViewHolder holder, int position) {
        ItemModel item = cartItems.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.textViewAvailability.setText(item.isAvailability() ? "Available" : "Out of Stock");
        // Use Glide or other libraries if you have images to load
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewDescription;
        public TextView textViewPrice;
        public TextView textViewAvailability;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
        }
    }
}
