package com.example.go4lunch.goforlunch.ui.signin;

import android.app.Activity;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Collections;

public class SignInViewModel extends BaseViewModel {

    public static final int RC_SIGN_IN = 100;

    public SignInViewModel(CoworkerRepository coworkerRepository) {
        this.coworkerRepository = coworkerRepository;
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

    public void updateCurrentUser() {
        String uid = (isCurrentUserLogged()) ? getCurrentUser().getUid() : "default";
        coworkerRepository.getCoworkerFromFirebase(uid)
                .addOnSuccessListener(documentSnapshot -> {
                    coworker = documentSnapshot.toObject(Coworker.class);
                    if (coworker != null) {
                        coworkerRepository.updateCurrentUser(coworker);
                    } else {
                        createUserInFirestore();
                    }
                });
    }

    private void createUserInFirestore() {
        String uid = getCurrentUser().getUid();
        String name = getCurrentUser().getDisplayName();
        String email = getCurrentUser().getEmail();
        String urlPicture = (getCurrentUser().getPhotoUrl() != null) ?
                this.getCurrentUser().getPhotoUrl().toString() : null;
        Coworker newCoworker = new Coworker(uid, name, email, urlPicture);
        coworkerRepository.createCoworker(newCoworker)
                .addOnSuccessListener(result -> coworkerRepository.updateCurrentUser(newCoworker));
    }

    private FirebaseUser getCurrentUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public boolean isCurrentUserLogged() {
        return getCurrentUser() != null;
    }
}
