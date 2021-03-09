package com.example.go4lunch.goforlunch.injections;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.service.GooglePlacesService;

public class Injection {

    public static RestaurantRepository createRestaurantRepository() {
        return new RestaurantRepository();
    }

    public static CoworkerRepository createCoworkerRepository() {
        return new CoworkerRepository();
    }

    public static Go4LunchFactory go4LunchFactory() {
        return new Go4LunchFactory(createRestaurantRepository(), createCoworkerRepository());
    }
    public static RestaurantRepository provideRestaurantRepository() {
        return RestaurantRepository.getInstance();
    }

    public static Go4LunchFactory provideViewModelFactory(){
        CoworkerRepository coworkerRepository =  CoworkerRepository.getInstance();
        RestaurantRepository restaurantRepository = provideRestaurantRepository();

        return new Go4LunchFactory(restaurantRepository,coworkerRepository);
    }
}