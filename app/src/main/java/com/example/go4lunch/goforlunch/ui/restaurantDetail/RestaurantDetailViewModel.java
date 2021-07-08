package com.example.go4lunch.goforlunch.ui.restaurantDetail;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class RestaurantDetailViewModel extends BaseViewModel {

    private final String TAG = RestaurantDetailViewModel.class.getSimpleName();

    private final RestaurantRepository restaurantRepository;
    public Restaurant restaurant;

    public final MutableLiveData<List<Coworker>> mCoworkerList = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantLiked = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantPicked = new MutableLiveData<>();

    public RestaurantDetailViewModel(RestaurantRepository restaurantRepository, CoworkerRepository coworkerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.coworkerRepository = coworkerRepository;
        coworker = coworkerRepository.getActualUser();
    }

    public LiveData<Restaurant> getRestaurantDetail(String placeId) {
        return restaurantRepository.getGoogleRestaurantDetail(placeId);
    }

    public void fetchInfoRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        fetchUsersGoing();
        isRestaurantLiked.setValue(checkIfRestaurantIsLiked());
        if (coworker.getCoworkerRestaurantChoice() != null) {
            String uidSelection = coworker.getCoworkerRestaurantChoice().getRestaurantId();
            String restaurantId = restaurant.getRestaurantID();
            if (uidSelection != null)
                isRestaurantPicked.setValue(uidSelection.equals(restaurantId));
        } else {
            isRestaurantPicked.setValue(false);
        }
    }

    public void updateRestaurantLiked(Restaurant restaurant) {
        if (isRestaurantLiked.getValue()) {
            isRestaurantLiked.setValue(false);
            coworkerRepository.removeLikedRestaurant(restaurant.getRestaurantID());
        } else {
            isRestaurantLiked.setValue(true);
            coworkerRepository.addLikedRestaurant(restaurant.getRestaurantID());
        }
    }

    public void updatePickedRestaurant(Restaurant restaurant) {
        if (isRestaurantPicked.getValue()) {
            isRestaurantPicked.setValue(false);
            coworkerRepository.updateRestaurantPicked(null, null, null, coworker.getUid())
                    .addOnCompleteListener(result -> Log.i(TAG, "restaurant unselected"));
        } else {
            isRestaurantPicked.setValue(true);
            coworkerRepository.updateRestaurantPicked(restaurant.getRestaurantID(), restaurant.getName(),
                    restaurant.getAddress(), coworker.getUid())
                    .addOnCompleteListener(result -> Log.i(TAG, "restaurant selected"));
        }
        fetchUsersGoing();
    }

    public void fetchCoworkerLike(Restaurant restaurant) {
        if (coworker != null && coworker.getLikedRestaurants() != null) {
            List<String> likedRestaurant = coworker.getLikedRestaurants();
            String restaurantUid = restaurant.getRestaurantID();
            if (likedRestaurant != null && restaurantUid != null && likedRestaurant.contains(restaurantUid)) {
                isRestaurantLiked.setValue(true);
            }
        }
    }

    private void fetchUsersGoing() {
        List<Coworker> coworkerToAdd = new ArrayList<>();
        coworkerRepository.getAllCoworker()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Coworker coworker = documentSnapshot.toObject(Coworker.class);
                        if (coworker != null && coworker.getCoworkerRestaurantChoice() != null && coworker.getCoworkerRestaurantChoice().getRestaurantId() != null) {
                            String restaurantUid = coworker.getCoworkerRestaurantChoice().getRestaurantId();
                            if (restaurantUid.equals(restaurant.getRestaurantID())) {
                                coworkerToAdd.add(coworker);
                            }
                        }
                    }
                    restaurant.setCoworkersGoingEating(coworkerToAdd);
                    mCoworkerList.setValue(restaurant.getCoworkersEatingHere());
                });
    }

    private boolean checkIfRestaurantIsLiked() {
        List<String> restaurantLiked = coworker.getLikedRestaurants();
        if (restaurantLiked != null && restaurantLiked.size() > 0) {
            for (String uid : restaurantLiked) {
                if (uid.equals(restaurant.getRestaurantID())) return true;
            }
        }
        return false;
    }
}
