package com.example.go4lunch.goforlunch.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.example.go4lunch.goforlunch.service.Go4Lunch.ERROR_ON_FAILURE_LISTENER;
import static com.example.go4lunch.goforlunch.service.Go4Lunch.api;


public class CoworkerRepository {

    /**
     *  Firebase
     */

    private final FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private final CollectionReference coworkerReference = dataBase.collection(String.valueOf(Coworker.Fields.Coworker));
    private final CollectionReference restaurantReference = dataBase.collection(String.valueOf(Restaurant.Fields.Restaurant));
    private DocumentReference coworkerDocumentReference;



    private final MutableLiveData<List<Coworker>> coworkerList = new MutableLiveData<>();
    private List<Coworker> mCoworkerList = new ArrayList<>();
    private final MutableLiveData<Coworker> mCoworker = new MutableLiveData<>();
    private final MutableLiveData<Actions> coworkerSaved = new MutableLiveData<>();
    private final MutableLiveData<Actions> likeStatus = new MutableLiveData<>();
    private final MutableLiveData<Actions> coworkerChoiceStatus = new MutableLiveData<>();

    private static final String TAG = CoworkerRepository.class.getSimpleName();

    public MutableLiveData<List<Coworker>> getCoworkerList() {

        coworkerReference.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mCoworkerList = Objects.requireNonNull(task.getResult()).toObjects(Coworker.class);
                coworkerList.setValue(mCoworkerList);
            } else {
                Log.e(TAG, "getCoworkerList: " + task.getException());
            }
        });

        return coworkerList;
    }

    /**
     * On his first connection, save the user profile in Firestore
     *
     * @param firebaseUser : object: coworker / user
     * @return : mutable live data Enum ActionStatus : information if the backup is done
     * it can be SAVED , EXIST, SAVE_FAILED
     */
    public MutableLiveData<Actions> saveCoworkersFirebaseProfile(FirebaseUser firebaseUser) {

        String url = null;
        if (firebaseUser.getPhotoUrl() != null) {
            url = firebaseUser.getPhotoUrl().toString();
        }

        Coworker coworker = new Coworker(firebaseUser.getUid(), firebaseUser.getDisplayName(),
                firebaseUser.getEmail(), url);

        coworkerReference.document(coworker.getCoworkerId())
                .get()
                .addOnSuccessListener(task -> {
                    if (!task.exists()) {
                        coworkerReference.document(firebaseUser.getUid())
                                .set(coworker)
                                .addOnSuccessListener(documentReference -> {
                                    api.setCoworker(coworker);
                                    coworkerSaved.setValue(Actions.SAVED);
                                })
                                .addOnFailureListener(pE -> coworkerSaved.setValue(Actions.SAVED_FAILED));
                    } else {
                        api.setCoworker(task.toObject(Coworker.class));
                        coworkerSaved.setValue(Actions.EXIST);
                    }
                })
                .addOnFailureListener(pE -> {
                    Log.e(TAG, ERROR_ON_FAILURE_LISTENER + pE);
                    coworkerSaved.setValue(Actions.SAVED_FAILED);
                });
        return coworkerSaved;
    }

    /**
     * Get the coworker information
     * @return : mutable live data object : coworker
     */
    public MutableLiveData<Coworker> getCoworkerData() {
        String coworkerId = api.getCoworkerId();
        coworkerReference.document(coworkerId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Coworker coworker = task.getResult().toObject(Coworker.class);
                        api.setCoworker(coworker);
                        mCoworker.setValue(coworker);
                    } else {
                        Log.e(TAG, "getCoworkerData: " + task.getException());
                    }
                });
        return mCoworker;
    }

    /**
     * Update Firestore with the new username
     * @param coworkerName : string : new user name
     * @return : object ActionStatus : result of the update
     */
    public MutableLiveData<Actions> updateCoworkerName(String coworkerName) {
        Coworker coworker = api.getCoworker();
        coworkerReference.document(coworker.getCoworkerId())
                .update(String.valueOf(Coworker.Fields.coworkerName), coworkerName)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        coworker.setCoworkerName(coworkerName);
                        api.setCoworker(coworker);
                        coworkerSaved.setValue(Actions.SAVED);
                    }
                })
                .addOnFailureListener(pE -> coworkerSaved.setValue(Actions.SAVED_FAILED));
        return coworkerSaved;
    }

    /**
     * Manage if the demand is to be get or save
     * @param actions : enum : status of the action SEARCH or SAVE
     * @return : mutable live data enum actions : result of the action
     */
    public MutableLiveData<Actions> getOrSaveCoworkerLikeRestaurant(Actions actions) {
        Coworker coworker = api.getCoworker();
        Restaurant restaurant = api.getRestaurant();

        if (actions.equals(Actions.TO_SEARCH)) {
            getCoworkerLikeRestaurant(coworker, restaurant, actions);
        } else {
            saveLikeRestaurant(coworker, restaurant, actions);
        }
        return likeStatus;
    }

    /**
     * For the search case, get coworker likes if the restaurant is in
     *
     * @param coworker     : object : coworker who likes
     * @param restaurant   : object : restaurant which is liked
     * @param actions : enum : status of the action SEARCH or SAVE
     */
    public void getCoworkerLikeRestaurant(Coworker coworker, Restaurant restaurant, Actions actions) {
        coworkerReference
                .document(coworker.getCoworkerId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Coworker mCoworker = task.getResult().toObject(Coworker.class);
                        if (mCoworker != null) {
                            api.setCoworker(mCoworker);
                            findRestaurantInLikes(mCoworker, restaurant, actions);
                        } else {
                            likeStatus.setValue(Actions.ERROR);
                        }
                    } else {
                        likeStatus.setValue(Actions.ERROR);
                    }
                })
                .addOnFailureListener(pE -> likeStatus.setValue(Actions.ERROR));
    }

    /**
     * Save the coworker like
     *
     * @param coworker     : object : coworker who likes
     * @param restaurant   : object : restaurant which is liked
     * @param actions : enum : status of the action SEARCH or SAVE
     */
    public void saveLikeRestaurant(Coworker coworker, Restaurant restaurant, Actions actions) {
        coworkerDocumentReference = coworkerReference.document(coworker.getCoworkerId());
        coworkerDocumentReference.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Coworker mCoworker = task.getResult().toObject(Coworker.class);
                        if (mCoworker != null) {
                            findRestaurantInLikes(mCoworker, restaurant, actions);
                        } else {
                            likeStatus.setValue(Actions.ERROR);
                        }
                    } else {
                        likeStatus.setValue(Actions.ERROR);
                    }
                })
                .addOnFailureListener(pE -> likeStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Find in the coworker likes if the restaurant is already in
     * If it's for the search case, mutable live data return IS_CHOSEN or NOT_CHOSEN
     * if it's for the save case, the restaurant is added to the list or removed
     *
     * @param coworker     : object : coworker who likes
     * @param restaurant   : object : restaurant which is liked
     * @param actions : enum : status of the action SEARCH or SAVE
     */
    private void findRestaurantInLikes(Coworker coworker, Restaurant restaurant, Actions actions) {
        boolean isFound = false;
        Coworker.Likes mRestaurant = new Coworker.Likes(restaurant.getRestaurantPlaceId(), restaurant.getRestaurantName());

        if (coworker.getCoworkerLikes() != null) {
            for (Coworker.Likes likes : coworker.getCoworkerLikes()) {
                if (likes.restaurantId.equals(restaurant.getRestaurantPlaceId())) {
                    isFound = true;
                    break;
                }
            }
        }

        switch (actions) {
            case TO_SAVE:
                if (isFound) {
                    removeRestaurantFromLikes(mRestaurant);
                } else {
                    addRestaurantToLikes(mRestaurant);
                }
                break;
            case TO_SEARCH:
                if (isFound) {
                    likeStatus.setValue(Actions.IS_CHOSEN);
                } else {
                    likeStatus.setValue(Actions.NOT_CHOSEN);
                }
                break;
            default:
                likeStatus.setValue(Actions.ERROR);
        }
    }

    /**
     * Add the restaurant in the like list
     * set the mutable live data to ADDED
     *
     * @param restaurant : object : restaurant to add
     */
    private void addRestaurantToLikes(Coworker.Likes restaurant) {

        coworkerDocumentReference.update(String.valueOf(Coworker.Fields.coworkerLikes), FieldValue.arrayUnion(restaurant))
                .addOnSuccessListener(documentReference -> likeStatus.setValue(Actions.ADDED))
                .addOnFailureListener(pE -> likeStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Remove the restaurant from the like list
     * set the mutable live data to REMOVED
     *
     * @param restaurant : object : Coworker.Likes : restaurant informations
     */
    private void removeRestaurantFromLikes(Coworker.Likes restaurant) {

        coworkerDocumentReference.update(String.valueOf(Coworker.Fields.coworkerLikes), FieldValue.arrayRemove(restaurant))
                .addOnSuccessListener(pDocumentReference -> likeStatus.setValue(Actions.REMOVED))
                .addOnFailureListener(pE -> likeStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Manage if the demand is to be get or save
     * @param actions : enum : status of the action SEARCH or SAVE
     * @return : mutable live data enum actionstatus : result of the action
     */
    public MutableLiveData<Actions> getOrSaveCoworkerRestaurantChoice(Actions actions) {

        Coworker coworker = api.getCoworker();
        Restaurant restaurant = api.getRestaurant();

        coworkerDocumentReference = coworkerReference.document(coworker.getCoworkerId());

        if (actions.equals(Actions.TO_SEARCH)) {
            getCoworkerChoice(coworker, restaurant);
        } else {
            saveCoworkerChoice(coworker, restaurant);
        }
        return coworkerChoiceStatus;
    }

    /**
     * Save coworker choice
     *
     * @param coworker   : object : coworker
     * @param restaurant : object : restaurant
     */
    private void saveCoworkerChoice(Coworker coworker, Restaurant restaurant) {
        if (coworker.getCoworkerRestaurantChosen() == null) {
            addChoice(coworker, restaurant);
        } else if (coworker.getCoworkerRestaurantChosen().getRestaurantId().equals(restaurant.getRestaurantPlaceId())) {
            removeChoice(coworker, restaurant);
        } else {
            removeCoworkerFromPreviousRestaurant(coworker, restaurant);
        }
    }

    /**
     * Get the coworker choice
     *
     * @param coworker   : object : coworker
     * @param restaurant : object : restaurant
     */
    private void getCoworkerChoice(Coworker coworker, Restaurant restaurant) {
        coworkerReference.document(coworker.getCoworkerId())
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Coworker mCoworker = task.getResult().toObject(Coworker.class);
                        api.setCoworker(mCoworker);
                        if ((mCoworker != null) && (mCoworker.getCoworkerRestaurantChosen() != null)
                                && (mCoworker.getCoworkerRestaurantChosen().getRestaurantId().equals(restaurant.getRestaurantPlaceId()))) {
                            coworkerChoiceStatus.setValue(Actions.IS_CHOSEN);
                        } else {
                            coworkerChoiceStatus.setValue(Actions.NOT_CHOSEN);
                        }
                    } else {
                        coworkerChoiceStatus.setValue(Actions.NOT_CHOSEN);
                    }
                })
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.ERROR));
    }

    /**
     * Add coworker choice to his informations
     *
     * @param coworker   : object : coworker
     * @param restaurant : object : restaurant chosen
     */
    private void addChoice(Coworker coworker, Restaurant restaurant) {
        Timestamp timestamp = Timestamp.now();

        Coworker.CoworkerRestaurantChoice mRestaurant = new Coworker.CoworkerRestaurantChoice(
                restaurant.getRestaurantPlaceId(), restaurant.getRestaurantName(), timestamp);

        coworkerDocumentReference.update(String.valueOf(Coworker.Fields.coworkerRestaurantChosen), mRestaurant)
                .addOnCompleteListener(task -> {
                    api.setCoworker(coworker);
                    addCoworkerToRestaurant(coworker, restaurant);
                })
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * add the coworker in the restaurant list of coworkers who comes to eat
     *
     * @param coworker   : object : coworker
     * @param restaurant : object : restaurant
     */
    private void addCoworkerToRestaurant(Coworker coworker, Restaurant restaurant) {

        Restaurant.CoworkerList coworkersRestaurantList =
                new Restaurant.CoworkerList(coworker.getCoworkerId(), coworker.getCoworkerName(), coworker.getCoworkerPhotoUrl());

        restaurantReference.document(restaurant.getRestaurantPlaceId())
                .update(String.valueOf(Restaurant.Fields.restaurantCoworkerList), FieldValue.arrayUnion(coworkersRestaurantList))
                .addOnSuccessListener(pDocumentReference -> coworkerChoiceStatus.setValue(Actions.ADDED))
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Remove the coworker choice
     *
     * @param coworker   : object : coworker
     * @param restaurant : object : restaurant
     */
    private void removeChoice(Coworker coworker, Restaurant restaurant) {
        coworker.setCoworkerRestaurantChosen(null);
        api.setCoworker(coworker);
        coworkerDocumentReference.update(String.valueOf(Coworker.Fields.coworkerRestaurantChosen), FieldValue.delete())
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) removeCoworkerFromRestaurant(coworker, restaurant);
                })
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Remove the coworker from the restaurant list of coworker who comes to eat
     *
     * @param coworker   : object: coworker
     * @param restaurant : object : restaurant
     */
    private void removeCoworkerFromRestaurant(Coworker coworker, Restaurant restaurant) {

        Restaurant.CoworkerList coworkersRestaurantList =
                new Restaurant.CoworkerList(coworker.getCoworkerId(), coworker.getCoworkerName(), coworker.getCoworkerPhotoUrl());

        restaurantReference.document(restaurant.getRestaurantPlaceId())
                .update(String.valueOf(Restaurant.Fields.restaurantCoworkerList), FieldValue.arrayRemove(coworkersRestaurantList))
                .addOnSuccessListener(pDocumentReference -> coworkerChoiceStatus.setValue(Actions.REMOVED))
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * If the coworker has already made a choice which is not this restaurant
     * The coworker is removed from his previous restaurant coworkers list
     *
     * @param coworker   : object : coworker
     * @param restaurant :object : restaurant
     */
    private void removeCoworkerFromPreviousRestaurant(Coworker coworker, Restaurant restaurant) {

        Restaurant.CoworkerList coworkersRestaurantList =
                new Restaurant.CoworkerList(coworker.getCoworkerId(), coworker.getCoworkerName(), coworker.getCoworkerPhotoUrl());

        restaurantReference.document(coworker.getCoworkerRestaurantChosen().getRestaurantId())
                .update(String.valueOf(Restaurant.Fields.restaurantCoworkerList), FieldValue.arrayRemove(coworkersRestaurantList))
                .addOnSuccessListener(documentReference -> addChoice(coworker, restaurant))
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

}
