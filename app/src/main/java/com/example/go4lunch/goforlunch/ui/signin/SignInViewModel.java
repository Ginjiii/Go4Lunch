package com.example.go4lunch.goforlunch.ui.signin;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

public class SignInViewModel extends ViewModel {

    public static final int RC_SIGN_IN = 100;
    private Coworker coworker;
    protected CoworkerRepository coworkerRepository;

    public SignInViewModel()
    {
        this.coworkerRepository = new CoworkerRepository();
    }

    public void startSignInActivityFacebook(Activity activity) {
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new
                                AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    public void startSignInActivityGoogle(Activity activity) {
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new
                                AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    public void startSignInActivityTwitter(Activity activity) {
        activity.startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new
                                AuthUI.IdpConfig.TwitterBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    public void checkIfUserIsLogged() {
        if (isCurrentUserLogged()){
            this.fetchCurrentUserFromFirestore();
        }
    }

    private void fetchCurrentUserFromFirestore(){
        if (isCurrentUserLogged()) {
            coworkerRepository.getCoworker(getCurrentUser().getUid())
                    .addOnSuccessListener(documentSnapshot -> {
                        coworker = documentSnapshot.toObject(Coworker.class);
                        if (coworker == null){
                            createUserInFirestore();
                        } else {
                            coworkerRepository.updateCoworkerRepository(coworker);
                        }
                    });
        }
    }

    private void createUserInFirestore() {
        Log.d("FireStore", "createUserInFirestore: ");
        String urlPicture = (getCurrentUser().getPhotoUrl() != null) ?
                this.getCurrentUser().getPhotoUrl().toString() : null;
        //String email = getCurrentUser().getEmail();
        String username = getCurrentUser().getDisplayName();
        String uid = getCurrentUser().getUid();
        coworkerRepository.createCoworker(uid, username, urlPicture)
                .addOnSuccessListener(aVoid -> fetchCurrentUserFromFirestore());
    }

    @Nullable
    private FirebaseUser getCurrentUser(){
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    private boolean isCurrentUserLogged() {
        return (this.getCurrentUser() != null);
    }


}