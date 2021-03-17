package com.example.go4lunch.goforlunch.models;

import com.example.go4lunch.goforlunch.models.common.Location;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Restaurant implements Serializable, Comparable<Restaurant> {
    private String restaurantPlaceId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
    private String restaurantWebSite;
    private String restaurantDistanceText;
    private double restaurantRating;
    private String restaurantPhotoUrl;
    private Location restaurantLocation;
    private RestaurantDetail.OpeningHours restaurantOpeningHours;
    private float restaurantDistance;
    private List<CoworkerList> restaurantCoworkerList;
    private List<Coworker> coworkersEatingHere;

    public Restaurant () {}

    public Restaurant(String mRestaurantPlaceId, String mRestaurantName) {
        restaurantPlaceId = mRestaurantPlaceId;
        restaurantName = mRestaurantName;
    }

    public Restaurant(String mRestaurantPlaceId, String mRestaurantName, String mRestaurantAddress,
                      String mRestaurantDistanceText, double mRestaurantRating, String mRestaurantPhotoUrl,
                      Location mRestaurantLocation, RestaurantDetail.OpeningHours mRestaurantOpeningHours,
                      float mRestaurantDistance, List<CoworkerList> mRestaurantCoworkerList) {
        restaurantPlaceId = mRestaurantPlaceId;
        restaurantName = mRestaurantName;
        restaurantAddress = mRestaurantAddress;
        restaurantDistanceText = mRestaurantDistanceText;
        restaurantRating = mRestaurantRating;
        restaurantPhotoUrl = mRestaurantPhotoUrl;
        restaurantLocation = mRestaurantLocation;
        restaurantOpeningHours = mRestaurantOpeningHours;
        restaurantDistance = mRestaurantDistance;
        restaurantCoworkerList = mRestaurantCoworkerList;
    }

    public Restaurant(String mRestaurantPlaceId, String mRestaurantName, String mRestaurantAddress, String mRestaurantPhone, String mRestaurantWebSite,
                      String mRestaurantDistanceText,  double mRestaurantRating, String mRestaurantPhotoUrl,
                      Location mRestaurantLocation, RestaurantDetail.OpeningHours mRestaurantOpeningHours,
                      float mRestaurantDistance, List<CoworkerList> mRestaurantCoworkerList) {

        restaurantPlaceId = mRestaurantPlaceId;
        restaurantName = mRestaurantName;
        restaurantAddress = mRestaurantAddress;
        restaurantPhone = mRestaurantPhone;
        restaurantWebSite = mRestaurantWebSite;
        restaurantDistanceText = mRestaurantDistanceText;
        restaurantRating = mRestaurantRating;
        restaurantPhotoUrl = mRestaurantPhotoUrl;
        restaurantLocation = mRestaurantLocation;
        restaurantOpeningHours = mRestaurantOpeningHours;
        restaurantDistance = mRestaurantDistance;
        restaurantCoworkerList = mRestaurantCoworkerList;
        coworkersEatingHere = new ArrayList<>();
    }

    public String getRestaurantPlaceId() { return restaurantPlaceId; }

    public void setRestaurantPlaceId(String mRestaurantPlaceId) { restaurantPlaceId = mRestaurantPlaceId; }

    public String getRestaurantName() { return restaurantName; }

    public void setRestaurantName(String mRestaurantName) { restaurantName = mRestaurantName; }

    public String getRestaurantAddress() { return restaurantAddress; }

    public void setRestaurantAddress(String mRestaurantAddress) { restaurantAddress = mRestaurantAddress; }

    public String getRestaurantPhone() { return restaurantPhone; }

    public void setRestaurantPhone(String mRestaurantPhone) { restaurantPhone = mRestaurantPhone; }

    public String getRestaurantWebSite() { return restaurantWebSite; }

    public void setRestaurantWebSite(String mRestaurantWebSite) { restaurantWebSite = mRestaurantWebSite; }

    public String getRestaurantDistanceText() { return restaurantDistanceText; }

    public void setRestaurantDistanceText(String mRestaurantDistanceText) { restaurantDistanceText = mRestaurantDistanceText; }

    public void setCoworkerChoice(List<Coworker> coworkers){
        coworkersEatingHere = coworkers;

    }

    public List<Coworker> getCoworkerChoice(){
        return coworkersEatingHere;
    }

    public double getRestaurantRating() { return restaurantRating; }

    public void setRestaurantRating(double mRestaurantRating) { restaurantRating = mRestaurantRating; }

    public String getRestaurantPhotoUrl() { return restaurantPhotoUrl; }

    public void setRestaurantPhotoUrl(String mRestaurantPhotoUrl) { restaurantPhotoUrl = mRestaurantPhotoUrl; }

    public Location getRestaurantLocation() { return restaurantLocation; }

    public void setRestaurantLocation(Location mRestaurantLocation) { restaurantLocation = mRestaurantLocation; }

    public RestaurantDetail.OpeningHours getRestaurantOpeningHours() { return restaurantOpeningHours; }

    public void setRestaurantOpeningHours(RestaurantDetail.OpeningHours mRestaurantOpeningHours) { restaurantOpeningHours = mRestaurantOpeningHours; }

    public float getRestaurantDistance() { return restaurantDistance; }

    public void setRestaurantDistance(float mRestaurantDistance) { restaurantDistance = mRestaurantDistance; }

    public List<CoworkerList> getRestaurantCoworkerList() {
        return restaurantCoworkerList;
    }

    public void setRestaurantCoworkerList(List<CoworkerList> mRestaurantCoworkerList) {
        restaurantCoworkerList = mRestaurantCoworkerList;
    }

    @Override
    public int compareTo(Restaurant mRestaurant) { return (int)mRestaurant.restaurantDistance - (int)this.restaurantDistance; }

  // public String SetChosenRestaurant() {
  //
  // }

    public static class CoworkerList {
        public String coworkerId;
        public String coworkerName;
        public String coworkerUrl;

        public CoworkerList() {}

        public CoworkerList(String mCoworkerId, String mCoworkerName, String mCoworkerUrl) {
            coworkerId = mCoworkerId;
            coworkerName = mCoworkerName;
            coworkerUrl = mCoworkerUrl;
        }

        public String getCoworkerId() {
            return coworkerId;
        }

        public void setCoworkerId(String mCoworkerId) {
            coworkerId = mCoworkerId;
        }

        public String getCoworkerName() {
            return coworkerName;
        }

        public void setCoworkerName(String mCoworkerName) {
            coworkerName = mCoworkerName;
        }

        public String getCoworkerUrl() {
            return coworkerUrl;
        }

        public void setCoworkerUrl(String mCoworkerUrl) {
            coworkerUrl = mCoworkerUrl;
        }
    }

    public enum Fields{
        Restaurant,
        restaurantPlaceId,
        restaurantName,
        restaurantAddress,
        restaurantPhone,
        restaurantWebSite,
        restaurantDistanceText,
        restaurantRating,
        restaurantPhotoUrl,
        restaurantLocation,
        restaurantOpeningHours,
        restaurantDistance,
        restaurantCoworkerList
    }
}