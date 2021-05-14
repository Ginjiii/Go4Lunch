package com.example.go4lunch.goforlunch.injections;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;

public class Injection {

    public static RestaurantRepository createRestaurantRepository() {
        CoworkerRepository coworkerRepository = CoworkerRepository.getInstance();

        return new RestaurantRepository(coworkerRepository);
    }

    public static CoworkerRepository createCoworkerRepository() {
        return new CoworkerRepository();
    }

    public static SaveDataRepository createSaveDataRepository() {
        return new SaveDataRepository();
    }

    public static Go4LunchFactory go4LunchFactory() {
        return new Go4LunchFactory(createRestaurantRepository(), createCoworkerRepository(), createSaveDataRepository());
    }

    public static RestaurantRepository provideRestaurantRepository() {
        return RestaurantRepository.getInstance();
    }

    public static SaveDataRepository provideSaveDataRepository() {
        return SaveDataRepository.getInstance();
    }

    public static Go4LunchFactory provideViewModelFactory() {
        CoworkerRepository coworkerRepository = CoworkerRepository.getInstance();
        RestaurantRepository restaurantRepository = provideRestaurantRepository();
        SaveDataRepository saveDataRepository = provideSaveDataRepository();


        return new Go4LunchFactory(restaurantRepository, coworkerRepository, saveDataRepository);
    }
}