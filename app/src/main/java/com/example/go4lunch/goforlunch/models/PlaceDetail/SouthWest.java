package com.example.go4lunch.goforlunch.models.PlaceDetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SouthWest implements Serializable {

    @SerializedName("lat")
    private Double mLat;
    @SerializedName("lng")
    private Double mLng;

    public Double getLat() {
        return mLat;
    }

    public void setLat(Double lat) {
        mLat = lat;
    }

    public Double getLng() {
        return mLng;
    }

    public void setLng(Double lng) {
        mLng = lng;
    }
}