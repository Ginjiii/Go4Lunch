package com.example.go4lunch.goforlunch.service;

import android.location.Location;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.utils.PreferencesHelper;

import java.util.List;
import java.util.Objects;

import static com.example.go4lunch.goforlunch.utils.PreferencesHelper.preferences;

public class Go4LunchService implements Api {

    public static final String PREF_KEY_LATITUDE = "PREF_KEY_LATITUDE";
    public static final String PREF_KEY_LONGITUDE = "PREF_KEY_LONGITUDE";

    private Coworker mCoworker;
    private String mCoworkerId;
    private Restaurant mRestaurant;
    private String mRestaurantId;
    private List<Restaurant> mRestaurantList;
    private Location mLocation;

    @Override
    public void setCoworkerId(String userId) {
        mCoworkerId = userId;
    }

    @Override
    public String getCoworkerId() {
        return mCoworkerId;
    }

    @Override
    public void setCoworker(Coworker coworker) {
        mCoworker = coworker;
    }

    @Override
    public Coworker getCoworker() {
        return mCoworker;
    }

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
