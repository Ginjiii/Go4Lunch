package com.example.go4lunch.goforlunch.repositories;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.CoworkerRestaurantChoice;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class CoworkerRepository {

    private static final String COLLECTION_NAME = "coworker";
    private final CollectionReference coworkerCollection;

    private Coworker coworker;

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

    private CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public Task<Void> createCoworker(Coworker coworker) {
        this.coworker = coworker;
        return coworkerCollection.document(coworker.getUid()).set(coworker);
    }

    public Task<DocumentSnapshot> getCoworkerFromFirebase(String uid) {
        return coworkerCollection.document(uid).get();
    }

    public Task<QuerySnapshot> getAllCoworker() {
        return coworkerCollection.get();
    }

    public Task<Void> updateRestaurantPicked(String id, String name, String address, String userUid) {
        CoworkerRestaurantChoice choice = new CoworkerRestaurantChoice(id, name, address, Timestamp.now());
        coworker.setCoworkerRestaurantChoice(choice);
        CoworkerRestaurantChoice choiceToCreate = (id != null) ? choice : null;
        return coworkerCollection.document(userUid).update("coworkerRestaurantChoice", choiceToCreate);
    }

    public Task<Void> addLikedRestaurant(String likedRestaurant) {
        coworker.addLikedRestaurant(likedRestaurant);
        return updateLikedRestaurants(coworker.getUid());
    }

    public Task<Void> removeLikedRestaurant(String likedRestaurant) {
        coworker.removeLikedRestaurant(likedRestaurant);
        return updateLikedRestaurants(coworker.getUid());
    }

    private Task<Void> updateLikedRestaurants(String uid) {
        List<String> likedRestaurantsList = coworker.getLikedRestaurants();
        return coworkerCollection.document(uid).update("likedRestaurants", likedRestaurantsList);
    }

    public void updateCurrentUser(Coworker coworker) {
        this.coworker = coworker;
    }
}