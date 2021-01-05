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

    public Coworker(String CoworkerId, String coworkerName, String coworkerEmail, String coworkerPhotoUrl,
                    CoworkerRestaurantChoice coworkerRestaurantChosen, List<Likes> coworkerLikes) {
        coworkerId = coworkerId;
        coworkerName = coworkerName;
        coworkerEmail = coworkerEmail;
        coworkerPhotoUrl = coworkerPhotoUrl;
        coworkerRestaurantChosen = coworkerRestaurantChosen;
        coworkerLikes = coworkerLikes;
    }

    public String getCoworkerName() { return coworkerName; }

    public void setCoworkerName(String coworkerName) { coworkerName = coworkerName; }

    public String getCoworkerEmail() { return coworkerEmail; }

    public void setCoworkerEmail(String coworkerEmail) { coworkerEmail = coworkerEmail; }

    public String getCoworkerPhotoUrl() { return coworkerPhotoUrl; }

    public void setCoworkerPhotoUrl(@Nullable String coworkerPhotoUrl) { coworkerPhotoUrl = coworkerPhotoUrl; }

    public CoworkerRestaurantChoice getCoworkerRestaurantChosen() { return coworkerRestaurantChosen; }

    public void setCoworkerRestaurantChosen(CoworkerRestaurantChoice coworkerRestaurantChosen) { coworkerRestaurantChosen = coworkerRestaurantChosen; }

    public String getCoworkerId() { return coworkerId; }

    public void setCoworkerId(String coworkerId) { coworkerId = coworkerId; }

    public List<Likes> getCoworkerLikes() { return coworkerLikes; }

    public void setCoworkerLikes(List<Likes> coworkerLikes) { coworkerLikes = coworkerLikes; }

    public static class Likes {
        public String restaurantId;
        public String restaurantName;

        public Likes() {}

        public Likes(String restaurantId, String restaurantName) {
            restaurantId = restaurantId;
            restaurantName = restaurantName;
        }

        public String getRestaurantId() { return restaurantId; }

        public void setRestaurantId(String restaurantId) { restaurantId = restaurantId; }

        public String getRestaurantName() { return restaurantName; }

        public void setRestaurantName(String restaurantName) { restaurantName = restaurantName; }
    }

    public static class CoworkerRestaurantChoice {
        public String restaurantId;
        public String restaurantName;
        public Timestamp restaurantDateChoice;

        public CoworkerRestaurantChoice() {}

        public CoworkerRestaurantChoice(String restaurantId, String restaurantName, Timestamp restaurantDateChoice) {
            restaurantId = restaurantId;
            restaurantName = restaurantName;
            restaurantDateChoice = restaurantDateChoice;
        }

        public String getRestaurantId() {
            return restaurantId;
        }

        public void setRestaurantId(String restaurantId) {
            restaurantId = restaurantId;
        }

        public String getRestaurantName() {
            return restaurantName;
        }

        public void setRestaurantName(String restaurantName) {
            restaurantName = restaurantName;
        }

        public Timestamp getRestaurantDateChoice() {
            return restaurantDateChoice;
        }

        public void setRestaurantDateChoice(Timestamp restaurantDateChoice) {
            restaurantDateChoice = restaurantDateChoice;
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
