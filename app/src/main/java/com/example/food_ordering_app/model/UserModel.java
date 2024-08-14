package com.example.food_ordering_app.model;

public class UserModel {
    private int userId;
    private String username;
    private String email;
    private String password;
    private String profilePicture;

    public UserModel() {
    }

    public UserModel(int userId, String username, String email) {
        this.userId = userId;
        this.username = username;
        this.email = email;
    }

    public UserModel(int userId, String username, String email, String profilePicture) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.profilePicture = profilePicture;
    }

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserModel(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserModel(String username, String email, String password, String profilePicture) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public UserModel(int userId, String username, String email, String password, String profilePicture) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.profilePicture = profilePicture;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
