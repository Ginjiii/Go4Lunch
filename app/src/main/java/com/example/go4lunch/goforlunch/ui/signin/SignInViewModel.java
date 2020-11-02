package com.example.go4lunch.goforlunch.ui.signin;

import android.app.Activity;

import androidx.lifecycle.ViewModel;

import com.firebase.ui.auth.AuthUI;

import java.util.Collections;

public class SignInViewModel extends ViewModel {

    public static final int RC_SIGN_IN = 100;

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
}