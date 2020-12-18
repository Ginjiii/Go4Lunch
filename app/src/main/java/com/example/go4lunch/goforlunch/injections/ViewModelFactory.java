package com.example.go4lunch.goforlunch.injections;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsViewModel;
import com.example.go4lunch.goforlunch.repositories.UserRepository;
import com.example.go4lunch.goforlunch.ui.signin.SignInViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;


    public ViewModelFactory(UserRepository userRepository, RestaurantRepository restaurantRepository){
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(SignInViewModel.class)){
            return (T) new SignInViewModel();
        }
        if(modelClass.isAssignableFrom(RestaurantsViewModel.class)){
            return (T) new RestaurantsViewModel(userRepository, restaurantRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}