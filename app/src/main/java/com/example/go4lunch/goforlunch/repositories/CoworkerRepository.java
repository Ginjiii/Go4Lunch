package com.example.go4lunch.goforlunch.repositories;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class CoworkerRepository {

    private static final String COLLECTION_NAME = "coworker";
    private CollectionReference coworkerCollection;
    private Coworker coworker;

    private static volatile CoworkerRepository INSTANCE;

    public static CoworkerRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new CoworkerRepository();
        }
        return INSTANCE;
    }

    public CoworkerRepository(){
        //this.coworkerCollection = getUsersCollection();

    }

    public Coworker getActualUser  (){
        return coworker;
    }
    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public  Task<Void> createCoworker(String uid, String username, String urlPicture) {
        Coworker userToCreate = new Coworker(uid, username, urlPicture);
        this.coworker = userToCreate;
        return CoworkerRepository.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getCoworker(String uid){
        return CoworkerRepository.getUsersCollection().document(uid).get();
    }

    // --- GET ---

    public static Task<QuerySnapshot> getAllCoworker(){
        return CoworkerRepository.getUsersCollection().get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return CoworkerRepository.getUsersCollection().document(uid).update("username", username);
    }


    // --- DELETE ---

    public static Task<Void> deleteCoworker(String uid) {
        return CoworkerRepository.getUsersCollection().document(uid).delete();
    }

    public void updateCoworkerRepository( Coworker coworker) {
        this.coworker = coworker;
    }
}