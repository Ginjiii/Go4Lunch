package model;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.common.Location;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.List;


import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class RestaurantTest {
    private Restaurant restaurant;

    private String restaurantPlaceId;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantPhone;
    private String restaurantWebSite;
    private String restaurantDistanceText;
    private double restaurantRating;
    private String restaurantPhotoUrl;
    private Location restaurantLocation;
    private RestaurantDetail.OpeningHours restaurantOpeningHours;
    private float restaurantDistance;
    private List<Restaurant.CoworkerList> restaurantCoworkerList;
    private List<Coworker> coworkersEatingHere;


    @Before
    public void setup(){
        restaurantName = "Jean Michel";
        restaurantPlaceId = "12345";
        restaurantAddress = "123 rue blablabla";
        restaurantPhone = "0666666666";
        restaurantWebSite = "www.blablabla.com";
        restaurantDistanceText = "distanceText";
        restaurantRating = 3;
        restaurantPhotoUrl = "http://photo";
        restaurantLocation = restaurantLocation;
        restaurantOpeningHours = restaurantOpeningHours;
        restaurantDistance = 3;
        restaurantCoworkerList = restaurantCoworkerList;
        coworkersEatingHere = new ArrayList<>();

        restaurant = new Restaurant(restaurantPlaceId, restaurantName, restaurantAddress, restaurantPhone, restaurantWebSite, restaurantDistanceText, restaurantRating, restaurantPhotoUrl, restaurantLocation, restaurantOpeningHours, restaurantDistance, restaurantCoworkerList);
    }

    @Test
    public void getCorrectInfoFromRestaurant() throws Exception{
        assertEquals(restaurantPlaceId, restaurant.getRestaurantPlaceId());
        assertEquals(restaurantName, restaurant.getRestaurantName());
        assertEquals(restaurantAddress, restaurant.getRestaurantAddress());
        assertEquals(restaurantPhone, restaurant.getRestaurantPhone());
        assertEquals(restaurantWebSite, restaurant.getRestaurantWebSite());
        assertEquals(restaurantLocation, restaurant.getRestaurantLocation());
        assertEquals(restaurantOpeningHours, restaurant.getRestaurantOpeningHours());
        assertEquals(restaurantPhotoUrl, restaurant.getRestaurantPhotoUrl());
        assertEquals(restaurantRating, restaurant.getRestaurantRating(), restaurantRating);

    }

    @Test
    public void changeInfoUser_getCorrectInfo() throws Exception{
        restaurant.setCoworkerChoice(coworkersEatingHere);

        assertEquals(coworkersEatingHere, restaurant.getCoworkerChoice());
    }
}