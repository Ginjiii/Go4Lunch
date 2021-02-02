package com.example.go4lunch.goforlunch.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.DI.DI;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.service.Api;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CoworkerRepository {

    private static final String TAG = "TAG_REPO_COWORKER";

    private final FirebaseFirestore myDb = FirebaseFirestore.getInstance();
    private final CollectionReference coworkerRef = myDb.collection(String.valueOf(Coworker.Fields.Coworker));
    private final CollectionReference restaurantRef = myDb.collection(String.valueOf(Restaurant.Fields.Restaurant));
    private DocumentReference coworkerDocumentRef;
    public static final Api api = DI.getGo4LunchApiService();

    private final MutableLiveData<List<Coworker>> coworkerList = new MutableLiveData<>();
    private final MutableLiveData<Coworker> coworker = new MutableLiveData<>();

    private final MutableLiveData<Actions> coworkerSaved = new MutableLiveData<>();
    private final MutableLiveData<Actions> likeStatus = new MutableLiveData<>();
    private List<Coworker> mCoworkerList = new ArrayList<>();
    private final MutableLiveData<Actions> coworkerChoiceStatus = new MutableLiveData<>();

    /**
     * Get the coworker list
     *
     * @return : mutable live data list object : coworker list
     */
    public MutableLiveData<List<Coworker>> getCoworkerListData() {
        coworkerRef
                .get()
                .addOnCompleteListener(pTask -> {
                    if (pTask.isSuccessful()) {
                        mCoworkerList = Objects.requireNonNull(pTask.getResult()).toObjects(Coworker.class);
                        coworkerList.setValue(mCoworkerList);
                    } else {
                        Log.e(TAG, "getCoworkerListData: " + pTask.getException());
                    }
                });
        return coworkerList;
    }

    /**
     * Get the coworker informations
     * @return : mutable live data object : coworker
     */
    public MutableLiveData<Coworker> getCoworkerData() {
        String coworkerId = api.getCoworkerId();
        coworkerRef.document(coworkerId)
                .get()
                .addOnCompleteListener(pTask -> {
                    if (pTask.isSuccessful()) {
                        Coworker mCoworker = pTask.getResult().toObject(Coworker.class);
                        api.setCoworker(mCoworker);
                        coworker.setValue(mCoworker);
                    } else {
                        Log.e(TAG, "getCoworkerData: " + pTask.getException());
                    }
                });
        return coworker;
    }
    /**
     * Update Firestore with the new username
     newUserName     * @return : object ActionStatus : result of the update
     */
    public MutableLiveData<Actions> updateCoworkerUserName(String newUserName) {
        Coworker coworker = api.getCoworker();
        coworkerRef.document(coworker.getCoworkerId())
                .update(String.valueOf(Coworker.Fields.coworkerName), newUserName)
                .addOnCompleteListener(pTask -> {
                    if (pTask.isSuccessful()) {
                        coworker.setCoworkerName(newUserName);
                        api.setCoworker(coworker);
                        coworkerSaved.setValue(Actions.SAVED);
                    }
                })
                .addOnFailureListener(pE -> coworkerSaved.setValue(Actions.SAVED_FAILED));
        return coworkerSaved;
    }

    /**
     * Manage if the demand is to be get or save
     * @param pActionStatus : enum : status of the action SEARCH or SAVE
     * @return : mutable live data enum actionstatus : result of the action
     */
    public MutableLiveData<Actions> getCoworkerLikeForRestaurant(Actions pActionStatus) {
        Coworker coworker = api.getCoworker();
        Restaurant lRestaurant = api.getRestaurant();

        if (pActionStatus.equals(Actions.TO_SEARCH)) {
            getCoworkerLikeForRestaurant(coworker, lRestaurant, pActionStatus);
        } else {
            saveLikeRestaurant(coworker, lRestaurant, pActionStatus);
        }
        return likeStatus;
    }

    /**
     * For the search case, get workmate likes if the restaurant is in
     *
     * @param coworker     : object : workmate who likes
     * @param restaurant   : object : restaurant which is liked
     * @param actions : enum : status of the action SEARCH or SAVE
     */
    public void getCoworkerLikeForRestaurant(Coworker coworker, Restaurant restaurant, Actions actions) {
        coworkerRef
                .document(coworker.getCoworkerId())
                .get()
                .addOnCompleteListener(pTask -> {
                    if (pTask.isSuccessful()) {
                        Coworker lWorkmate = pTask.getResult().toObject(Coworker.class);
                        if (lWorkmate != null) {
                            api.setCoworker(lWorkmate);
                            findRestaurantInLikes(coworker, restaurant, actions);
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
     * Save the workmate like
     *
     * @param coworker     : object : workmate who likes
     * @param restaurant   : object : restaurant which is liked
     * @param actions : enum : status of the action SEARCH or SAVE
     */
    public void saveLikeRestaurant(Coworker coworker, Restaurant restaurant, Actions actions) {
        coworkerDocumentRef = coworkerRef.document(coworker.getCoworkerId());
        coworkerDocumentRef.get()
                .addOnCompleteListener(pTask -> {
                    if (pTask.isSuccessful()) {
                        Coworker mCoworker = pTask.getResult().toObject(Coworker.class);
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
     * Find in the workmate likes if the restaurant is already in
     * If it's for the search case, mutable live data return IS_CHOSEN or NOT_CHOSEN
     * if it's for the save case, the restaurant is added to the list or removed
     *
     * @param coworker     : object : workmate who likes
     * @param restaurant   : object : restaurant which is liked
     * @param actions : enum : status of the action SEARCH or SAVE
     */
    private void findRestaurantInLikes(Coworker coworker, Restaurant restaurant, Actions actions) {
        boolean lIsFound = false;
        Coworker.Likes lRestaurant = new Coworker.Likes(restaurant.getRestaurantPlaceId(), restaurant.getRestaurantName());

        if (coworker.getCoworkerLikes() != null) {
            for (Coworker.Likes lLikes : coworker.getCoworkerLikes()) {
                if (lLikes.restaurantId.equals(restaurant.getRestaurantPlaceId())) {
                    lIsFound = true;
                    break;
                }
            }
        }

        switch (actions) {
            case TO_SAVE:
                if (lIsFound) {
                    removeRestaurantFromLikes(lRestaurant);
                } else {
                    addRestaurantToLikes(lRestaurant);
                }
                break;
            case TO_SEARCH:
                if (lIsFound) {
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

        coworkerDocumentRef.update(String.valueOf(Coworker.Fields.coworkerLikes), FieldValue.arrayUnion(restaurant))
                .addOnSuccessListener(pDocumentReference -> likeStatus.setValue(Actions.ADDED))
                .addOnFailureListener(pE -> likeStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Remove the restaurant from the like list
     * set the mutable live data to REMOVED
     *
     * @param restaurant : object : Workmate.Likes : restaurant informations
     */
    private void removeRestaurantFromLikes(Coworker.Likes restaurant) {

        coworkerDocumentRef.update(String.valueOf(Coworker.Fields.coworkerLikes), FieldValue.arrayRemove(restaurant))
                .addOnSuccessListener(pDocumentReference -> likeStatus.setValue(Actions.REMOVED))
                .addOnFailureListener(pE -> likeStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Manage if the demand is to be get or save
     * @param actions : enum : status of the action SEARCH or SAVE
     * @return : mutable live data enum actions : result of the action
     */
    public MutableLiveData<Actions> getCoworkerRestaurantChoice(Actions actions) {

        Coworker coworker = api.getCoworker();
        Restaurant restaurant = api.getRestaurant();

        coworkerDocumentRef = coworkerRef.document(coworker.getCoworkerId());

        if (actions.equals(Actions.TO_SEARCH)) {
            getCoworkerChoice(coworker, restaurant);
        } else {
            saveCoworkerChoice(coworker, restaurant);
        }
        return coworkerChoiceStatus;
    }

    /**
     * Save workmate choice
     *
     * @param coworker   : object : workmate
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
     * Add workmate choice to his informations
     *
     * @param coworker   : object : workmate
     * @param restaurant : object : restaurant chosen
     */
    private void addChoice(Coworker coworker, Restaurant restaurant) {
        Timestamp lTimestamp = Timestamp.now();

        Coworker.CoworkerRestaurantChoice lRestaurant = new Coworker.CoworkerRestaurantChoice(
                restaurant.getRestaurantPlaceId(), restaurant.getRestaurantName(), lTimestamp);

        coworkerDocumentRef.update(String.valueOf(Coworker.Fields.coworkerRestaurantChosen), lRestaurant)
                .addOnCompleteListener(pTask -> {
                    api.setCoworker(coworker);
                    addCoworkerToRestaurant(coworker, restaurant);
                })
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * add the workmate in the restaurant list of workmates who comes to eat
     *
     * @param coworker   : object : workmate
     * @param restaurant : object : restaurant
     */
    private void addCoworkerToRestaurant(Coworker coworker, Restaurant restaurant) {

        Restaurant.CoworkerList lWorkmatesInRestoList =
                new Restaurant.CoworkerList(coworker.getCoworkerId(), coworker.getCoworkerName(), coworker.getCoworkerPhotoUrl());

        restaurantRef.document(restaurant.getRestaurantPlaceId())
                .update(String.valueOf(Restaurant.Fields.restaurantWkList), FieldValue.arrayUnion(lWorkmatesInRestoList))
                .addOnSuccessListener(pDocumentReference -> coworkerChoiceStatus.setValue(Actions.ADDED))
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Remove the workmate choice
     *
     * @param coworker   : object : workmate
     * @param restaurant : object : restaurant
     */
    private void removeChoice(Coworker coworker, Restaurant restaurant) {
        coworker.setCoworkerRestaurantChosen(null);
        api.setCoworker(coworker);
        coworkerDocumentRef.update(String.valueOf(Coworker.Fields.coworkerRestaurantChosen), FieldValue.delete())
                .addOnCompleteListener(pTask -> {
                    if (pTask.isSuccessful()) removeCoworkerFromRestaurant(coworker, restaurant);
                })
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Remove the workmate from the restaurant list of workmates who comes to eat
     *
     * @param coworker   : object: workmate
     * @param pRestaurant : object : restaurant
     */
    private void removeCoworkerFromRestaurant(Coworker coworker, Restaurant pRestaurant) {

        Restaurant.CoworkerList lWorkmatesInRestoList =
                new Restaurant.CoworkerList(coworker.getCoworkerId(), coworker.getCoworkerName(), coworker.getCoworkerPhotoUrl());

        restaurantRef.document(pRestaurant.getRestaurantPlaceId())
                .update(String.valueOf(Restaurant.Fields.restaurantWkList), FieldValue.arrayRemove(lWorkmatesInRestoList))
                .addOnSuccessListener(pDocumentReference -> coworkerChoiceStatus.setValue(Actions.REMOVED))
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * If the workmate has already made a choice which is not this restaurant
     * The workmate is removed from his previous restaurant workmates list
     *
     * @param coworker   : object : workmate
     * @param pRestaurant :object : restaurant
     */
    private void removeCoworkerFromPreviousRestaurant(Coworker coworker, Restaurant pRestaurant) {

        Restaurant.CoworkerList lWorkmatesInRestoList =
                new Restaurant.CoworkerList(coworker.getCoworkerId(), coworker.getCoworkerName(), coworker.getCoworkerPhotoUrl());

        restaurantRef.document(coworker.getCoworkerRestaurantChosen().getRestaurantId())
                .update(String.valueOf(Restaurant.Fields.restaurantWkList), FieldValue.arrayRemove(lWorkmatesInRestoList))
                .addOnSuccessListener(pDocumentReference -> addChoice(coworker, pRestaurant))
                .addOnFailureListener(pE -> coworkerChoiceStatus.setValue(Actions.SAVED_FAILED));
    }

    /**
     * Get the workmate choice
     *
     * @param coworker   : object : workmate
     * @param pRestaurant : object : restaurant
     */
    private void getCoworkerChoice(Coworker coworker, Restaurant pRestaurant) {
        restaurantRef.document(coworker.getCoworkerId())
                .get()
                .addOnCompleteListener(pTask -> {
                    if (pTask.isSuccessful()) {
                        Coworker mCoworker = pTask.getResult().toObject(Coworker.class);
                        api.setCoworker(mCoworker);
                        if ((mCoworker != null) && (mCoworker.getCoworkerRestaurantChosen() != null)
                                && (mCoworker.getCoworkerRestaurantChosen().getRestaurantId().equals(pRestaurant.getRestaurantPlaceId()))) {
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
}
