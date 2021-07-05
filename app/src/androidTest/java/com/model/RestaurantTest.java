package com.model;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

@RunWith(JUnit4.class)
public class RestaurantTest {

    private Restaurant restaurant;
    private String restaurantID = "id" ;
    private String name = "name";
    private Double latitude = 1.0;
    private Double longitude = -1.0;
    private String address = "address";
    private boolean openingHours = true;
    private int distance = 100;
    private String photoReference = "photo_reference";
    private float rating = 4;
    private String phoneNumber = "phone_number";
    private String webSite = "website";
    private List<Coworker> coworkersEatingHere = new ArrayList<>();
    private Coworker coworkerEating;


    @Before
    public void setup() {}

    @Test
    public void setAndGetRestaurantIdWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);
        assertEquals(restaurantID, restaurant.getRestaurantID());

        String setData = "restaurantId";
        restaurant.setRestaurantID(setData);

        String getData = restaurant.getRestaurantID();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    @Test
    public void setAndGetRestaurantNameWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);
        assertEquals(name, restaurant.getName());

        String setData = "restaurantName";
        restaurant.setName(setData);

        String getData = restaurant.getName();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    @Test
    public void setAndGetRestaurantAddressWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);

        String setData = "restaurantAddress";
        restaurant.setAddress(setData);

        String getData = restaurant.getAddress();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    @Test
    public void setAndGetPhoneWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);

        String setData = "restaurantPhoneNumber";
        restaurant.setPhoneNumber(setData);

        String getData = restaurant.getPhoneNumber();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    @Test
    public void setAndGetWebSiteWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);

        String setData = "restaurantWebsite";
        restaurant.setWebSite(setData);

        String getData = restaurant.getWebSite();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    @Test
    public void setAndGetRatingWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);
        assertEquals(restaurant.getRating(), (rating), 0.0);

        float setData = (float) 3.2;
        restaurant.setRating(setData);

        double getData = restaurant.getRating();
        assertEquals(getData, setData, 0.0);
    }

    @Test
    public void setAndGetRestaurantPhotoUrlWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);
        assertEquals(photoReference, restaurant.getPhotoReference());

        String setData = "photoReference";
        restaurant.setPhotoReference(setData);

        String getData = restaurant.getPhotoReference();
        assertNotNull(getData);
        assertEquals(setData,getData);
    }

    @Test
    public void setAndGetRestaurantDistanceInMetersWithSuccess() {
        restaurant = new Restaurant(restaurantID,name,latitude,longitude,address,openingHours,distance,photoReference,rating,phoneNumber,webSite);

        int setData = 200;
        restaurant.setDistance(setData);

        int getData = restaurant.getDistance();
        assertEquals(setData, getData);
    }

    @Test
    public void setAndGetCoworkerNameFromCoworkersListComingToEatWithSuccess() {
        coworkerEating = new Coworker();
        assertNull(coworkerEating.getCoworkerRestaurantChoice());

        String setData = "coworkerName";
        coworkerEating.setName(setData);

        String getData = coworkerEating.getName();
        assertNotNull(getData);
        assertEquals(setData, getData);
    }

    @Test
    public void setAndGetCoworkerPhotoReferenceFromCoworkersListComingToEatWithSuccess() {
        coworkerEating = new Coworker();
        assertNull(coworkerEating.getPhotoUrl());

        String setData = "albert photo url";
        coworkerEating.setPhotoUrl(setData);

        String getData = coworkerEating.getPhotoUrl();
        assertNotNull(getData);
        assertEquals(setData, getData);
    }

}