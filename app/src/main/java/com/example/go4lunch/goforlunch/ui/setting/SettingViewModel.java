package com.example.go4lunch.goforlunch.ui.setting;

import android.content.Context;

import com.example.go4lunch.goforlunch.base.BaseViewModel;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.repositories.SaveDataRepository;

public class SettingViewModel extends BaseViewModel {

    private final SaveDataRepository saveDataRepository;
    private final Coworker coworker;

    public SettingViewModel(CoworkerRepository coworkerRepository, SaveDataRepository saveDataRepository) {
        this.saveDataRepository = saveDataRepository;
        this.coworkerRepository = coworkerRepository;
        coworker = coworkerRepository.getActualUser();
    }

    public void configureSaveDataRepo(Context context) {
        if (saveDataRepository.getPreferences() == null) {
            saveDataRepository.configureContext(context);
        }
    }

    // --------------------
    // GET USER ACTION
    // --------------------

    public void notificationStateChanged(boolean enabled, Context context) {
        saveDataRepository.configureContext(context);
        saveDataRepository.saveNotificationSettings(enabled, coworker.getUid());
    }

    public boolean getStatusNotification(Context context) {
        saveDataRepository.configureContext(context);
        return saveDataRepository.getNotificationSettings(coworker.getUid());
    }
}
