package com.example.go4lunch.goforlunch.ui.coworker;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CoworkerViewModel extends BaseViewModel {


    private MutableLiveData<List<Coworker>> coworkers = new MutableLiveData<>();

    public LiveData<List<Coworker>> getCoworkers() {
        return coworkers;
    }

    public CoworkerViewModel(CoworkerRepository coworkerRepository, RestaurantRepository restaurantRepository) {
        this.coworkerRepository = coworkerRepository;
        this.restaurantRepository = restaurantRepository;
        coworker = coworkerRepository.getActualUser();
    }

    void fetchListUsersFromFirebase() {
        coworkerRepository.getAllCoworker().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Coworker> fetchedUsers = new ArrayList<>();
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                Coworker userFetched = documentSnapshot.toObject(Coworker.class);
                fetchedUsers.add(userFetched);
            }
            coworkers.setValue(fetchedUsers);
        });
    }
}