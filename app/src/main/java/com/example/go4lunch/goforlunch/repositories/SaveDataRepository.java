package com.example.go4lunch.goforlunch.repositories;

import android.content.Context;
import android.content.SharedPreferences;

public class SaveDataRepository {

    private SharedPreferences preferences;

    public static final String KEY_PREF = "prefNotification";

    private static volatile SaveDataRepository INSTANCE;

    public static SaveDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SaveDataRepository();
        }
        return INSTANCE;
    }

    public SaveDataRepository() {
    }

    public SharedPreferences getPreferences() {
        return preferences;
    }

    public void configureContext(Context context) {
        preferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
    }

    //-----SAVE------
    public void saveNotificationSettings(boolean state, String userId) {
        if (preferences != null) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean(userId, state);
            editor.apply();
        }
    }

    //-----GET------
    public Boolean getNotificationSettings(String userId) {
        if (preferences == null) return true;
        return preferences.getBoolean(userId, true);
    }
}
