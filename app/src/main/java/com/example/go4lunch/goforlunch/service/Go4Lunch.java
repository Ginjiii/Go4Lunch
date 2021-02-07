package com.example.go4lunch.goforlunch.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.go4lunch.goforlunch.DI.DI;
import com.example.go4lunch.goforlunch.utils.Go4LunchHelper;
import com.example.go4lunch.goforlunch.utils.PreferencesHelper;
import com.go4lunch.R;


public class Go4Lunch extends MultiDexApplication {

    /**
     * Applications errors/success
     */
    public static final String ERROR_ON_FAILURE_LISTENER = "OnFailure : ";

    public static final Api api = DI.getGo4LunchApiService();
    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

        initializeSharedPreferences();
    }

    /**
     * Initialize and load Shared Preferences
     */
    private void initializeSharedPreferences() {

        PreferencesHelper.loadPreferences(this);
        if (preferences == null) {
            double lLat = Double.parseDouble(getString(R.string.default_latitude_position));
            double lLng = Double.parseDouble(getString(R.string.default_longitude_position));
            Location location = Go4LunchHelper.setCurrentLocation(lLat, lLng);
            api.setLocation(location);
        }
    }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
