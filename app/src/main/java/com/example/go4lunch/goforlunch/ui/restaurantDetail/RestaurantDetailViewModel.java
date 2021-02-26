package com.example.go4lunch.goforlunch.ui.restaurantDetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.utils.Actions;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailViewModel extends ViewModel {

    private final CoworkerRepository coworkerRepository ;
    private final RestaurantRepository restaurantRepository ;
    private Restaurant restaurant;

    public RestaurantDetailViewModel(RestaurantRepository mRestaurantRepository, CoworkerRepository mCoworkerRepository) {
        restaurantRepository = mRestaurantRepository;
        coworkerRepository = mCoworkerRepository;
    }

    public LiveData<Restaurant> getRestaurantDetail(String placeId) {

        return restaurantRepository.getGoogleRestaurantDetail(placeId);
    }
    public RestaurantDetail getRestaurantDetailList(String placeId) {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        Restaurant restaurant = new Restaurant();
            return restaurantRepository.getGoogleRestaurantDetailList(placeId,restaurants,restaurant);
    }
}
