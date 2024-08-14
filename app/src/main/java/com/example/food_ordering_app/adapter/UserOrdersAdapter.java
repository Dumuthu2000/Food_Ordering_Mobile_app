package com.example.food_ordering_app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.food_ordering_app.R;
import com.example.food_ordering_app.model.ItemModel;
import com.example.food_ordering_app.model.OrderModel;

import java.util.List;

public class UserOrdersAdapter extends RecyclerView.Adapter<UserOrdersAdapter.OrderViewHolder> {
    private List<OrderModel> ordersList;
    private Context context;

    public UserOrdersAdapter(List<OrderModel> ordersList, Context context) {
        this.ordersList = ordersList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_orders_card, parent, false);
        return new OrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel order = ordersList.get(position);
        holder.textViewOrderId.setText("Order ID: " + order.getOrderId());
        holder.textViewOrderDate.setText("Order Date: " + order.getOrderDate());
        holder.textViewTotalAmount.setText("Total Amount: $" + String.format("%.2f", order.getTotalAmount()));
    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView textViewOrderId, textViewOrderDate, textViewTotalAmount;
        LinearLayout linearLayoutOrderItems;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewOrderId = itemView.findViewById(R.id.textViewOrderId);
            textViewOrderDate = itemView.findViewById(R.id.textViewOrderDate);
            textViewTotalAmount = itemView.findViewById(R.id.textViewTotalAmount);
            linearLayoutOrderItems = itemView.findViewById(R.id.linearLayoutOrderItems);
        }
    }
}
