package com.example.go4lunch.goforlunch.injections;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;

public class Injection {

    private static RestaurantRepository createRestaurantRepository() {
        return new RestaurantRepository();
    }

    public static Go4LunchFactory go4LunchFactory() {
        return new Go4LunchFactory(createRestaurantRepository());
    }
}