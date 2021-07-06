package com.example.go4lunch.goforlunch.ui.restaurant;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;

public class RestaurantsViewModel extends BaseViewModel {

    public RestaurantsViewModel(RestaurantRepository restaurantRepository, CoworkerRepository coworkerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.coworkerRepository = coworkerRepository;
    }
}