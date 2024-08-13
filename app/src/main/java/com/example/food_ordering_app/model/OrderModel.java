package com.example.food_ordering_app.model;

import java.util.ArrayList;
import java.util.List;

public class OrderModel {
    private int orderId;
    private int userId;
    private String orderDate;
    private double totalAmount;
    private String orderStatus;
    private String deliveryAddress;
    private List<ItemModel> orderItems;

    // Constructor, getters, and setters
    public OrderModel() {
        this.orderItems = new ArrayList<>();
    }

    public OrderModel(int orderId, int userId, String orderDate, double totalAmount, String orderStatus, String deliveryAddress, List<ItemModel> orderItems) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
        this.orderItems = orderItems;
    }

    public OrderModel(int userId, String orderDate, double totalAmount, String orderStatus, String deliveryAddress, List<ItemModel> orderItems) {
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.deliveryAddress = deliveryAddress;
        this.orderItems = orderItems;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public List<ItemModel> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<ItemModel> orderItems) {
        this.orderItems = orderItems;
    }
}

