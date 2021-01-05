package com.example.go4lunch.goforlunch.models.places.RestaurantsDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Address {

    @SerializedName("long_name")
    @Expose
    private String longName;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("types")
    @Expose
    private List<String> types = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public Address() {
    }

    /**
     *
     * @param types
     * @param shortName
     * @param longName
     */
    public Address(String longName, String shortName, List<String> types) {
        super();
        this.longName = longName;
        this.shortName = shortName;
        this.types = types;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public Address withLongName(String longName) {
        this.longName = longName;
        return this;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Address withShortName(String shortName) {
        this.shortName = shortName;
        return this;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public Address withTypes(List<String> types) {
        this.types = types;
        return this;
    }
}

