package com.example.go4lunch.goforlunch.ui.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;

public class RestaurantDetailViewModel extends ViewModel {


        private final RestaurantRepository mRestaurantRepo ;

    public RestaurantDetailViewModel(RestaurantRepository pRestaurantRepository) {
            mRestaurantRepo = pRestaurantRepository;

        }
        /**
         * Get detail restaurant
         * @return : object : restaurant
         */
        public MutableLiveData<Restaurant> getRestaurantDetail() {
            return mRestaurantRepo.getRestaurantDetail();
        }
}
