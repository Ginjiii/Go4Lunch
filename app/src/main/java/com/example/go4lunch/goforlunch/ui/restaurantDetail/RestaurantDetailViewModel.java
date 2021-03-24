package com.example.go4lunch.goforlunch.ui.restaurantDetail;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.google.android.gms.tasks.OnFailureListener;
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

    private final CoworkerRepository coworkerRepository ;
    private final RestaurantRepository restaurantRepository ;
    private Coworker coworker;


    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantLiked = new MutableLiveData<>();
    public MutableLiveData<Boolean> isRestaurantPicked = new MutableLiveData<>();

    public RestaurantDetailViewModel(RestaurantRepository mRestaurantRepository, CoworkerRepository mCoworkerRepository) {
        restaurantRepository = mRestaurantRepository;
        coworkerRepository = mCoworkerRepository;
        coworker = coworkerRepository.getActualUser();
    }

    public MutableLiveData<Restaurant> getRestaurantDetail(String placeId) {
        return restaurantRepository.getGoogleRestaurantDetail(placeId);
    }

    public void updateRestaurantLiked(Restaurant restaurant) {
        isLoading.setValue(true);
        if(isRestaurantLiked.getValue()){
            coworkerRepository.removeLikedRestaurant(restaurant.getRestaurantPlaceId(), coworker.getUid())
                    .addOnSuccessListener(onSuccessListener(REMOVED_LIKED, restaurant))
                    .addOnFailureListener(this.onFailureListener(UPDATE_LIKED));
        } else {
            coworkerRepository.addLikedRestaurant(restaurant.getRestaurantPlaceId(), coworker.getUid())
                    .addOnSuccessListener(onSuccessListener(UPDATE_LIKED, restaurant))
                    .addOnFailureListener(this.onFailureListener(UPDATE_LIKED));
        }
    }

    public void updatePickedRestaurant(Restaurant res) {
        coworkerRepository.getCoworker(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(doc -> {
            coworker = doc.getResult().toObject(Coworker.class);
            if(isRestaurantPicked != null && isRestaurantPicked.getValue() != null && isRestaurantPicked.getValue()){
                coworkerRepository.updateRestaurantPicked(null, null, null, coworker)
                        .addOnSuccessListener(onSuccessListener(REMOVED_PICKED, res))
                        .addOnFailureListener(this.onFailureListener(UPDATE_PICKED));
            } else {
                isRestaurantPicked.setValue(true);
                coworkerRepository.updateRestaurantPicked(res.getRestaurantPlaceId(), res.getRestaurantName(),
                        res.getRestaurantAddress(), coworker)
                        .addOnSuccessListener(onSuccessListener(UPDATE_PICKED, res))
                        .addOnFailureListener(this.onFailureListener(UPDATE_PICKED));
            }
        });
    }

    private void fetchCoworkerChoice(Restaurant restaurant) {
        List<Coworker> userToAdd = new ArrayList<>();
        CoworkerRepository.getAllCoworker()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                        Coworker coworker = documentSnapshot.toObject(Coworker.class);
                        if(coworker != null && coworker.getUid() != null){
                            String restaurantUid = coworker.getUid();
                            if(restaurantUid.equals(restaurant.getRestaurantPlaceId())){
                                userToAdd.add(coworker);
                            }
                        }
                    }
                    restaurant.setCoworkerChoice(userToAdd);
                    configureInfoRestaurant(restaurant);
                });
    }

    private void configureInfoRestaurant(Restaurant restaurant){
        isRestaurantLiked.setValue(checkIfRestaurantIsLiked(restaurant));
        if(coworker.getUid() != null) {
            isRestaurantPicked.setValue(coworker.getUid().equals(restaurant.getRestaurantPlaceId()));
        } else {
            isRestaurantPicked.setValue(false);
        }
        isLoading.setValue(false);
    }

    public Boolean checkIfRestaurantIsLiked(Restaurant restaurant){
        List<String> restaurantLiked = coworker.getCoworkerLikes();
        if(restaurantLiked != null && restaurantLiked.size() > 0) {
            for (String uid : restaurantLiked) {
                if (uid.equals(restaurant.getRestaurantPlaceId())) return true;
            }
        }
        return false;
    }

    public MutableLiveData<Boolean> checkIfRestaurantIsChosen(Restaurant restaurant){
//        coworker = coworkerRepository.getActualUser();
//        Coworker.CoworkerRestaurantChoice restaurantChoosen = coworker.getCoworkerRestaurantChosen();
//        if(restaurantChoosen != null) {
//            if (restaurantChoosen.getRestaurantId().equals(restaurant.getRestaurantPlaceId())) return true;
//        }
//        return false;
        coworkerRepository.getCoworker(FirebaseAuth.getInstance().getCurrentUser().getUid()).addOnCompleteListener(doc -> {
            coworker = doc.getResult().toObject(Coworker.class);
            if(isRestaurantPicked != null && isRestaurantPicked.getValue() != null && isRestaurantPicked.getValue()){
                coworkerRepository.updateRestaurantPicked(null, null, null, coworker)
                        .addOnSuccessListener(onSuccessListener(REMOVED_PICKED, restaurant))
                        .addOnFailureListener(this.onFailureListener(UPDATE_PICKED));
            } else {
                isRestaurantPicked.setValue(true);
                coworkerRepository.updateRestaurantPicked(restaurant.getRestaurantPlaceId(), restaurant.getRestaurantName(),
                        restaurant.getRestaurantAddress(), coworker)
                        .addOnSuccessListener(onSuccessListener(UPDATE_PICKED, restaurant))
                        .addOnFailureListener(this.onFailureListener(UPDATE_PICKED));
            }
        });
return isRestaurantPicked;
    }

    private OnSuccessListener<Void> onSuccessListener(final Actions actions, Restaurant restaurant){
        return aVoid -> {
            switch (actions){
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

    public OnFailureListener onFailureListener(Actions actions){
        return e -> {
            isLoading.setValue(false);
        };
    }
}
