package com.example.go4lunch.goforlunch.ui.restaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;

import java.util.List;

public class RestaurantsViewModel  extends ViewModel {

    private RestaurantRepository repository;

  //  public RestaurantsViewModel(RestaurantRepository restaurantsRepository) {
   //     repository = restaurantsRepository;
    //}

    /**
     * Get restaurant list
     * @return : list object : restaurant list
     */
    public LiveData<List<Restaurant>>  getRestaurantList() {
        return null;
    }
}