package com.example.go4lunch.goforlunch.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CoworkerRepository {

    private static final String COLLECTION_NAME = "coworker";
    private CollectionReference coworkerCollection;
    private DocumentReference coworkerDocumentReference;

    private Coworker coworker;
    private final MutableLiveData<Actions> coworkerChoiceStatus = new MutableLiveData<>();
    private Coworker.CoworkerRestaurantChoice coworkerRestaurantChoice;

    private static volatile CoworkerRepository INSTANCE;

    public static CoworkerRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CoworkerRepository();
        }
        return INSTANCE;
    }

    public CoworkerRepository() {
        this.coworkerCollection = getUsersCollection();

    }

    public Coworker getActualUser() {
        return coworker;
    }
    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public Task<Void> createCoworker(String uid, String username, String urlPicture) {
        Coworker userToCreate = new Coworker(uid, username, urlPicture);
        this.coworker = userToCreate;
        return CoworkerRepository.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public Coworker getCoworker() {
        return coworker;
    }

    public static Task<DocumentSnapshot> getCoworker(String uid) {
        return CoworkerRepository.getUsersCollection().document(uid).get();
    }

    // --- GET ---

    public static Task<QuerySnapshot> getAllCoworker() {
        return CoworkerRepository.getUsersCollection().get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return CoworkerRepository.getUsersCollection().document(uid).update("username", username);
    }

    public Task<Void> updateRestaurantPicked(String mRestaurantId, String restaurantName, String restaurantAddress, Coworker coworker) {
        Log.d("updateRestaurantPicked", "updateRestaurantPicked: pickedup3");
        if (coworker == null) {
            coworker = getActualUser();
        }

        if (coworker.getCoworkerRestaurantChosen() == null) {
            coworkerRestaurantChoice = new Coworker.CoworkerRestaurantChoice();
        } else {
            coworkerRestaurantChoice = coworker.getCoworkerRestaurantChosen();
        }
        coworkerRestaurantChoice.setRestaurantId(mRestaurantId);
        coworkerRestaurantChoice.setRestaurantName(restaurantName);
        coworkerRestaurantChoice.setRestaurantAddress(restaurantAddress);
        coworker.setCoworkerRestaurantChosen(coworkerRestaurantChoice);
        coworkerCollection.document(coworker.getUid()).update(
                "restaurantUid", mRestaurantId,
                "restaurantName", restaurantName,
                "restaurantAddress", restaurantAddress, "coworkerRestaurantChosen", coworker.getCoworkerRestaurantChosen());

        return coworkerCollection.document(coworker.getUid()).update(
                "restaurantUid", mRestaurantId,
                "restaurantName", restaurantName,
                "restaurantAddress", restaurantAddress, "coworkerRestaurantChosen", coworker.getCoworkerRestaurantChosen());
    }

    public Task<Void> updateUrlPicture(String urlPicture, String uid){
        coworker.setCoworkerPhotoUrl(urlPicture);
        return coworkerCollection.document(uid).update("urlPicture", urlPicture);
    }

    public Task<Void> addLikedRestaurant(String likedRestaurant, Coworker coworker) {
        if (coworker == null) {
            coworker = getActualUser();
        }
        coworker.addLikedRestaurant(likedRestaurant);
        return updateLikedRestaurants(coworker);
    }

    public Task<Void> removeLikedRestaurant(String likedRestaurant, Coworker coworker) {
        if (coworker == null) {
            coworker = getActualUser();
        }
        coworker.removeLikedRestaurant(likedRestaurant);
        return updateLikedRestaurants(coworker);
    }

    private Task<Void> updateLikedRestaurants(Coworker coworker) {
        List<String> likedRestaurantsList = coworker.getLikedRestaurants();
        return coworkerCollection.document(coworker.getUid()).update("likedRestaurants", likedRestaurantsList);
    }

    // --- DELETE ---

    public static Task<Void> deleteCoworker(String uid) {
        return CoworkerRepository.getUsersCollection().document(uid).delete();
    }

    public void updateCoworkerRepository(Coworker coworker) {
        this.coworker = coworker;
    }
}