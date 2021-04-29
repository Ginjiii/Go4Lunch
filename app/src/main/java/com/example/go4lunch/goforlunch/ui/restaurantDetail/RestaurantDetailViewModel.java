package com.example.go4lunch.goforlunch.ui.restaurantDetail;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

import static com.example.go4lunch.goforlunch.utils.Actions.REMOVED_LIKED;
import static com.example.go4lunch.goforlunch.utils.Actions.REMOVED_PICKED;
import static com.example.go4lunch.goforlunch.utils.Actions.UPDATE_LIKED;
import static com.example.go4lunch.goforlunch.utils.Actions.UPDATE_PICKED;

public class RestaurantDetailViewModel extends ViewModel {

    private final CoworkerRepository coworkerRepository;
    private final RestaurantRepository restaurantRepository;
    private Restaurant restaurant;
    private Coworker coworker;


    public final MutableLiveData<List<Restaurant.CoworkerList>> mCoworkerList = new MutableLiveData<>();
    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantLiked = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantPicked = new MutableLiveData<>();

    public RestaurantDetailViewModel(RestaurantRepository mRestaurantRepository, CoworkerRepository mCoworkerRepository) {
        this.restaurantRepository = mRestaurantRepository;
        this.coworkerRepository = mCoworkerRepository;
        coworker = coworkerRepository.getCoworker();
    }

    public MutableLiveData<Restaurant> getRestaurantDetail(String placeId) {
        return restaurantRepository.getGoogleRestaurantDetail(placeId);
    }

    public void fetchInfoRestaurant() {
        isLoading.setValue(true);
        String uidSelection = restaurantRepository.getRestaurantSelected();
        boolean isRestaurantStored = false;
        if (restaurantRepository.getRestaurantsLoaded() != null) {
            for (Restaurant restaurant : restaurantRepository.getRestaurantsLoaded()) {
                if (restaurant.getRestaurantPlaceId().equals(uidSelection)) {
                    this.restaurant = restaurant;
                    isRestaurantStored = true;
                    break;
                }
            }
        }
        if (isRestaurantStored) {
            fetchCoworkerChoice(restaurant);

        } else {
            getRestaurantDetail(uidSelection);
        }
    }

    public void updateRestaurantLiked(Restaurant restaurant) {
        coworkerRepository.getCoworker(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(doc -> {
            coworker = doc.getResult().toObject(Coworker.class);
            if (isRestaurantLiked != null && isRestaurantLiked.getValue() != null && isRestaurantLiked.getValue()) {
                coworkerRepository.removeLikedRestaurant(restaurant.getRestaurantPlaceId(), coworker)
                        .addOnSuccessListener(onSuccessListener(REMOVED_LIKED, restaurant));
            } else {
                isRestaurantLiked.setValue(true);
                coworkerRepository.addLikedRestaurant(restaurant.getRestaurantPlaceId(), coworker)
                        .addOnSuccessListener(onSuccessListener(UPDATE_LIKED, restaurant));
            }
        });
    }

    public void updatePickedRestaurant(Restaurant restaurant) {
        CoworkerRepository.getCoworker(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(doc -> {
            coworker = doc.getResult().toObject(Coworker.class);
            if (isRestaurantPicked != null && isRestaurantPicked.getValue() != null && isRestaurantPicked.getValue()) {
                coworkerRepository.updateRestaurantPicked(null, null, null, coworker)
                        .addOnSuccessListener(onSuccessListener(REMOVED_PICKED, restaurant));
            } else {
                isRestaurantPicked.setValue(true);
                coworkerRepository.updateRestaurantPicked(restaurant.getRestaurantPlaceId(), restaurant.getRestaurantName(),
                        restaurant.getRestaurantAddress(), coworker)
                        .addOnSuccessListener(onSuccessListener(UPDATE_PICKED, restaurant));
            }
        });
    }

    public void fetchCoworkerLike(Restaurant restaurant)
    {
        coworker = coworkerRepository.getActualUser();
        CoworkerRepository.getCoworker(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(doc -> {
            coworker = doc.getResult().toObject(Coworker.class);
            List<String> likedRestaurant = coworker.getLikedRestaurants();
            String restaurantUid = restaurant.getRestaurantPlaceId();
            if (likedRestaurant != null && restaurantUid != null && likedRestaurant.contains(restaurantUid)) {
                isRestaurantLiked.setValue(true);
            }
        });
        isLoading.setValue(false);
    }

    public void fetchCoworkerChoice(Restaurant restaurant) {
        List<Coworker> userToAdd = new ArrayList<>();
        ArrayList<Restaurant.CoworkerList> coworkerList = new ArrayList<Restaurant.CoworkerList>();
        mCoworkerList.setValue(coworkerList);

        CoworkerRepository.getAllCoworker()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()) {
                        Coworker coworker = documentSnapshot.toObject(Coworker.class);
                        if (coworker != null && coworker.getUid() != null) {
                            String restaurantUid = coworker.getRestaurantUid();
                            if (restaurantUid != null && restaurantUid.equals(restaurant.getRestaurantPlaceId())) {
                                userToAdd.add(coworker);
                                coworkerList.add(new Restaurant.CoworkerList(coworker.getUid(), coworker.getCoworkerName(), coworker.getCoworkerPhotoUrl()));
                            }
                        }
                    }
                    restaurant.setCoworkerChoice(userToAdd);
                    mCoworkerList.setValue(coworkerList);
                    configureInfoRestaurant(restaurant);
                });
    }

    private void configureInfoRestaurant(Restaurant restaurant) {

        coworker = coworkerRepository.getActualUser();
        coworkerRepository.getCoworker(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(doc -> {
            coworker = doc.getResult().toObject(Coworker.class);
            String coworkerRestaurantUid = coworker.getRestaurantUid();
            String restaurantUid = restaurant.getRestaurantPlaceId();

            if (coworkerRestaurantUid != null && coworkerRestaurantUid.equals(restaurantUid)) {
                isRestaurantPicked.setValue(true);
            }
        });
        isLoading.setValue(false);
    }

    private OnSuccessListener<Void> onSuccessListener(final Actions actions, Restaurant restaurant) {
        return aVoid -> {
            switch (actions) {
                case UPDATE_PICKED:
                    Log.d("we step in", "onSuccessListener: ");
                    isRestaurantPicked.setValue(true);
                    fetchCoworkerChoice(restaurant);
                    isLoading.setValue(false);
                    break;
                case REMOVED_PICKED:
                    isRestaurantPicked.setValue(false);
                    fetchCoworkerChoice(restaurant);
                    isLoading.setValue(false);
                    break;
                case UPDATE_LIKED:
                    isRestaurantLiked.setValue(true);
                    isLoading.setValue(false);
                    break;
                case REMOVED_LIKED:
                    isRestaurantLiked.setValue(false);
                    isLoading.setValue(false);
                    break;
            }
        };
    }
}
