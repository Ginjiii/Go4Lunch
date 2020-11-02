package com.example.go4lunch.goforlunch.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import com.example.go4lunch.goforlunch.models.User;

public class UserHelper {


    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---
    public static CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---
    public static Task<Void> createUser(String userId, String username, String urlPicture, String placeId, ArrayList<String> like, int currentTime) {
        //Create UserObject
        User userToCreate = new User(userId, username, urlPicture, placeId, like, currentTime);
        //Add it in Firestore
        return UserHelper.getUsersCollection()
                .document(userId) //Setting userID for Document
                .set(userToCreate);//Setting object for Document
    }

    // --- GET ---
    public static Task<DocumentSnapshot> getUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---
    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> updatePlaceId(String uid, String placeId, int currentTime) {
        return UserHelper.getUsersCollection().document(uid).update("placeId", placeId, "currentTime", currentTime);
    }

    public static Task<Void> updateLike(String uid, String placeId) {
        return UserHelper.getUsersCollection().document(uid).update("like", FieldValue.arrayUnion(placeId));
    }

    // --- DELETE ---
    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

    public static Task<Void> deletePlaceId(String uid) {
        return UserHelper.getUsersCollection().document(uid).update("placeId", null);
    }

    public static Task<Void> deleteLike(String uid, String placeId) {
        return UserHelper.getUsersCollection().document(uid).update("like", FieldValue.arrayRemove(placeId));
    }
}
