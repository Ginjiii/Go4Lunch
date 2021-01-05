package com.example.go4lunch.goforlunch.models.places.RestaurantsDetail;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class DetailsResult {

    @SerializedName("address_components")
    @Expose
    private List<Address> address = null;
    @SerializedName("the_address")
    @Expose
    private String theAddress;
    @SerializedName("formatted_address")
    @Expose
    private String formattedAddress;
    @SerializedName("formatted_phone_number")
    @Expose
    private String formattedPhoneNumber;
    @SerializedName("geometry")
    @Expose
    private Geometry geometry;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("international_phone_number")
    @Expose
    private String internationalPhoneNumber;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("opening_hours")
    @Expose
    private OpeningHours openingHours;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("rating")
    @Expose
    private Double rating;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("reviews")
    @Expose
    private List<Review> reviews = null;
    @SerializedName("types")
    @Expose
    private List<String> types = null;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("utc_offset")
    @Expose
    private Integer utcOffset;
    @SerializedName("vicinity")
    @Expose
    private String vicinity;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("photos")
    @Expose
    private List<Photos> photos = new ArrayList<>();

    /**
     * No args constructor for use in serialization
     *
     */
    public DetailsResult() {
    }
    public DetailsResult(List<Address> address, String theAddress, String formattedAddress,
                  String formattedPhoneNumber, Geometry geometry, String icon, String id, String internationalPhoneNumber,
                  String name, String placeId, Double rating, String reference, List<Review> reviews, List<String> types,
                  String url, Integer utcOffset, String vicinity, String website, List<Photos> photos) {
        super();
        this.address = address;
        this.theAddress = theAddress;
        this.formattedAddress = formattedAddress;
        this.formattedPhoneNumber = formattedPhoneNumber;
        this.geometry = geometry;
        this.icon = icon;
        this.id = id;
        this.internationalPhoneNumber = internationalPhoneNumber;
        this.name = name;
        this.placeId = placeId;
        this.rating = rating;
        this.reference = reference;
        this.reviews = reviews;
        this.types = types;
        this.url = url;
        this.utcOffset = utcOffset;
        this.vicinity = vicinity;
        this.website = website;
        this.photos = photos;
    }


    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public DetailsResult withAddress(List<Address> address) {
        this.address = address;
        return this;
    }

    public String getTheAddress() {
        return theAddress;
    }

    public void setTheAddress(String theAddress) {
        this.theAddress = theAddress;
    }

    public DetailsResult withTheAddress(String theAddress) {
        this.theAddress = theAddress;
        return this;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public DetailsResult withFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
        return this;
    }

    public String getFormattedPhoneNumber() {
        return formattedPhoneNumber;
    }

    public void setFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
    }

    public DetailsResult withFormattedPhoneNumber(String formattedPhoneNumber) {
        this.formattedPhoneNumber = formattedPhoneNumber;
        return this;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public DetailsResult withGeometry(Geometry geometry) {
        this.geometry = geometry;
        return this;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public DetailsResult withIcon(String icon) {
        this.icon = icon;
        return this;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DetailsResult withId(String id) {
        this.id = id;
        return this;
    }

    public String getInternationalPhoneNumber() {
        return internationalPhoneNumber;
    }

    public void setInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
    }

    public DetailsResult withInternationalPhoneNumber(String internationalPhoneNumber) {
        this.internationalPhoneNumber = internationalPhoneNumber;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DetailsResult withName(String name) {
        this.name = name;
        return this;
    }

    public OpeningHours getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(OpeningHours openingHours) {
        this.openingHours = openingHours;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public DetailsResult withPlaceId(String placeId) {
        this.placeId = placeId;
        return this;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public DetailsResult withRating(Double rating) {
        this.rating = rating;
        return this;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public DetailsResult withReference(String reference) {
        this.reference = reference;
        return this;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public DetailsResult withReviews(List<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public List<String> getTypes() {
        return types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public DetailsResult withTypes(List<String> types) {
        this.types = types;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public DetailsResult withUrl(String url) {
        this.url = url;
        return this;
    }

    public Integer getUtcOffset() {
        return utcOffset;
    }

    public void setUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
    }

    public DetailsResult withUtcOffset(Integer utcOffset) {
        this.utcOffset = utcOffset;
        return this;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public DetailsResult withVicinity(String vicinity) {
        this.vicinity = vicinity;
        return this;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public DetailsResult withWebsite(String website) {
        this.website = website;
        return this;
    }

    public List<Photos> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photos> photos) {
        this.photos = photos;
    }

}