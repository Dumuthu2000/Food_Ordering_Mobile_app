package com.example.food_ordering_app.adapter;

import android.annotation.SuppressLint;
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

public class ItemCardAdapter extends RecyclerView.Adapter<ItemCardAdapter.CardViewHolder> {

    private List<ItemModel> itemList;
    private OnAddToCartListener onAddToCartListener;

    public interface OnAddToCartListener {
        void onAddToCart(ItemModel item);
    }

    public ItemCardAdapter(List<ItemModel> itemList, OnAddToCartListener listener) {
        this.itemList = itemList;
        this.onAddToCartListener = listener;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_item, parent, false);
        return new CardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
        ItemModel item = itemList.get(position);
        holder.textViewName.setText(item.getName());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewPrice.setText(String.valueOf(item.getPrice()));
        holder.textViewCategory.setText(item.getCategory());
        holder.textViewAvailability.setText(item.isAvailability() ? "Available" : "Unavailable");

        holder.buttonAddToCart.setOnClickListener(v -> {
            if (onAddToCartListener != null) {
                onAddToCartListener.onAddToCart(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewDescription;
        public TextView textViewPrice;
        public TextView textViewCategory;
        public TextView textViewAvailability;
        public Button buttonAddToCart;

        @SuppressLint("WrongViewCast")
        public CardViewHolder(View view) {
            super(view);
            textViewName = view.findViewById(R.id.textViewName);
            textViewDescription = view.findViewById(R.id.textViewDescription);
            textViewPrice = view.findViewById(R.id.textViewPrice);
            textViewCategory = view.findViewById(R.id.textViewCategory);
            textViewAvailability = view.findViewById(R.id.textViewAvailability);
            buttonAddToCart = view.findViewById(R.id.buttonAddToCart);
        }
    }
}
