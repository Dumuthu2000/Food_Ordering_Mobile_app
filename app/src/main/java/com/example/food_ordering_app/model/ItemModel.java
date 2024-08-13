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

    public ItemModel(String name, String description, double price, boolean availability, String category, String image) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.category = category;
        this.image = image;
    }

    public ItemModel(int itemID, String name, String description, double price, boolean availability, String category, String image) {
        this.itemID = itemID;
        this.name = name;
        this.description = description;
        this.price = price;
        this.availability = availability;
        this.category = category;
        this.image = image;
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

    public int getId() {
        return itemID;
    }
}
