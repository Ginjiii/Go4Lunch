package com.example.go4lunch.goforlunch.utils;

import android.annotation.SuppressLint;
import android.location.Location;

import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Go4LunchHelper {

    public static final String TXT_PROVIDER = "fusedLocationProvider";

    /**
     * Convert a latitude and a longitude into a location
     * @param mLat : double : latitude
     * @param mLng: double : longitude
     * @return : object : location
     */
    public static Location setCurrentLocation(Double mLat, Double mLng) {
        Location mFusedLocationProvider = new Location(TXT_PROVIDER);
        mFusedLocationProvider.setLatitude(mLat);
        mFusedLocationProvider.setLongitude(mLng);
        return mFusedLocationProvider;
    }

    /**
     * get the current day of week
     * @return : int : day of week
     */
    public static int getCurrentDayInt() {
        Calendar c = Calendar.getInstance();
        Date date = new Date();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * Get day in string format
     * @param mDay : int : day
     * @return : string : day
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDayString(int mDay) {
        String[] mShortWeekDay = new DateFormatSymbols().getShortWeekdays();
        String mStringDay = "";

        for(int mIndex = 1; mIndex < mShortWeekDay.length; mIndex++) {
            if (mIndex == mDay+1) {
                mStringDay = mShortWeekDay[mIndex];
            }
        }
        return mStringDay;
    }

    /**
     * Get current time
     * @return : int : time
     */
    public static int getCurrentTime() {
        @SuppressLint("SimpleDateFormat")
        SimpleDateFormat sdf = new SimpleDateFormat("HHmm");
        return Integer.parseInt(sdf.format(new Date()));
    }

    /**
     * Get current time with a string format
     * @param mTime : int : time
     * @return : string : time
     */
    public static String getCurrentTimeFormatted(int mTime) {

        @SuppressLint("DefaultLocale")
        String mResult = String.format("%02d:%02d", mTime / 100, mTime % 100);

        return mResult;
    }

    /**
     * Convert time into minutes
     * @param mTime : int : time
     * @return : int : minutes
     */
    public static int convertTimeInMinutes(int mTime) {
        int mHourIntoMin = (mTime/100)*60;
        int mMinutes = mTime % 100;
        return (mHourIntoMin + mMinutes);
    }
}