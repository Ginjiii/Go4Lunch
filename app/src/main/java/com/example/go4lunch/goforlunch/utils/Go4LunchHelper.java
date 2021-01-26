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
    public static final double MAX_LEVEL_ONE_STAR = 1.67;
    public static final double MAX_LEVEL_TWO_STAR = 3.34;

    /**
     * Indicates the number of star note to display in function of the rating note
     * @param note : double : rating note
     * @return int : number of star to display
     */
    public static int ratingNumberOfStarToDisplay(double note) {

        int numberOfStarToDisplay;

        if (note == 0) {
            numberOfStarToDisplay = 0;
        } else if (note > 0 && note <= MAX_LEVEL_ONE_STAR) {
            numberOfStarToDisplay = 1;
        } else if (note > MAX_LEVEL_ONE_STAR && note <= MAX_LEVEL_TWO_STAR) {
            numberOfStarToDisplay = 2;
        } else {
            numberOfStarToDisplay = 3;
        }
        return numberOfStarToDisplay;
    }

    /**
     * Calculate the distance between the current location and the restaurant
     * @param mCurrentLocation : object : current location
     * @param mRestaurantLocation : object : restaurant location
     * @return int : return the distance
     */
    public static int getRestaurantDistanceToCurrentLocation(Location mCurrentLocation, RestaurantDetail.Location mRestaurantLocation) {
        Location rRestaurantLocation = new Location(TXT_PROVIDER);
        mRestaurantLocation.setLat(mRestaurantLocation.getLat());
        mRestaurantLocation.setLng(mRestaurantLocation.getLng());
        return (int) mCurrentLocation.distanceTo(rRestaurantLocation);
    }

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
     * Convert the distance in a text format for the display
     * @param mDistance : int : distance
     * @return : string : distance with the indicator meters or kilometers
     */
    public static String convertDistance(int mDistance) {
        String mNewDistance = String.valueOf(mDistance);
        double dDistance = mDistance*0.001;

        DecimalFormat lDecimalFormat = new DecimalFormat("##.#");
        lDecimalFormat.setRoundingMode(RoundingMode.UP);

        if(mDistance<1000) {
            mNewDistance = mNewDistance + "m";
        } else {
            mNewDistance = lDecimalFormat.format(dDistance) + "km";
        }

        return mNewDistance;
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

    /**
     * Format the address
     * @param mAddress : string : address
     * @return : string : address
     */
    public static String formatAddress(String mAddress) {
        String aAddress = null;
        if(mAddress.indexOf(",")>0) {
            aAddress = mAddress.substring(0, mAddress.indexOf(","));
        }
        return aAddress;
    }

    /**
     * Define a perimeter for the autocomplete prediction request
     * @param center : object : LatLng center of the perimeter
     * @param radiusInMeters : double : radius in meters of the perimeter
     * @return : object : LatLngBounds : return the new position
     */
    public static LatLngBounds toBounds(LatLng center, double radiusInMeters) {
        double distanceFromCenterToCorner = radiusInMeters * Math.sqrt(2.0);
        LatLng southwestCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 225.0);
        LatLng northeastCorner =
                SphericalUtil.computeOffset(center, distanceFromCenterToCorner, 45.0);
        return new LatLngBounds(southwestCorner, northeastCorner);
    }
}