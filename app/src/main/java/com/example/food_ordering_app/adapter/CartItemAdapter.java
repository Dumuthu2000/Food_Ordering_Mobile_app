package com.example.food_ordering_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

        // Initialize quantity
        final int[] quantity = {item.getQuantity()};
        holder.textViewQuantity.setText(String.valueOf(quantity[0]));

        // Increase button listener
        holder.buttonIncrease.setOnClickListener(v -> {
            quantity[0]++;
            holder.textViewQuantity.setText(String.valueOf(quantity[0]));
            item.setQuantity(quantity[0]); // Update the quantity in your ItemModel
            // Optionally notify the adapter or update cart data
        });

        // Decrease button listener
        holder.buttonDecrease.setOnClickListener(v -> {
            if (quantity[0] > 1) { // Assuming a minimum quantity of 1
                quantity[0]--;
                holder.textViewQuantity.setText(String.valueOf(quantity[0]));
                item.setQuantity(quantity[0]); // Update the quantity in your ItemModel
                // Optionally notify the adapter or update cart data
            }
        });
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
        public TextView textViewQuantity;
        public Button buttonDecrease;
        public Button buttonIncrease;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewAvailability = itemView.findViewById(R.id.textViewAvailability);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
        }
    }
}
