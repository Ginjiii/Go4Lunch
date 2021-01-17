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
     * @param pLat : double : latitude
     * @param pLng: double : longitude
     * @return : object : location
     */
    public static Location setCurrentLocation(Double pLat, Double pLng) {
        Location lFusedLocationProvider = new Location(TXT_PROVIDER);
        lFusedLocationProvider.setLatitude(pLat);
        lFusedLocationProvider.setLongitude(pLng);
        return lFusedLocationProvider;
    }

    /**
     * Convert the distance in a text format for the display
     * @param pDistance : int : distance
     * @return : string : distance with the indicator meters or kilometers
     */
    public static String convertDistance(int pDistance) {
        String lNewDistance = String.valueOf(pDistance);
        double lDistance = pDistance*0.001;

        DecimalFormat lDecimalFormat = new DecimalFormat("##.#");
        lDecimalFormat.setRoundingMode(RoundingMode.UP);

        if(pDistance<1000) {
            lNewDistance = lNewDistance + "m";
        } else {
            lNewDistance = lDecimalFormat.format(lDistance) + "km";
        }

        return lNewDistance;
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
     * @param pDay : int : day
     * @return : string : day
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDayString(int pDay) {
        String[] lShortWeekDay = new DateFormatSymbols().getShortWeekdays();
        String lStringDay = "";

        for(int lIndex = 1; lIndex < lShortWeekDay.length; lIndex++) {
            if (lIndex == pDay+1) {
                lStringDay = lShortWeekDay[lIndex];
            }
        }
        return lStringDay;
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
     * @param pTime : int : time
     * @return : string : time
     */
    public static String getCurrentTimeFormatted(int pTime) {

        @SuppressLint("DefaultLocale")
        String lResult = String.format("%02d:%02d", pTime / 100, pTime % 100);

        return lResult;
    }

    /**
     * Convert time into minutes
     * @param pTime : int : time
     * @return : int : minutes
     */
    public static int convertTimeInMinutes(int pTime) {
        int lHourIntoMin = (pTime/100)*60;
        int lMinutes = pTime % 100;
        return (lHourIntoMin + lMinutes);
    }

    /**
     * Format the address
     * @param pAddress : string : address
     * @return : string : address
     */
    public static String formatAddress(String pAddress) {
        String lAddress = null;
        if(pAddress.indexOf(",")>0) {
            lAddress = pAddress.substring(0, pAddress.indexOf(","));
        }
        return lAddress;
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