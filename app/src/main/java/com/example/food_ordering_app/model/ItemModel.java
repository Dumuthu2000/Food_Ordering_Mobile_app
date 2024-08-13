package com.example.food_ordering_app.model;

import java.io.Serializable;

public class ItemModel implements Serializable {
    private int itemID;
    private String name;
    private String description;
    private double price;
    private boolean availability;
    private String category;
    private String image;
    private int quantity; // Added field for quantity

    // Constructor with quantity
    public ItemModel(int itemID, String name, String description, double price, boolean availability, String category, String image, int quantity) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.category = category;
        this.image = image;
        this.quantity = quantity; // Initialize quantity
    }

    // Constructor without quantity (default quantity to 1)
    public ItemModel(String name, String description, double price, boolean availability, String category, String image) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.category = category;
        this.image = image;
        this.quantity = 1; // Default quantity
    }

    // Getters and setters
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity; // Getter for quantity
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity; // Setter for quantity
    }
}
