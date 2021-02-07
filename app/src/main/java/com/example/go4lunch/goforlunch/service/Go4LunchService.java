package com.example.go4lunch.goforlunch.service;

import android.location.Location;

import com.example.go4lunch.goforlunch.models.Restaurant;

import java.util.List;

public class Go4LunchService implements Api {
    private Restaurant mRestaurant;
    private String mRestaurantId;
    private List<Restaurant> mRestaurantList;
    private Location mLocation;

    @Override
    public void setRestaurantId(String restaurantId) { mRestaurantId = restaurantId; }

    @Override
    public String getRestaurantId() { return mRestaurantId; };

    @Override
    public void setRestaurant(Restaurant restaurant) {
        mRestaurant = restaurant;
    }

    @Override
    public Restaurant getRestaurant() {
        return mRestaurant;
    }

    @Override
    public void setRestaurantList(List<Restaurant> restaurantList) { mRestaurantList = restaurantList; }

    @Override
    public List<Restaurant> getRestaurantList() { return mRestaurantList; }

    @Override
    public void setLocation(Location location) { mLocation = location; }

    @Override
    public Location getLocation() { return mLocation; }

}