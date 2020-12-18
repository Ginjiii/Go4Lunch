package com.example.go4lunch.goforlunch.models.places;

import java.io.Serializable;

public class RestaurantGeometry implements Serializable {

    private RestaurantLocation location;

    public RestaurantGeometry() {
    }

    public RestaurantGeometry(RestaurantLocation location) {
        this.location = location;
    }

    public RestaurantLocation getLocation() {
        return location;
    }

    public void setLocation(RestaurantLocation location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "RestaurantGeometry{" +
                "location=" + location +
                '}';
    }
}