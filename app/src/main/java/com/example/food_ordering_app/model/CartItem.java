package com.example.food_ordering_app.model;

import java.io.Serializable;

public class CartItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private  int itemID;
    private  String name;
    private String image;
    private  double price;
    private  int quantity;

    public CartItem() {
    }

    public CartItem(int itemID, String name, String image, double price) {
        this.itemID = itemID;
        this.name = name;
        this.image = image;
        this.price = price;
    }

    public CartItem(int itemID, String name, String image, double price, int quantity) {
        this.itemID = itemID;
        this.name = name;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
