package com.example.go4lunch.goforlunch.models;

import androidx.annotation.Nullable;

import com.google.firebase.Timestamp;

import java.util.List;

public class Coworker {
    private String uid;
     private String coworkerName;
    @Nullable private String coworkerEmail;
    @Nullable private String coworkerPhotoUrl;
    @Nullable private CoworkerRestaurantChoice coworkerRestaurantChosen;
    @Nullable private List<Likes> coworkerLikes;
    public Coworker() { }
    public Coworker(String uid, String username, String urlPicture) {
        this.uid = uid;
        this.coworkerName = username;
        this.coworkerPhotoUrl = urlPicture;
    }

    public Coworker(String coworkerId, List<Likes> coworkerLikes) {
        this.uid = coworkerId;
        this.coworkerLikes = coworkerLikes;
    }

    public Coworker(String mCoworkerId, String mCoworkerName) {
        uid = mCoworkerId;
        coworkerName = mCoworkerName;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", username='" + this.coworkerName + '\'' +
                ", email='" + this.coworkerEmail + '\'' +
                ", urlPicture='" + this.coworkerPhotoUrl + '\'' +
                ", restaurantUid='" + this.coworkerRestaurantChosen.getRestaurantId() + '\'' +
                ", likedRestaurants=" + this.coworkerLikes +
                '}';
    }
    public Coworker(String mCoworkerId,String mCoworkerName, String mCoworkerEmail, String mCoworkerPhotoUrl) {
        this.uid = mCoworkerId;
        this.coworkerName = mCoworkerName;
        this.coworkerEmail = mCoworkerEmail;
        this.coworkerPhotoUrl = mCoworkerPhotoUrl;
    }

    public Coworker(String mCoworkerId, String mCoworkerName, String mCoworkerEmail, String mCoworkerPhotoUrl,
                    CoworkerRestaurantChoice mCoworkerRestaurantChosen) {
        this.uid = mCoworkerId;
        this.coworkerName = mCoworkerName;
        this.coworkerEmail = mCoworkerEmail;
        this.coworkerPhotoUrl = mCoworkerPhotoUrl;
        this.coworkerRestaurantChosen = mCoworkerRestaurantChosen;
    }

    public Coworker(String mCoworkerId, String mCoworkerName, String mCoworkerEmail, String mCoworkerPhotoUrl,
                    CoworkerRestaurantChoice mCoworkerRestaurantChosen, List<Likes> mCoworkerLikes) {
        uid = mCoworkerId;
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

    public String getUid() { return uid; }

    public void setUid(String uid) {
        this.uid = uid;
    }

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