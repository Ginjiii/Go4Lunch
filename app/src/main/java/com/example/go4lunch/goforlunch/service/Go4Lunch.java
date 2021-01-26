package com.example.go4lunch.goforlunch.service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Build;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.example.go4lunch.goforlunch.DI.DI;
import com.example.go4lunch.goforlunch.utils.Go4LunchHelper;
import com.example.go4lunch.goforlunch.utils.PreferencesHelper;
import com.go4lunch.R;

import static com.example.go4lunch.goforlunch.utils.PreferencesHelper.preferences;

public class Go4Lunch extends MultiDexApplication {
    /**
     * Notifications
     */
    public static final String CHANNEL_4_ID = "channel4";
    /**
     * Shared Preferences
     */
    public static final String PREF_KEY_RADIUS = "PREF_KEY_RADIUS";
    public static final String PREF_KEY_BOUND_RADIUS = "PREF_KEY_BOUND_RADIUS";
    public static final String PREF_KEY_TYPE_GOOGLE_SEARCH = "PREF_KEY_TYPE_GOOGLE_SEARCH";
    public static final String PREF_KEY_PLACE_DETAIL_FIELDS = "PREF_KEY_PLACE_DETAIL_FIELDS";

    /**
     * Applications errors/success
     */
    public static final String ERROR_ON_FAILURE_LISTENER = "OnFailure : ";
    public static final String ERROR_UNKNOWN = "Error : ";

    public static final Api api = DI.getGo4LunchApiService();
    public static SharedPreferences preferences;

    @Override
    public void onCreate() {
        super.onCreate();

  //      createNotificationChannels();
        initializeSharedPreferences();
        removePreviousRestaurantChoice();
    }

    /**
     * Initiate the start of the cleaning choice restaurant
     */
    private void removePreviousRestaurantChoice() {
  //      CoworkerRestaurantChoiceController.startWorkerController(this);
    }

    /**
     * Initialize and load Shared Preferences
     */
    private void initializeSharedPreferences() {

        PreferencesHelper.loadPreferences(this);
        String valueString;
        int valueInt;

        if (preferences == null) {
            valueString = getString(R.string.type_search);
            PreferencesHelper.saveStringPreferences(PREF_KEY_TYPE_GOOGLE_SEARCH, valueString);

            valueString = getString(R.string.place_detail_fields);
            PreferencesHelper.saveStringPreferences(PREF_KEY_PLACE_DETAIL_FIELDS, valueString);

            valueInt = Integer.parseInt(getString(R.string.proximity_radius));
            PreferencesHelper.saveIntPreferences(PREF_KEY_RADIUS, valueInt);

            valueInt = Integer.parseInt(getString(R.string.bound_radius));
            PreferencesHelper.saveIntPreferences(PREF_KEY_BOUND_RADIUS, valueInt);

            double lLat = Double.parseDouble(getString(R.string.default_latitude_position));
            double lLng = Double.parseDouble(getString(R.string.default_longitude_position));
            Location location = Go4LunchHelper.setCurrentLocation(lLat, lLng);
            api.setLocation(location);
        }
    }

  //  /**
  //   * Create notification channels
  //   */
  //  private void createNotificationChannels() {
  //      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//
  //          NotificationManager lManager = getSystemService(NotificationManager.class);
  //          NotificationChannel lChannel4 = new NotificationChannel(
  //                  CHANNEL_4_ID, getString(R.string.notif_high),
  //                  NotificationManager.IMPORTANCE_HIGH);
  //          lChannel4.setDescription(getString(R.string.notif_high_importance));
//
  //          if (lManager != null) {
  //              lManager.createNotificationChannel(lChannel4);
  //          }
  //      }
  //  }

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(context);
        MultiDex.install(this);
    }
}
