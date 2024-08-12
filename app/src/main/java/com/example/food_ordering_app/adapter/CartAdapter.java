package com.example.food_ordering_app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.food_ordering_app.R;
import com.example.food_ordering_app.model.CartModel;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private List<CartModel> cartItemList;

    public CartAdapter(List<CartModel> cartItemList) {
        this.cartItemList = cartItemList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the item_card.xml layout for each cart item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_cart_item, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartModel cartItem = cartItemList.get(position);
        holder.textViewName.setText(cartItem.getItemName());
        holder.textViewPrice.setText(String.format("$%.2f", cartItem.getItemPrice()));
        holder.itemCount.setText(String.valueOf(cartItem.getQuantity()));

        // Assuming you have logic to set the image
        // holder.imageViewItem.setImageResource(cartItem.getImageResourceId());
    }

    @Override
    public int getItemCount() {
        return cartItemList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewPrice;
        public TextView itemCount;
        public ImageView imageViewItem;
        public TextView addBtn;
        public TextView reduceBtn;

        public CartViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            itemCount = itemView.findViewById(R.id.itemCount);
            imageViewItem = itemView.findViewById(R.id.imageViewItem);
            addBtn = itemView.findViewById(R.id.addBtn);
            reduceBtn = itemView.findViewById(R.id.reduceBtn);
        }
    }
}
