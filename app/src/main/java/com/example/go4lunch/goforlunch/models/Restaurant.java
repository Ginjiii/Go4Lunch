package com.example.go4lunch.goforlunch.models;

import com.example.go4lunch.goforlunch.models.places.RestaurantsDetail.Location;
import com.example.go4lunch.goforlunch.models.places.RestaurantsDetail.OpeningHours;

import java.io.Serializable;
import java.util.List;

public class Restaurant implements Serializable, Comparable<Restaurant> {
    private String restaurantsPlaceId;
    private String restaurantsName;
    private String restaurantsAddress;
    private String restaurantsPhoneNumber;
    private String restaurantsWebSite;
    private String restaurantsDistanceText;
    private double restaurantsRating;
    private String restaurantsPhotoUrl;
    private Location restaurantsLocation;
    private OpeningHours restaurantsOpeningHours;
    private int restaurantsDistance;
    private List<CoworkersList> restaurantsCoworkersList;

    public Restaurant () {}

    public Restaurant(String restaurantsPlaceId, String restaurantsName) {
        restaurantsPlaceId = restaurantsPlaceId;
        restaurantsName = restaurantsName;
    }

    public Restaurant(String restaurantsPlaceId, String restaurantsName, String restaurantsAddress,
                      String restaurantsDistanceText, double restaurantsRating, String restaurantsPhotoUrl,
                      Location restaurantsLocation, OpeningHours restaurantsOpeningHours,
                      int restaurantsDistance, List<CoworkersList> restaurantsCoworkersList) {
        restaurantsPlaceId = restaurantsPlaceId;
        restaurantsName = restaurantsName;
        restaurantsAddress = restaurantsAddress;
        restaurantsDistanceText = restaurantsDistanceText;
        restaurantsRating = restaurantsRating;
        restaurantsPhotoUrl = restaurantsPhotoUrl;
        restaurantsLocation = restaurantsLocation;
        restaurantsOpeningHours = restaurantsOpeningHours;
        restaurantsDistance = restaurantsDistance;
        restaurantsCoworkersList = restaurantsCoworkersList;
    }

    public Restaurant(String restaurantsPlaceId, String restaurantsName, String restaurantsAddress, String restaurantsPhoneNumber, String restaurantsWebSite,
                      String restaurantsDistanceText,  double restaurantsRating, String restaurantsPhotoUrl,
                      Location restaurantsLocation, OpeningHours restaurantsOpeningHours,
                      int restaurantsDistance, List<CoworkersList> restaurantsCoworkersList) {

        restaurantsPlaceId = restaurantsPlaceId;
        restaurantsName = restaurantsName;
        restaurantsAddress = restaurantsAddress;
        restaurantsPhoneNumber = restaurantsPhoneNumber;
        restaurantsWebSite = restaurantsWebSite;
        restaurantsDistanceText = restaurantsDistanceText;
        restaurantsRating = restaurantsRating;
        restaurantsPhotoUrl = restaurantsPhotoUrl;
        restaurantsLocation = restaurantsLocation;
        restaurantsOpeningHours = restaurantsOpeningHours;
        restaurantsDistance = restaurantsDistance;
        restaurantsCoworkersList = restaurantsCoworkersList;
    }

    public String getRestaurantsPlaceId() { return restaurantsPlaceId; }

    public void setRestaurantsPlaceId(String restaurantsPlaceId) { restaurantsPlaceId = restaurantsPlaceId; }

    public String getRestaurantsNameName() { return restaurantsName; }

    public void setRestaurantsNameName(String restaurantsName) { restaurantsName = restaurantsName; }

    public String getRestaurantsAddress() { return restaurantsAddress; }

    public void setRestaurantsAddress(String restaurantsAddress) { restaurantsAddress = restaurantsAddress; }

    public String getRestaurantsPhoneNumber() { return restaurantsPhoneNumber; }

    public void setRestaurantsPhoneNumber(String restaurantsPhoneNumber) { restaurantsPhoneNumber = restaurantsPhoneNumber; }

    public String getRestaurantsWebSite() { return restaurantsWebSite; }

    public void setRestaurantsWebSite(String restaurantsWebSite) { restaurantsWebSite = restaurantsWebSite; }

    public String getRestaurantsDistanceText() { return restaurantsDistanceText; }

    public void setRestaurantsDistanceText(String restaurantsDistanceText) { restaurantsDistanceText = restaurantsDistanceText; }

    public double getRestaurantsRating() { return restaurantsRating; }

    public void setRestaurantsRating(double restaurantsRating) { restaurantsRating = restaurantsRating; }

    public String getRestaurantsPhotoUrl() { return restaurantsPhotoUrl; }

    public void setRestaurantsPhotoUrl(String restaurantsPhotoUrl) { restaurantsPhotoUrl = restaurantsPhotoUrl; }

    public Location getRestaurantsLocation() { return restaurantsLocation; }

    public void setRestaurantsLocation(Location restaurantsLocation) { restaurantsLocation = restaurantsLocation; }

    public OpeningHours getRestaurantsOpeningHours() { return restaurantsOpeningHours; }

    public void setRestaurantsOpeningHours(OpeningHours restaurantsOpeningHours) { restaurantsOpeningHours = restaurantsOpeningHours; }

    public int getRestaurantsDistance() { return restaurantsDistance; }

    public void setRestaurantsDistance(int restaurantsDistance) { restaurantsDistance = restaurantsDistance; }

    public List<CoworkersList> getRestaurantsCoworkersList() {
        return restaurantsCoworkersList;
    }

    public void setRestaurantsCoworkersList(List<CoworkersList> restaurantsCoworkersList) {
        restaurantsCoworkersList = restaurantsCoworkersList;
    }

    @Override
    public int compareTo(Restaurant restaurant) { return restaurant.restaurantsDistance - this.restaurantsDistance; }

    public static class CoworkersList {
        public String coworkersId;
        public String coworkersName;
        public String coworkersUrl;

        public CoworkersList() {}

        public CoworkersList(String coworkersId, String coworkersName, String coworkersUrl) {
            coworkersId = coworkersId;
            coworkersName = coworkersName;
            coworkersUrl = coworkersUrl;
        }

        public String getCoworkersId() {
            return coworkersId;
        }

        public void setCoworkersId(String coworkersId) {
            coworkersId = coworkersId;
        }

        public String getCoworkersName() {
            return coworkersName;
        }

        public void setCoworkersName(String coworkersName) {
            coworkersName = coworkersName;
        }

        public String getCoworkersUrl() {
            return coworkersUrl;
        }

        public void setCoworkersUrl(String coworkersUrl) {
            coworkersUrl = coworkersUrl;
        }
    }

    public enum Fields{
        Restaurant,
        restaurantsPlaceId,
        restaurantsName,
        restaurantsAddress,
        restaurantsPhoneNumber,
        restaurantsWebSite,
        restaurantsDistanceText,
        restaurantsRating,
        restaurantsPhotoUrl,
        restaurantsLocation,
        restaurantsOpeningHours,
        restaurantsDistance,
        restaurantsCoworkersList
    }
}