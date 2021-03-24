package com.example.go4lunch.goforlunch.ui.coworker;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.utils.Event;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CoworkerViewModel extends ViewModel {

    private RestaurantRepository restaurantRepository;
    private CoworkerRepository coworkerRepository;
    protected Coworker coworker;

    private MutableLiveData<List<Coworker>> coworkers = new MutableLiveData<>();
    private MutableLiveData<Event<Object>> openDetailRestaurant = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Event<Object>> getOpenDetailRestaurant() {
        return openDetailRestaurant;
    }

    public LiveData<List<Coworker>> getCoworkers() { return coworkers; }

    public CoworkerViewModel(CoworkerRepository mCoworkerRepository, RestaurantRepository mRestaurantRepository) {
        this.coworkerRepository = mCoworkerRepository;
        this.restaurantRepository = mRestaurantRepository;
    }

    void fetchListUsersFromFirebase() {
        CoworkerRepository.getAllCoworker().addOnSuccessListener(queryDocumentSnapshots -> {
            List<Coworker> fetchedUsers = new ArrayList<>();
            Log.d("FireBAse", "fetchListUsersFromFirebase: "+queryDocumentSnapshots.getDocuments().size());
            for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                Coworker userFetched = documentSnapshot.toObject(Coworker.class);
                fetchedUsers.add(userFetched);
            }
            coworkers.setValue(fetchedUsers);
            isLoading.setValue(false);
        })
                .addOnFailureListener(this.onFailureListener());
    }

    private OnFailureListener onFailureListener() {
        return e -> {
            isLoading.setValue(false);
        };
    }

    public void onRefreshUserList(){
        isLoading.setValue(true);
        this.fetchListUsersFromFirebase();
    }

    public void updateRestaurantToDisplay(Coworker coworker) {
        String uidRestaurant = coworker.getUid();
        if (uidRestaurant != null) {
            restaurantRepository.setRestaurantSelected(uidRestaurant);
            openDetailRestaurant.setValue(new Event<>(new Object()));
        }
    }
}