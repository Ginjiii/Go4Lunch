package com.example.go4lunch.goforlunch.models.PlaceDetail;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlaceDetailsResult implements Serializable {

    @SerializedName("name")
    private String mName;
    @SerializedName("opening_hours")
    private OpeningHours mOpeningHours;
    @SerializedName("photos")
    private List<Photos> mPhotos;
    @SerializedName("rating")
    private Double mRating;
    @SerializedName("outskirts")
    private String mOutskirts;
    @SerializedName("formatted_phone_number")
    private String mFormattedPhoneNumber;
    @SerializedName("location")
    private Location mLocation;
    @SerializedName("website")
    private String mWebsite;
    @SerializedName("place_id")
    private String mPlaceId;
    @SerializedName("id")
    private String mId;
    @SerializedName("reference")
    private String mReference;

    public PlaceDetailsResult(String mName, OpeningHours mOpeningHours, List<Photos> mPhotos, Double mRating, String mOutskirts, String mFormattedPhoneNumber, Location mLocation, String mWebsite, String mPlaceId, String mId, String mReference) {
        this.mName = mName;
        this.mOpeningHours = mOpeningHours;
        this.mPhotos = mPhotos;
        this.mRating = mRating;
        this.mOutskirts = mOutskirts;
        this.mFormattedPhoneNumber = mFormattedPhoneNumber;
        this.mLocation = mLocation;
        this.mWebsite = mWebsite;
        this.mPlaceId = mPlaceId;
        this.mId = mId;
        this.mReference = mReference;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public OpeningHours getOpeningHours() {
        return mOpeningHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        mOpeningHours = openingHours;
    }

    public List<Photos> getPhotos() {
        return mPhotos;
    }

    public void setPhotos(List<Photos> photos) {
        mPhotos = photos;
    }

    public Double getRating() {
        return mRating;
    }

    public void setRating(Double rating) {
        mRating = rating;
    }

    public String getOutskirts() {
        return mOutskirts;
    }

    public void setVicinity(String outskirts) {
        mOutskirts = outskirts;
    }

    public String getFormattedPhoneNumber() {
        return mFormattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        mFormattedPhoneNumber = formattedPhoneNumber;
    }

    public Location getLocation() {
        return mLocation;
    }

    public void setLocation(Location geometry) {
        mLocation = geometry;
    }

    public String getWebsite() {
        return mWebsite;
    }

    public void setWebsite(String website) {
        mWebsite = website;
    }

    public String getPlaceId() {
        return mPlaceId;
    }

    public void setPlaceId (String placeId) {
        mPlaceId = placeId;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getReference() {
        return mReference;
    }

    public void setReference(String reference) {
        mReference = reference;
    }
}