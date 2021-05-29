package com.example.go4lunch.goforlunch.models;

import com.google.firebase.Timestamp;

public class CoworkerRestaurantChoice {

    private String restaurantId;
    private String restaurantName;
    private String restaurantAddress;
    private Timestamp restaurantDateChoice;

    public CoworkerRestaurantChoice() {
    }

    public CoworkerRestaurantChoice(String restaurantId, String restaurantName, String restaurantAddress, Timestamp restaurantDateChoice) {
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantDateChoice = restaurantDateChoice;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    public Timestamp getRestaurantDateChoice() {
        return restaurantDateChoice;
    }

    public void setRestaurantDateChoice(Timestamp restaurantDateChoice) {
        this.restaurantDateChoice = restaurantDateChoice;
    }
}
