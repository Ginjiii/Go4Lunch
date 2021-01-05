package com.example.go4lunch.goforlunch.models.places.Restaurants;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestaurantGeometry {

    @SerializedName("location")
    @Expose
    private RestaurantLocation location;

    /**
     *
     * @return
     * The location
     */
    public RestaurantLocation getLocation() {
        return location;
    }

    /**
     *
     * @param location
     * The location
     */
    public void setLocation(RestaurantLocation location) {
        this.location = location;
    }
}
