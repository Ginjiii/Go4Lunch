package com.example.go4lunch.goforlunch.ui.maps;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;

public class MapsViewModel extends BaseViewModel {

    public MapsViewModel(RestaurantRepository restaurantRepository, CoworkerRepository coworkerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.coworkerRepository = coworkerRepository;
    }
}