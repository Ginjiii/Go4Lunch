package com.example.go4lunch.goforlunch.service;

import com.example.go4lunch.goforlunch.models.ApiDetailsRestaurantResponse;
import com.example.go4lunch.goforlunch.models.ApiRestaurantResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GooglePlacesService {

    String BASE_URL_GOOGLE = "https://maps.googleapis.com/maps/api/place/";
    String PHOTO_REF_GOOGLE = "photo?photoreference=";
    String MAX_WIDTH_GOOGLE = "&maxwidth=";
    String KEY_GOOGLE = "&key=";

    @GET("nearbysearch/json")
    Call<ApiRestaurantResponse> getNearByPlaces(
            @Query("key") String key,
            @Query("type") String type,
            @Query("location") String location,
            @Query("radius") int radius);

    @GET("details/json")
    Call<ApiDetailsRestaurantResponse> getRestaurantDetail(
            @Query("key") String pKey,
            @Query("place_id") String pPlaceId,
            @Query("fields") String pFields
    );
}