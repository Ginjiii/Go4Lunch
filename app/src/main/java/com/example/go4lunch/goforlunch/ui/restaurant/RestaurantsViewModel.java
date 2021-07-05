package com.example.go4lunch.goforlunch.ui.restaurant;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantsViewModel extends BaseViewModel {

    public RestaurantsViewModel(RestaurantRepository restaurantRepository, CoworkerRepository coworkerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.coworkerRepository = coworkerRepository;
    }
    public final MutableLiveData<List<Restaurant>> restaurantsLiveData = new MutableLiveData<>();

    /**
     * Get restaurant list.
     *
     * @return restaurant list.
     */
    public LiveData<List<Restaurant>> getRestaurantListFirebase() {
        List<Restaurant> restaurantToAdd = new ArrayList<>();

        restaurantRepository.getRestaurantFromFirebase().addOnSuccessListener( queryDocumentSnapshots -> {
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                Restaurant restaurant = documentSnapshot.toObject(Restaurant.class);
                restaurantToAdd.add(restaurant);
            }
            restaurantsLiveData.setValue(restaurantToAdd);
        });

        return restaurantsLiveData;
    }
}