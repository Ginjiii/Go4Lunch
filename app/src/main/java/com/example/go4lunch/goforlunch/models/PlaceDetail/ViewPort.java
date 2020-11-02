package com.example.go4lunch.goforlunch.models.PlaceDetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ViewPort implements Serializable {

    @SerializedName("northeast")
    private NorthEast mNorthEast;
    @SerializedName("southwest")
    private SouthWest mSouthWest;

    public NorthEast getNorthEast() {
        return mNorthEast;
    }

    public void setNorthEast(NorthEast northeast) {
        mNorthEast = northeast;
    }

    public SouthWest getSouthWest() {
        return mSouthWest;
    }

    public void setSouthWest(SouthWest southwest) {
        mSouthWest = southwest;
    }
}