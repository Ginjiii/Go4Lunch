package com.example.go4lunch.goforlunch.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Coworker {
    private String uid;
    private String coworkerName;
    @Nullable private String coworkerEmail;
    @Nullable private String coworkerPhotoUrl;
    @Nullable private CoworkerRestaurantChoice coworkerRestaurantChosen;
    @Nullable private boolean restaurantChoice;
    @Nullable private String restaurantUid;
    @Nullable private String restaurantName;
    private String restaurantAddress;
    private List<String> likedRestaurants;
    public Coworker() { }

    public Coworker(String uid, String username, String urlPicture) {
        this.uid = uid;
        this.coworkerName = username;
        this.coworkerPhotoUrl = urlPicture;
    }

    public Coworker(String mCoworkerId,String mCoworkerName, String mCoworkerEmail, String mCoworkerPhotoUrl) {
        this.uid = mCoworkerId;
        this.coworkerName = mCoworkerName;
        this.coworkerEmail = mCoworkerEmail;
        this.coworkerPhotoUrl = mCoworkerPhotoUrl;

        this.restaurantChoice = false;
    }

    public Coworker(String mCoworkerId, String mCoworkerName, String mCoworkerEmail, String mCoworkerPhotoUrl,
                    CoworkerRestaurantChoice mCoworkerRestaurantChosen) {
        this.uid = mCoworkerId;
        this.coworkerName = mCoworkerName;

        this.coworkerEmail = mCoworkerEmail;
        this.coworkerPhotoUrl = mCoworkerPhotoUrl;
        this.coworkerRestaurantChosen = mCoworkerRestaurantChosen;
    }

    public String getCoworkerName() { return coworkerName; }

    public void setCoworkerName(String mCoworkerName) { coworkerName = mCoworkerName; }

    public String getCoworkerEmail() { return coworkerEmail; }

    public String getRestaurantUid() { return restaurantUid; }

    @Nullable
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(@Nullable String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantAddress() {
        return restaurantAddress;
    }


    public void setCoworkerEmail(String mCoworkerEmail) { coworkerEmail = mCoworkerEmail; }
    public void setRestaurantUid(String mRestaurantUid) { restaurantUid = mRestaurantUid; }


    public String getCoworkerPhotoUrl() { return coworkerPhotoUrl; }

    public void setCoworkerPhotoUrl(@Nullable String mCoworkerPhotoUrl) { coworkerPhotoUrl = mCoworkerPhotoUrl; }

    public boolean isRestaurantChoice() { return restaurantChoice;}

    public CoworkerRestaurantChoice getCoworkerRestaurantChosen() { return coworkerRestaurantChosen; }

    public void  setCoworkerRestaurantChosen(CoworkerRestaurantChoice mCoworkerRestaurantChosen) { this.coworkerRestaurantChosen = mCoworkerRestaurantChosen; this.restaurantChoice = true; }

    public String getUid() { return uid; }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<String> getLikedRestaurants() {
        return likedRestaurants;
    }

    public void addLikedRestaurant(String restaurantUid){
        if(likedRestaurants == null) {
            this.likedRestaurants = new ArrayList<>();
        }
        this.likedRestaurants.add(restaurantUid);
    }

    public void removeLikedRestaurant(String restaurantUid){
        if(likedRestaurants != null) {
            List<String> likeToRemove = new ArrayList<String>();
            for (String like : likedRestaurants) {
                if (like.equals(restaurantUid)) {
                    likeToRemove.add(like);
                }
            }
            likedRestaurants.removeAll(likeToRemove);
        }
    }

    public static class CoworkerRestaurantChoice {
        public String restaurantId;
        public String restaurantName;
        public String restaurantAddress;
        public Timestamp restaurantDateChoice;

        public CoworkerRestaurantChoice() {}

        public CoworkerRestaurantChoice(String mRestaurantId, String mRestaurantName, String mRestaurantAddress ,Timestamp mRestaurantDateChoice) {
            restaurantId = mRestaurantId;
            restaurantName = mRestaurantName;
            restaurantAddress = mRestaurantAddress;
            restaurantDateChoice = mRestaurantDateChoice;
        }

        public CoworkerRestaurantChoice(String restaurantPlaceId, String restaurantName, Timestamp timestamp) {
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String mRestaurantId) {
            restaurantId = mRestaurantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String mRestaurantName) {
            restaurantName = mRestaurantName;
        }

        public String getRestaurantAddress() {
            return restaurantAddress;
        }

        public void setRestaurantAddress(String restaurantAddress) {
            this.restaurantAddress = restaurantAddress;
        }

        public Timestamp getRestaurantDateChoice() {
            return restaurantDateChoice;
        }

        public void setRestaurantDateChoice(Timestamp mRestaurantDateChoice) {
            restaurantDateChoice = mRestaurantDateChoice;
        }

    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + this.coworkerName + '\'' +
                ", email='" + this.coworkerEmail + '\'' +
                ", urlPicture='" + this.coworkerPhotoUrl + '\'' +
                ", restaurantUid='" + this.coworkerRestaurantChosen.getRestaurantId() + '\'' +
                ", likedRestaurants=" + this.likedRestaurants +
                '}';
    }

    public enum Fields {
        Coworker,
        coworkerId,
        coworkerName,
        coworkerEmail,
        coworkerPhotoUrl,
        coworkerRestaurantChosen,
        coworkerLikes
    }

}