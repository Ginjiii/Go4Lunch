package com.example.go4lunch.goforlunch.injections;

import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.service.GooglePlacesService;
import com.example.go4lunch.goforlunch.repositories.UserRepository;

import retrofit2.Retrofit;

public class Injection {

    public static UserRepository provideUserRepository(){
        return UserRepository.getInstance();
    }

    public static RestaurantRepository provideRestaurantRepository() {
        GooglePlacesService googlePlacesService = Retrofit.create(GooglePlacesService.class);
        return RestaurantRepository.getInstance(googlePlacesService);
    }

    public static ViewModelFactory provideViewModelFactory(){
        UserRepository userRepository = provideUserRepository();
        RestaurantRepository restaurantRepository = provideRestaurantRepository();

        return new ViewModelFactory(userRepository, restaurantRepository);
    }
}