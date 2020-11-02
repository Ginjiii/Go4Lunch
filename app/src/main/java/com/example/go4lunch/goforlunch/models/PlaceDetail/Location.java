package com.example.go4lunch.goforlunch.models.PlaceDetail;

import com.google.geo.type.Viewport;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {

    @SerializedName("location")
    private Location mLocation;
    @SerializedName("viewport")
    private Viewport mViewport;

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location location) {
        mLocation = location;
    }

    public Viewport getViewport() {
        return mViewport;
    }

    public void setViewport(Viewport viewport) {
        mViewport = viewport;
    }
}