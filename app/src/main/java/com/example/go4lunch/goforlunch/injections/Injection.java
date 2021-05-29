package com.example.go4lunch.goforlunch.injections;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;

public class Injection {

    public static RestaurantRepository provideRestaurantRepository() {
        return RestaurantRepository.getInstance();
    }

    public static CoworkerRepository provideCoworkerRepository() {
        return CoworkerRepository.getInstance();
    }

    public static SaveDataRepository provideSaveDataRepository() {
        return SaveDataRepository.getInstance();
    }

    public static Go4LunchFactory provideViewModelFactory() {
        RestaurantRepository restaurantRepository = provideRestaurantRepository();
        CoworkerRepository coworkerRepository = provideCoworkerRepository();
        SaveDataRepository saveDataRepository = provideSaveDataRepository();

        return new Go4LunchFactory(restaurantRepository, coworkerRepository, saveDataRepository);
    }
}