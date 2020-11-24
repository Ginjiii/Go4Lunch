package com.example.go4lunch.goforlunch.models;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String userId;
    private String username;
    private String email;
    @Nullable private String urlPicture;
    @Nullable private String restaurantUserId;
    @Nullable private String restaurantName;
    @Nullable private String restaurantAddress;
    private List<String> likedRestaurants;

    public User(){}

    public User(String userId, String username, String email, @Nullable String urlPicture) {
        this.userId = userId;
        this.username = username;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Nullable
    public String getUrlPicture() {
        return urlPicture;
    }

    public void setUrlPicture(@Nullable String urlPicture) {
        this.urlPicture = urlPicture;
    }

    @Nullable
    public String getRestaurantUserId() {
        return restaurantUserId;
    }

    public void setRestaurantUserId(@Nullable String restaurantUserId) {
        this.restaurantUserId = restaurantUserId;
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
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", urlPicture='" + urlPicture + '\'' +
                ", restaurantUserId='" + restaurantUserId + '\'' +
                ", likedRestaurants=" + likedRestaurants +
                '}';
    }
}