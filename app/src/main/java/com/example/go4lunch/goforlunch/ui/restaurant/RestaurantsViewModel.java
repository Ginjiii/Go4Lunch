package com.example.go4lunch.goforlunch.ui.restaurant;

import androidx.lifecycle.LiveData;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class RestaurantsViewModel extends BaseViewModel {

    private RestaurantRepository restaurantRepository;
    private CoworkerRepository coworkerRepository;

    public RestaurantsViewModel(RestaurantRepository restaurantRepository, CoworkerRepository coworkerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.coworkerRepository = coworkerRepository;
    }

    public LiveData<List<Restaurant>> getRestaurantListFirebase() {
        return restaurantRepository.getRestaurantFromFirebase();
    }

    public Task<DocumentSnapshot> getRestaurantRepository() {
        return restaurantRepository.getRestaurantFromFirebase();
    }
}