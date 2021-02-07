package com.example.go4lunch.goforlunch.ui.restaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;

import java.util.List;

public class RestaurantsViewModel extends ViewModel {

    private final String TAG = RestaurantsViewModel.class.getSimpleName();

    private final RestaurantRepository mRepository;

    public RestaurantsViewModel(RestaurantRepository restaurantRepository) {
        mRepository = restaurantRepository;
    }

    /**
     * Get restaurant list.
     *
     * @return restaurant list.
     */
    public LiveData<List<Restaurant>> getRestaurantList(double latitude, double longitude) {
        return mRepository.getGoogleRestaurantList(latitude, longitude);
    }
}