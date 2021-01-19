package com.example.go4lunch.goforlunch.utils;

import android.annotation.SuppressLint;
import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.maps.android.SphericalUtil;

import java.math.RoundingMode;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utils {

    public static final String TXT_PROVIDER = "fusedLocationProvider";
    public static final double MAX_LEVEL_ONE_STAR = 1.67;
    public static final double MAX_LEVEL_TWO_STAR = 3.34;

    /**
     * Indicates the number of star note to display in function of the rating note
     * @param note : double : rating note
     * @return int : number of star to display
     */
    public static int ratingNumberOfStarToDisplay(double note) {

        int lNbStarToDisplay;

        if (note == 0) {
            lNbStarToDisplay = 0;
        } else if (note > 0 && note <= MAX_LEVEL_ONE_STAR) {
            lNbStarToDisplay = 1;
        } else if (note > MAX_LEVEL_ONE_STAR && note <= MAX_LEVEL_TWO_STAR) {
            lNbStarToDisplay = 2;
        } else {
            lNbStarToDisplay = 3;
        }
        return lNbStarToDisplay;
    }

    /**
     * Calculate the distance between the current location and the restaurant
     * @param currentLocation : object : current location
     * @param restaurantLocation : object : restaurant location
     * @return int : return the distance
     */
    public static int getRestaurantDistanceToCurrentLocation(Location currentLocation, Location restaurantLocation) {
        Location lRestaurantLocation = new Location(TXT_PROVIDER);
        lRestaurantLocation.setLatitude(restaurantLocation.getLatitude());
        lRestaurantLocation.setLongitude(restaurantLocation.getLongitude());
        return (int) currentLocation.distanceTo(lRestaurantLocation);
    }

    /**
     * Convert a latitude and a longitude into a location
     * @param Lat : double : latitude
     * @param Lng: double : longitude
     * @return : object : location
     */
    public static Location setCurrentLocation(Double Lat, Double Lng) {
        Location lFusedLocationProvider = new Location(TXT_PROVIDER);
        lFusedLocationProvider.setLatitude(Lat);
        lFusedLocationProvider.setLongitude(Lng);
        return lFusedLocationProvider;
    }

    /**
     * Convert the distance in a text format for the display
     * @param distance : int : distance
     * @return : string : distance with the indicator meters or kilometers
     */
    public static String convertDistance(int distance) {
        String newDistance = String.valueOf(distance);
        double mDistance = distance*0.001;

        DecimalFormat mDecimalFormat = new DecimalFormat("##.#");
        mDecimalFormat.setRoundingMode(RoundingMode.UP);

        if(distance<1000) {
            newDistance = newDistance + "m";
        } else {
            newDistance = mDecimalFormat.format(mDistance) + "km";
        }

        return newDistance;
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
     * @param day : int : day
     * @return : string : day
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDayString(int day) {
        String[] shortWeekDay = new DateFormatSymbols().getShortWeekdays();
        String stringDay = "";

        for(int index = 1; index < shortWeekDay.length; index++) {
            if (index == day+1) {
                stringDay = shortWeekDay[index];
            }
        }
        return stringDay;
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
     * @param time : int : time
     * @return : string : time
     */
    public static String getCurrentTimeFormatted(int time) {

        @SuppressLint("DefaultLocale")
        String result = String.format("%02d:%02d", time / 100, time % 100);

        return result;
    }

    /**
     * Convert time into minutes
     * @param time : int : time
     * @return : int : minutes
     */
    public static int convertTimeInMinutes(int time) {
        int hourIntoMin = (time/100)*60;
        int minutes = time % 100;
        return (hourIntoMin + minutes);
    }

    /**
     * Format the address
     * @param address : string : address
     * @return : string : address
     */
    public static String formatAddress(String address) {
        String mAddress = null;
        if(address.indexOf(",")>0) {
            mAddress = address.substring(0, address.indexOf(","));
        }
        return mAddress;
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