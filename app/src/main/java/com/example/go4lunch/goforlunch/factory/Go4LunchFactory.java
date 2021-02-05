package com.example.go4lunch.goforlunch.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.maps.MapsViewModel;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantDetailViewModel;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsViewModel;
import com.example.go4lunch.goforlunch.ui.signin.SignInViewModel;

public class Go4LunchFactory implements ViewModelProvider.Factory {

    private final RestaurantRepository restaurantRepository;
    private final CoworkerRepository coworkerRepository;


    public Go4LunchFactory(RestaurantRepository restaurantRepository, CoworkerRepository coworkerRepository) {
        this.restaurantRepository = restaurantRepository;
        this.coworkerRepository = coworkerRepository;
    }

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SignInViewModel.class)) {
            return (T) new SignInViewModel();
        }
        if (modelClass.isAssignableFrom(MapsViewModel.class)) {
            return (T) new MapsViewModel(this.restaurantRepository);
        }

        if (modelClass.isAssignableFrom(RestaurantDetailViewModel.class)) {
            return (T) new RestaurantDetailViewModel(this.restaurantRepository,coworkerRepository);
        }
        if (modelClass.isAssignableFrom(RestaurantsViewModel.class)) {
            return (T) new RestaurantsViewModel(this.restaurantRepository);
        }
        throw new IllegalArgumentException("Problem with ViewModelFactory");
    }
}
