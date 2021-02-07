package com.example.go4lunch.goforlunch.ui.maps;

import androidx.lifecycle.LiveData;
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
     * Get restaurant list.
     *
     * @return restaurant list.
     */
    public LiveData<List<Restaurant>> getRestaurantList(double latitude, double longitude) {
        return repository.getGoogleRestaurantList(latitude, longitude);
    }
}