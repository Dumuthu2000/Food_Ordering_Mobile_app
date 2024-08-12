package com.example.food_ordering_app.model;

public class CartModel {
    private int cart_id;
    private String itemName;
    private double itemPrice;
    private String itemImage;
    private int quantity;
    private double totalAmount;

    public CartModel() {
    }

    public CartModel(String itemName, double itemPrice, int quantity, double totalAmount) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }

    public CartModel(int cart_id, String itemName, double itemPrice, int quantity, double totalAmount) {
        this.cart_id = cart_id;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.totalAmount = totalAmount;
    }


    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
