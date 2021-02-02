package com.example.go4lunch.goforlunch.ui.maps;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;

import java.util.List;

public class MapsViewModel extends ViewModel {

    private final RestaurantRepository repository ;

    public MapsViewModel(RestaurantRepository restaurantRepository) {

        repository = restaurantRepository ;
    }

    /**
     * Get restaurant list
     * @return : list object : restaurant list
     */
    public MutableLiveData<List<Restaurant>> getRestaurantList(double latitude, double longitude) {
        return repository.getRestaurantList(latitude, longitude);
    }
}