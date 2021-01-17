package com.example.go4lunch.goforlunch.utils;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

public class PreferencesHelper {

    public static SharedPreferences preferences;

    /**
     * Upload the SharedPreferences in mPreferences
     */
    public static void  loadPreferences(Context context) {
        preferences = context.getSharedPreferences("SHARED_PREFS", MODE_PRIVATE);
    }

    /**
     * Backup a String data type in the SharedPreferences
     * @param key : String : SharedPreferences key
     * @param valueSaved : String : value of the data saved
     */
    public static void saveStringPreferences(String key,String valueSaved) {
        preferences.edit().putString(key,valueSaved).apply();
    }

    /**
     * Backup an int data type in the SharedPreferences
     * @param key : String : SharedPreferences key
     * @param valueSaved : int : value of the data saved
     */
    public static void saveIntPreferences(String key, int valueSaved) {
        preferences.edit().putInt(key,valueSaved).apply();
    }
}
