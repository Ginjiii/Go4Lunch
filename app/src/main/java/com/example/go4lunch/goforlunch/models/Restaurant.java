package com.example.go4lunch.goforlunch.models;

import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;

import java.io.Serializable;
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
    private RestaurantDetail.Location restaurantLocation;
    private RestaurantDetail.OpeningHours restaurantOpeningHours;
    private int restaurantDistance;
    private List<CoworkerList> restaurantCoworkerList;

    public Restaurant () {}

    public Restaurant(String mRestaurantPlaceId, String mRestaurantName) {
        restaurantPlaceId = mRestaurantPlaceId;
        restaurantName = mRestaurantName;
    }

    public Restaurant(String mRestaurantPlaceId, String mRestaurantName, String mRestaurantAddress,
                      String mRestaurantDistanceText, double mRestaurantRating, String mRestaurantPhotoUrl,
                      RestaurantDetail.Location mRestaurantLocation, RestaurantDetail.OpeningHours mRestaurantOpeningHours,
                      int mRestaurantDistance, List<CoworkerList> mRestaurantCoworkerList) {
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
                      RestaurantDetail.Location mRestaurantLocation, RestaurantDetail.OpeningHours mRestaurantOpeningHours,
                      int mRestaurantDistance, List<CoworkerList> mRestaurantCoworkerList) {

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

    public double getRestaurantRating() { return restaurantRating; }

    public void setRestaurantRating(double mRestaurantRating) { restaurantRating = mRestaurantRating; }

    public String getRestaurantPhotoUrl() { return restaurantPhotoUrl; }

    public void setRestaurantPhotoUrl(String mRestaurantPhotoUrl) { restaurantPhotoUrl = mRestaurantPhotoUrl; }

    public RestaurantDetail.Location getRestaurantLocation() { return restaurantLocation; }

    public void setRestaurantLocation(RestaurantDetail.Location mRestaurantLocation) { restaurantLocation = mRestaurantLocation; }

    public RestaurantDetail.OpeningHours getRestaurantOpeningHours() { return restaurantOpeningHours; }

    public void setRestaurantOpeningHours(RestaurantDetail.OpeningHours mRestaurantOpeningHours) { restaurantOpeningHours = mRestaurantOpeningHours; }

    public int getRestaurantDistance() { return restaurantDistance; }

    public void setRestaurantDistance(int mRestaurantDistance) { restaurantDistance = mRestaurantDistance; }

    public List<CoworkerList> getRestaurantCoworkerList() {
        return restaurantCoworkerList;
    }

    public void setRestaurantCoworkerList(List<CoworkerList> mRestaurantCoworkerList) {
        restaurantCoworkerList = mRestaurantCoworkerList;
    }

    @Override
    public int compareTo(Restaurant mRestaurant) { return mRestaurant.restaurantDistance - this.restaurantDistance; }

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
        restaurantWkList
    }
}