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

    private Coworker coworker;
    private String coworkerId;
    private Restaurant restaurant;
    private String restaurantId;
    private List<Restaurant> restaurantList;
    private Location location;

    @Override
    public void setCoworkerId(String userId) {
        coworkerId = userId;
    }

    @Override
    public String getCoworkerId() {
        return coworkerId;
    }

    @Override
    public void setCoworker(Coworker coworker) {
        coworker = coworker;
    }

    @Override
    public Coworker getCoworker() {
        return coworker;
    }

    @Override
    public void setRestaurantId(String restaurantId) { restaurantId = restaurantId; }

    @Override
    public String getRestaurantId() { return restaurantId; };

    @Override
    public void setRestaurant(Restaurant restaurant) {
        restaurant = restaurant;
    }

    @Override
    public Restaurant getRestaurant() {
        return restaurant;
    }

    @Override
    public void setRestaurantList(List<Restaurant> restaurantList) { restaurantList = restaurantList; }

    @Override
    public List<Restaurant> getRestaurantList() { return restaurantList; }

    @Override
    public void setLocation(Location location) { location = location; }

    @Override
    public Location getLocation() { return location; }

    public void saveLocationInSharedPreferences(Location pLocation) {
        PreferencesHelper.saveStringPreferences(PREF_KEY_LATITUDE, String.valueOf(location.getLatitude()));
        PreferencesHelper.saveStringPreferences(PREF_KEY_LONGITUDE, String.valueOf(location.getLongitude()));
    }

    @Override
    public double getLocationFromSharedPreferences(String typeLocation) {
        return  Double.parseDouble(Objects.requireNonNull(preferences.getString(typeLocation, "")));
    }
}
