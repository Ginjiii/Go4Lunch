package com.example.go4lunch.goforlunch.ui.setting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.utils.Actions;

public class SettingViewModel extends ViewModel {

    private final CoworkerRepository coworkerRepository ;

    public SettingViewModel(CoworkerRepository mCoworkerRepository) {
        coworkerRepository = mCoworkerRepository;
    }

    /**
     * Retrieve coworker information from Firestore
     * @return : object : coworker
     */
    public LiveData<Coworker> getCoworkerData() {
        return coworkerRepository.getCoworkerData();
    }

    /**
     * Update the coworker user name in Firestore
     * @param coworkerName : string : new user name
     * @return : enum : result of the action
     */
    public LiveData<Actions> updateCoworkerName(String coworkerName) {
        return coworkerRepository.updateCoworkerName(coworkerName);
    }
}
