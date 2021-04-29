package com.example.go4lunch.goforlunch.ui.setting;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;
import com.google.firebase.auth.FirebaseAuth;


public class SettingViewModel extends ViewModel {

    private final SaveDataRepository saveDataRepository;
    private final CoworkerRepository coworkerRepository;
    private Coworker user;

    public SettingViewModel(CoworkerRepository coworkerRepository, SaveDataRepository saveDataRepository) {
        this.saveDataRepository = saveDataRepository;
        this.coworkerRepository = coworkerRepository;
        user = coworkerRepository.getActualUser();
    }

    public void configureSaveDataRepo(Context context){
        if(saveDataRepository.getPreferences() == null){
            saveDataRepository.configureContext(context);
        }
    }

    // --------------------
    // GET USER ACTION
    // --------------------

    public void notificationStateChanged(boolean enabled) {
        saveDataRepository.getPreferences();
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        saveDataRepository.saveNotificationSettings(enabled, uid);
    }
}
