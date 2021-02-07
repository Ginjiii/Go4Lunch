package com.example.go4lunch.goforlunch.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.List;

public class Coworker {

    private String coworkerId;
    private String coworkerName;
    private String coworkerEmail;
    private String coworkerPhotoUrl;
    private CoworkerRestaurantChoice coworkerRestaurantChosen;
    private List<Likes> coworkerLikes;

    public Coworker() {}

    public Coworker(String coworkerId, List<Likes> coworkerLikes) {
        this.coworkerId = coworkerId;
        this.coworkerLikes = coworkerLikes;
    }

    public Coworker(String coworkerId, String coworkerName) {
        coworkerId = coworkerId;
        coworkerName = coworkerName;
    }


    public Coworker(String coworkerId,String coworkerName, String coworkerEmail, String coworkerPhotoUrl) {
        this.coworkerId = coworkerId;
        this.coworkerName = coworkerName;
        this.coworkerEmail = coworkerEmail;
        this.coworkerPhotoUrl = coworkerPhotoUrl;
    }

    public Coworker(String coworkerId, String coworkerName, String coworkerEmail, String coworkerPhotoUrl,
                    CoworkerRestaurantChoice coworkerRestaurantChosen) {
        this.coworkerId = coworkerId;
        this.coworkerName = coworkerName;
        this.coworkerEmail = coworkerEmail;
        this.coworkerPhotoUrl = coworkerPhotoUrl;
        this.coworkerRestaurantChosen = coworkerRestaurantChosen;
    }

    public Coworker(String mCoworkerId, String mCoworkerName, String mCoworkerEmail, String mCoworkerPhotoUrl,
                    CoworkerRestaurantChoice mCoworkerRestaurantChosen, List<Likes> mCoworkerLikes) {
        coworkerId = mCoworkerId;
        coworkerName = mCoworkerName;
        coworkerEmail = mCoworkerEmail;
        coworkerPhotoUrl = mCoworkerPhotoUrl;
        coworkerRestaurantChosen = mCoworkerRestaurantChosen;
        coworkerLikes = mCoworkerLikes;
    }

    public String getCoworkerName() { return coworkerName; }

    public void setCoworkerName(String mCoworkerName) { coworkerName = mCoworkerName; }

    public String getCoworkerEmail() { return coworkerEmail; }

    public void setCoworkerEmail(String mCoworkerEmail) { coworkerEmail = mCoworkerEmail; }

    public String getCoworkerPhotoUrl() { return coworkerPhotoUrl; }

    public void setCoworkerPhotoUrl(@Nullable String mCoworkerPhotoUrl) { coworkerPhotoUrl = mCoworkerPhotoUrl; }

    public CoworkerRestaurantChoice getCoworkerRestaurantChosen() { return coworkerRestaurantChosen; }

    public void setCoworkerRestaurantChosen(CoworkerRestaurantChoice mCoworkerRestaurantChosen) { coworkerRestaurantChosen = mCoworkerRestaurantChosen; }

    public String getCoworkerId() { return coworkerId; }

    public void setCoworkerId(String mCoworkerId) { coworkerId = mCoworkerId; }

    public List<Likes> getCoworkerLikes() { return coworkerLikes; }

    public void setCoworkerLikes(List<Likes> mCoworkerLikes) { coworkerLikes = mCoworkerLikes; }

    public static class Likes {
        public String restaurantId;
        public String restaurantName;

        public Likes() {}

        public Likes(String mRestaurantId, String mRestaurantName) {
            restaurantId = mRestaurantId;
            restaurantName = mRestaurantName;
        }

        public String getRestaurantId() { return restaurantId; }

        public void setRestaurantId(String mRestaurantId) { restaurantId = mRestaurantId; }

        public String getRestaurantName() { return restaurantName; }

        public void setRestaurantName(String mRestaurantName) { restaurantName = mRestaurantName; }
    }

    public static class CoworkerRestaurantChoice {
        public String restaurantId;
        public String restaurantName;
        public Timestamp restaurantDateChoice;

        public CoworkerRestaurantChoice() {}

        public CoworkerRestaurantChoice(String mRestaurantId, String mRestaurantName, Timestamp mRestaurantDateChoice) {
            restaurantId = mRestaurantId;
            restaurantName = mRestaurantName;
            restaurantDateChoice = mRestaurantDateChoice;
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

        public Timestamp getRestaurantDateChoice() {
            return restaurantDateChoice;
        }

        public void setRestaurantDateChoice(Timestamp mRestaurantDateChoice) {
            restaurantDateChoice = mRestaurantDateChoice;
        }
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