package com.example.go4lunch.goforlunch.ui;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainViewModel extends ViewModel {

    private final CoworkerRepository coworkerRepository;
    public MutableLiveData<String> selectedRestaurantId = new MutableLiveData<>();

    public MainViewModel(CoworkerRepository coworkerRepository) {
        this.coworkerRepository = coworkerRepository;
    }

    public void getSelectedRestaurant() {
        String uid = (getCurrentUser() != null) ? getCurrentUser().getUid() : "default";
        coworkerRepository.getCoworkerFromFirebase(uid)
                .addOnSuccessListener(documentSnapshot -> {
                    Coworker coworker = documentSnapshot.toObject(Coworker.class);
                    if (coworker != null && coworker.getCoworkerRestaurantChoice() != null) {
                        selectedRestaurantId.setValue(coworker.getCoworkerRestaurantChoice().getRestaurantId());
                    }
                });
    }

    private FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
