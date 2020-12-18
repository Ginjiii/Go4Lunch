package com.example.go4lunch.goforlunch.repositories;

import androidx.arch.core.util.Function;
import androidx.multidex.BuildConfig;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.places.DetailsResponse;
import com.example.go4lunch.goforlunch.models.places.DetailsResult;
import com.example.go4lunch.goforlunch.models.places.RestaurantOpeningHours;
import com.example.go4lunch.goforlunch.models.places.RestaurantResponse;
import com.example.go4lunch.goforlunch.models.places.RestaurantResult;
import com.example.go4lunch.goforlunch.service.GooglePlacesService;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RestaurantRepository {

    private final GooglePlacesService googlePlacesService;

    private List<Restaurant> restaurants;
    private String restaurantSelected;
    private LatLng location;

    private static volatile RestaurantRepository INSTANCE;

    public static RestaurantRepository getInstance(GooglePlacesService googlePlacesService){
        if(INSTANCE == null){
            INSTANCE = new RestaurantRepository(googlePlacesService);
        }
        return INSTANCE;
    }

    private RestaurantRepository(GooglePlacesService googlePlacesService){
        this.googlePlacesService = googlePlacesService;
    }

    public Observable<RestaurantResponse> streamFetchRestaurantsNearBy(String location){
        return googlePlacesService.getNearbyRestaurant(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public Observable<DetailsResponse> streamFetchRestaurantDetails(String placeId){
        return googlePlacesService.getDetailsRestaurant(placeId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    public Observable<List<DetailsResponse>> streamFetchRestaurantDetails(){
        return googlePlacesService.getNearbyRestaurant(location)
                .map(DetailsResponse::getDetailsResult)
                .concatMap((Function<List<DetailsResult>, Observable<List<DetailsResponse>>>) results -> {
                    if(results != null && results.size() > 0) {
                        return Observable.fromIterable(results)
                                .concatMap((Function<DetailsResult, Observable<DetailsResponse>>) result -> streamFetchRestaurantDetails(RestaurantResult.getPlaceId()))
                                .toList()
                                .toObservable();
                    } else {
                        return null;
                    }
                });
    }

    public String getPhotoRestaurant(String photoReference){
        return String.format("%splace/photo?maxwidth=400&photoreference=%s&key=%s",
                BuildConfig.BUILD_TYPE, photoReference, BuildConfig.BUILD_TYPE);
    }

    public List<Restaurant> getRestaurantsLoaded(){
        return restaurants;
    }

    public void updateRestaurants(List<Restaurant> restaurants){
        this.restaurants = restaurants;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setRestaurantSelected(String restaurantUid){
        this.restaurantSelected = restaurantUid;
    }

    public String getRestaurantSelected(){
        return restaurantSelected;
    }

    public Restaurant createRestaurant(DetailsResult result){
        String uid = RestaurantResult.getPlaceId();
        String name = DetailsResult.getName();
        Double latitude = Double.valueOf(DetailsResult.getDetailsGeometry().getLocation().getLat());
        Double longitude = Double.valueOf(DetailsResult.getDetailsGeometry().getLocation().getLng());
        String photo = this.getPhotoRestaurant(result.getDetailsPhotos().get(0).getPhotoReference());
        String address = result.getAddress();
        RestaurantOpeningHours openingHours = result.getRestaurantOpeningHours();
        String webSite = result.getWebsite();
        String phoneNumber = result.getFormattedPhoneNumber();
        return new Restaurant(uid, name, latitude, longitude, address, openingHours, photo, rating, phoneNumber, webSite);
    }
}