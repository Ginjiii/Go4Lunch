package com.example.go4lunch.goforlunch.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class BaseViewModel extends ViewModel {

    public final MutableLiveData<List<String>> coworkersIdMutableLiveData = new MutableLiveData<>();

    protected Coworker coworker;
    protected CoworkerRepository coworkerRepository;
    protected RestaurantRepository restaurantRepository;

    /**
     * Get restaurant list.
     *
     * @return restaurant list.
     */
    public LiveData<List<Restaurant>> getRestaurantList(double latitude, double longitude) {
        return restaurantRepository.getGoogleRestaurantList(latitude, longitude);
    }

    public LiveData<List<Restaurant>> getRestaurants() {
        return restaurantRepository.restaurantListMutableLiveData;
    }


    /**
     * Get going coworkers.
     */
    public void fetchCoworkersGoing() {
        List<String> coworkerToAdd = new ArrayList<>();
        coworkerRepository.getAllCoworker()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Coworker coworker = documentSnapshot.toObject(Coworker.class);
                        if (coworker != null && coworker.getCoworkerRestaurantChoice() != null && coworker.getCoworkerRestaurantChoice().getRestaurantId() != null) {
                            String restaurantUid = coworker.getCoworkerRestaurantChoice().getRestaurantId();
                            coworkerToAdd.add(restaurantUid);
                        }
                    }
                    coworkersIdMutableLiveData.setValue(coworkerToAdd);
                });
    }
}
