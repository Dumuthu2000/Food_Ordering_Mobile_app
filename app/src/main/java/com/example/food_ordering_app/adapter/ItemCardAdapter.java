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

public class ItemCardAdapter extends RecyclerView.Adapter<ItemCardAdapter.CardViewHolder> {

    private List<ItemModel> cardList;

    private OnAddToCartListener onAddToCartListener;
    public interface OnAddToCartListener {
        void onAddToCart();
    }

    public ItemCardAdapter(List<ItemModel> cardList, OnAddToCartListener listener) {
        this.cardList = cardList;
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
        ItemModel card = cardList.get(position);
        holder.textViewName.setText(card.getName());
        holder.textViewDescription.setText(card.getDescription());
        holder.textViewPrice.setText(String.valueOf(card.getPrice()));
        holder.textViewCategory.setText(card.getCategory());
        holder.textViewAvailability.setText(card.isAvailability() ? "Available" : "Unavailable");

        // Handle Add to Cart button click
        holder.buttonAddToCart.setOnClickListener(v -> {
            onAddToCartListener.onAddToCart();
        });
    }


    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public static class CardViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewName;
        public TextView textViewDescription;
        public TextView textViewPrice;
        public TextView textViewCategory;
        public TextView textViewAvailability;
        public TextView buttonAddToCart;

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
