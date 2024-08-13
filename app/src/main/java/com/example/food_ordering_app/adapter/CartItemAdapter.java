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
    private final OnQuantityChangeListener quantityChangeListener;

    public CartItemAdapter(List<ItemModel> cartItems, OnQuantityChangeListener quantityChangeListener) {
        this.cartItems = cartItems;
        this.quantityChangeListener = quantityChangeListener;
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
        holder.textViewPrice.setText(String.format("$%.2f", item.getPrice()));
        holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));

        holder.buttonIncrease.setOnClickListener(v -> {
            item.setQuantity(item.getQuantity() + 1);
            holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));
            quantityChangeListener.onQuantityChange(cartItems);
        });

        holder.buttonDecrease.setOnClickListener(v -> {
            if (item.getQuantity() > 1) {
                item.setQuantity(item.getQuantity() - 1);
                holder.textViewQuantity.setText(String.valueOf(item.getQuantity()));
                quantityChangeListener.onQuantityChange(cartItems);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(List<ItemModel> updatedCartItems);
    }

    public static class CartItemViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView textViewQuantity;
        public Button buttonIncrease;
        public Button buttonDecrease;

        public CartItemViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            textViewQuantity = itemView.findViewById(R.id.textViewQuantity);
            buttonIncrease = itemView.findViewById(R.id.buttonIncrease);
            buttonDecrease = itemView.findViewById(R.id.buttonDecrease);
        }
    }
}
