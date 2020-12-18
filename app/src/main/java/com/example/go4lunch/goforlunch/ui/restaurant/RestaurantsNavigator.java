package com.example.go4lunch.goforlunch.ui.restaurant;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface RestaurantsNavigator {

    void displayRestaurants(List<Restaurant> restaurants);
    void displayRestaurantDetail();
    void configureLocation(LatLng location);
}
