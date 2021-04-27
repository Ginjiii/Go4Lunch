package com.example.go4lunch.goforlunch.ui.maps;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class MapsViewModel extends ViewModel {

    private final RestaurantRepository repository ;
    private final CoworkerRepository coworkersRepository;


    public MapsViewModel(RestaurantRepository restaurantRepository, CoworkerRepository coworkerRepository) {
        repository = restaurantRepository ;
        coworkersRepository = coworkerRepository;
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