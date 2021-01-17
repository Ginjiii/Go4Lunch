package com.example.go4lunch.goforlunch.service;

import android.location.Location;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;

import java.util.List;

public interface Api {

    void setCoworkerId(String mUserId) ;

    String getCoworkerId();

    void setCoworker(Coworker mCoworker);

    Coworker getCoworker() ;

    void setRestaurantId(String mRestaurantId);

    String getRestaurantId();

    void setRestaurant(Restaurant mRestaurant);

    Restaurant getRestaurant();

    void setRestaurantList(List<Restaurant> mRestaurantList);

    List<Restaurant> getRestaurantList();

    void setLocation(Location mLocation);

    Location getLocation();

}
