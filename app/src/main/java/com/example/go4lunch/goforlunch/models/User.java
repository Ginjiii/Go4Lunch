package com.example.go4lunch.goforlunch.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userId;
    private String userName;
    private String email;
    @Nullable private String urlPicture;
    @Nullable private String restaurantUid;
    @Nullable private String restaurantName;
    @Nullable private String restaurantAddress;
    private List<String> likedRestaurants;

    public User(String userId, String username, String urlPicture, String placeId, ArrayList<String> like, int currentTime){}

    public User(String userId, String userName, String email, @Nullable String urlPicture) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.urlPicture = urlPicture;
    }

    //-----------
    // SETTERS & GETTERS
    //-----------

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    @Nullable
    public String getRestaurantUid() {
        return restaurantUid;
    }

    public void setRestaurantUid(@Nullable String restaurantUid) {
        this.restaurantUid = restaurantUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
            int position = 0;
            for (String uid : likedRestaurants) {
                if (uid.equals(restaurantUid)) likedRestaurants.remove(position);
                position += 1;
            }
        }
    }

    @Nullable
    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(@Nullable String restaurantName) {
        this.restaurantName = restaurantName;
    }

    @Nullable
    public String getRestaurantAddress() {
        return restaurantAddress;
    }

    public void setRestaurantAddress(@Nullable String restaurantAddress) {
        this.restaurantAddress = restaurantAddress;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + userName + '\'' +
                ", email='" + email + '\'' +
                ", urlPicture='" + urlPicture + '\'' +
                ", restaurantUid='" + restaurantUid + '\'' +
                ", likedRestaurants=" + likedRestaurants +
                '}';
    }
}