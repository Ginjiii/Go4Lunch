package com.example.go4lunch.goforlunch.repositories;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


import com.example.go4lunch.goforlunch.models.Restaurant;

import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import com.example.go4lunch.goforlunch.service.GooglePlacesService;
import com.example.go4lunch.goforlunch.service.Retrofit;
import com.go4lunch.BuildConfig;
import com.google.firebase.firestore.GeoPoint;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.go4lunch.goforlunch.service.GooglePlacesService.BASE_URL_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.KEY_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.MAX_WIDTH_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.PHOTO_REF_GOOGLE;
import static com.example.go4lunch.goforlunch.utils.Utils.distFrom;

public class RestaurantRepository {

    public static final String TAG = RestaurantRepository.class.getSimpleName();

    private final String type = "restaurant";
    private final int proximityRadius = 2000;

    // Google / Retrofit declarations
    private final String key = BuildConfig.MAPS_API_KEY;
    private GooglePlacesService googlePlacesService;

    // MutableLiveData Declarations
    private final MutableLiveData<List<Restaurant>> restaurantList = new MutableLiveData<>();

    /**
     * Get the restaurant list from Google
     *
     * @param latitude
     * @param longitude
     * @return
     */
    public LiveData<List<Restaurant>> getGoogleRestaurantList(double latitude, double longitude) {
        List<Restaurant> restaurants = new ArrayList<>();
        googlePlacesService = Retrofit.getClient(BASE_URL_GOOGLE).create(GooglePlacesService.class);
        Call<com.example.go4lunch.goforlunch.models.places.Restaurant> restaurantCall = googlePlacesService.getNearByPlaces(key, type,
                latitude + "," + longitude, proximityRadius);
        restaurantCall.enqueue(new Callback<com.example.go4lunch.goforlunch.models.places.Restaurant>() {
            @Override
            public void onResponse(@NonNull Call<com.example.go4lunch.goforlunch.models.places.Restaurant> call, @NonNull Response<com.example.go4lunch.goforlunch.models.places.Restaurant> response) {
                if (response.isSuccessful()) {
                    List<com.example.go4lunch.goforlunch.models.places.Restaurant.Result> restaurantResponse = Objects.requireNonNull(response.body()).getResults();
                    Log.d(TAG, "onResponse: " + response.body().getResults());
                    for (com.example.go4lunch.goforlunch.models.places.Restaurant.Result restaurant : restaurantResponse) {
                        String photo = null;
                        double rating = 0.0;
                        String address = null;
                        RestaurantDetail.OpeningHours openingHours = null;
                        if (restaurant.getPhotos() != null && restaurant.getPhotos().size() > 0) {
                            photo = getPhoto(restaurant.getPhotos().get(0).getPhotoReference(), 400, key);
                        }
                        if (restaurant.getVicinity() != null) {
                            address = restaurant.getVicinity();
                        }
                        if (restaurant.getRating() != null) {
                            rating = restaurant.getRating();
                        }

                        if(restaurant.getOpeningHours() != null)
                        {
                            Log.d(TAG, "onResponse: OPENING " + restaurant.getOpeningHours().getOpenNow());
                            com.example.go4lunch.goforlunch.models.places.Restaurant.OpeningHours apiOpeningHours = restaurant.getOpeningHours();

                            if(apiOpeningHours.getOpenNow())
                            {
                                Log.d(TAG, "onResponse: OPENING = true" + restaurant.getOpeningHours().getOpenNow());
                                openingHours = new RestaurantDetail.OpeningHours(true,null,null);
                            }
                        }

                        double longUser = longitude;
                        double latUser=latitude;
                        double lonRestaurant=restaurant.getGeometry().getLocation().getLng();
                        double latRestaurant=restaurant.getGeometry().getLocation().getLat();
                        float[] distance = new float[1];
                       Location.distanceBetween(latUser,longUser,latRestaurant,lonRestaurant, distance);


                       //restaurant.getGeometry()
                        Log.d(TAG, "distance " +distance);


                        Restaurant restaurantToAdd = new Restaurant(
                                restaurant.getPlaceId(),
                                restaurant.getName(),
                                address,
                                null,
                                null,
                                null,
                                rating,
                                photo,
                                restaurant.getGeometry().getLocation(),
                                openingHours,
                                distance[0],
                                null
                        );
                        restaurants.add(restaurantToAdd);
                    }

                    restaurantList.postValue(restaurants);
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.example.go4lunch.goforlunch.models.places.Restaurant> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

        return restaurantList;
    }

    /**
     * Get the photo from Google
     *
     * @param photoReference : string : photo reference of the restaurant
     * @param maxWidth       : int : max width of the photo
     * @param key            : string : google key
     * @return : string : the link to the photo
     */
    public static String getPhoto(String photoReference, int maxWidth, String key) {
        return BASE_URL_GOOGLE + PHOTO_REF_GOOGLE + photoReference
                + MAX_WIDTH_GOOGLE + maxWidth + KEY_GOOGLE + key;
    }

}