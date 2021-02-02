package com.example.go4lunch.goforlunch.ui.restaurant;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.utils.Actions;

import java.util.List;

public class RestaurantDetailViewModel extends ViewModel {


        private final RestaurantRepository restaurantRepo ;
        private final CoworkerRepository coworkerRepo;

    public RestaurantDetailViewModel(RestaurantRepository pRestaurantRepository, CoworkerRepository mCoworkerRepo) {
        restaurantRepo = pRestaurantRepository;
        coworkerRepo = mCoworkerRepo;
        }

    /**
     * Get or save the workmate restaurant choice
     * @param actions : enum : action to do
     * @return : enum : result of the action
     */
    public MutableLiveData<Actions> getCoworkerChoiceForRestaurant(Actions actions) {
        return coworkerRepo.getCoworkerRestaurantChoice(actions);
    }

    /**
     * Get or save the workmate restaurant like
     * @param actions : enum : action to do
     * @return : enum : result of the action
     */
    public MutableLiveData<Actions> getCoworkerLikeForRestaurant(Actions actions) {
        return coworkerRepo.getCoworkerLikeForRestaurant(actions);
    }


    /**
     * Get  workmate information
     * @return : object : workmate
     */
    public MutableLiveData<Coworker> getCoworkerData() {
        return coworkerRepo.getCoworkerData();
    }
        /**
         * Get detail restaurant
         * @return : object : restaurant
         */
        public MutableLiveData<Restaurant> getRestaurantDetail() {
            return restaurantRepo.getRestaurantDetail();
        }
}
