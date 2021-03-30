package com.example.go4lunch.goforlunch.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.google.android.gms.maps.model.LatLng;

public class MainActivityViewModel extends BaseViewModel {

    private final MutableLiveData<LatLng> location = new MutableLiveData<>();

    public final MutableLiveData<String> coworkerName = new MutableLiveData<>();
    public final MutableLiveData<String> urlPicture = new MutableLiveData<>();

    private RestaurantRepository restaurantRepository;


    public LiveData<LatLng> getLocation(){ return location; }

    public MainActivityViewModel(CoworkerRepository coworkerRepository, RestaurantRepository restaurantRepository) {
        this.coworkerRepository = coworkerRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public void configureInfoUser(){
        this.coworker = coworkerRepository.getCoworker();
        coworkerName.setValue(coworkerRepository.getActualUser().getCoworkerName());
        urlPicture.setValue(coworker.getCoworkerPhotoUrl());
    }

    @Override
    public void action(Actions actions) {

    }
}
