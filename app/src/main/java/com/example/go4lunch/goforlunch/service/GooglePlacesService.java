package com.example.go4lunch.goforlunch.service;

import com.example.go4lunch.goforlunch.models.places.Restaurant;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesService {

    String BASE_URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/";
// https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=1500&type=restaurant&key=AIzaSyBJwB-MzOX2VpT79du_7e8idBPfaZtfcoA
    String PHOTO_REF_GOOGLE = "photo?photoreference=";
    String MAX_WIDTH_GOOGLE = "&maxwidth=";
    String KEY_GOOGLE = "&key=";

    @GET("nearbysearch/json")
    Call<Restaurant> getNearByPlaces(
            @Query("key") String key,
            @Query("type") String type,
            @Query("location") String location,
            @Query("radius") int radius);


    @GET("details/json")
    Call<RestaurantDetail> getRestaurantDetail(
            @Query("key") String pKey,
            @Query("place_id") String pPlaceId,
            @Query("fields") String pFields
    );
}