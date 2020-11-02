package com.example.go4lunch.goforlunch.ui.signin;

public interface SignInNavigator {

    void handleError(String error);

    void login();

    void openMainActivity();
}