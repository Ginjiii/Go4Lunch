package com.example.go4lunch.goforlunch.repositories;

import android.location.Location;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import com.example.go4lunch.goforlunch.service.GooglePlacesService;
import com.example.go4lunch.goforlunch.service.Retrofit;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailViewModel;
import com.go4lunch.BuildConfig;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static com.example.go4lunch.goforlunch.service.Go4Lunch.api;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.BASE_URL_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.KEY_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.MAX_WIDTH_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.PHOTO_REF_GOOGLE;

public class RestaurantRepository {

    public static final String TAG = RestaurantRepository.class.getSimpleName();

    private final String type = "restaurant";
    private final int proximityRadius = 300;
    private List<Restaurant> restaurants;
    private String restaurantSelected;

    RestaurantDetailViewModel restaurantDetailViewModel;

    // Google / Retrofit declarations
    private final String key = BuildConfig.MAPS_API_KEY;
    private GooglePlacesService googlePlacesService;

    // MutableLiveData Declarations
    private final MutableLiveData<List<Restaurant>> restaurantList = new MutableLiveData<>();
    private final MutableLiveData<Restaurant> restaurantListDetail = new MutableLiveData<>();

    private List<Restaurant> mRestaurantList = new ArrayList<>();
    private List<Restaurant> mRestaurantListDetail = new ArrayList<>();

    private static volatile RestaurantRepository INSTANCE;


    public static RestaurantRepository getInstance(){
        if(INSTANCE == null){
            INSTANCE = new RestaurantRepository();
        }
        return INSTANCE;
    }
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

                        double longUser = longitude;
                        double latUser=latitude;
                        double lonRestaurant=restaurant.getGeometry().getLocation().getLng();
                        double latRestaurant=restaurant.getGeometry().getLocation().getLat();
                        float[] distance = new float[1];
                        Location.distanceBetween(latUser,longUser,latRestaurant,lonRestaurant, distance);

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
                                null,
                                distance[0],
                                null
                        );
                        RestaurantDetail restaurantDetail = getGoogleRestaurantDetailList(restaurant.getPlaceId(), restaurants, restaurantToAdd);
                        //if (dRestaurant!= null && dRestaurant. != null)
                        //{
                        RestaurantDetail mRestaurantDetail =restaurantDetail;
                        if (mRestaurantDetail != null && mRestaurantDetail.getResult() != null ){
                            restaurantToAdd.setRestaurantOpeningHours(mRestaurantDetail.getResult().getOpeningHours());
                            Log.d(TAG, "onResponse: detailRestaurant"+mRestaurantDetail.getResult().getName());
                        }
                        restaurants.add(restaurantToAdd);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.example.go4lunch.goforlunch.models.places.Restaurant> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure: " + t.toString());
            }
        });

        return restaurantList;
    }

    public RestaurantDetail getGoogleRestaurantDetailList(String placeId, List<Restaurant> restaurants, Restaurant restaurantToAdd)  {

        GooglePlacesService googlePlacesService = Retrofit.getClient(BASE_URL_GOOGLE).create(GooglePlacesService.class);
        String fields = "name,address_components,adr_address,formatted_address,formatted_phone_number,geometry,icon,id,international_phone_number,rating,website,utc_offset,opening_hours,photo,vicinity,place_id";

        Call<RestaurantDetail> restaurantDetailCall = googlePlacesService.getRestaurantDetail(key, placeId, fields);
        Log.d(TAG, "getGoogleRestaurantDetailList: ");

        restaurantDetailCall.enqueue(new Callback<RestaurantDetail>() {
            @Override
            public void onResponse(Call<RestaurantDetail> call, Response<RestaurantDetail> response) {
                Log.d(TAG, "onResponse:  restaurantDetailList");
                Log.d(TAG, "restaurantList start "+mRestaurantList.size());

                if (response.isSuccessful()) {
                    RestaurantDetail.Result restaurantDetailResponse = Objects.requireNonNull(response.body().getResult());

                    String photo = null;
                    double rating = 0.0;
                    String address = null;
                    com.example.go4lunch.goforlunch.models.common.Location mLocation = null;
                    RestaurantDetail.OpeningHours openingHours = null;
                    if (restaurantDetailResponse.getPhotos() != null && restaurantDetailResponse.getPhotos().size() > 0) {
                        photo = getPhoto(restaurantDetailResponse.getPhotos().get(0).getPhotoReference(), 400, key);
                    }

                    if (restaurantDetailResponse.getGeometry().getLocation() != null) {
                        mLocation = new com.example.go4lunch.goforlunch.models.common.Location();
                        if (restaurantDetailResponse.getGeometry().getLocation().getLat() != null)
                        {
                            mLocation.setLat(restaurantDetailResponse.getGeometry().getLocation().getLat());
                        }
                        if (restaurantDetailResponse.getGeometry().getLocation().getLng() != null)
                        {
                            mLocation.setLng(restaurantDetailResponse.getGeometry().getLocation().getLng());
                        }
                    }

                    if (restaurantDetailResponse.getVicinity() != null) {
                        address = restaurantDetailResponse.getVicinity();
                    }
                    if (restaurantDetailResponse.getRating() != null) {
                        rating = restaurantDetailResponse.getRating();
                    }
                    if(restaurantDetailResponse.getOpeningHours() != null)
                    {
                        Log.d(TAG, "onResponse: getOpeningHours"+restaurantDetailResponse.getOpeningHours());
                        openingHours = restaurantDetailResponse.getOpeningHours();
                    }
                    Log.d(TAG, "onResponse restaurantDetail: "+restaurantDetailResponse.getName());
                    Restaurant modelRestaurant = new Restaurant(
                            restaurantDetailResponse.getPlaceId(),
                            restaurantDetailResponse.getName(),
                            address,
                            restaurantDetailResponse.getFormattedPhoneNumber(),
                            restaurantDetailResponse.getWebsite(),
                            null,
                            rating,
                            photo,
                            mLocation,
                            openingHours,
                            0,
                            null
                    );

                    restaurantToAdd.setRestaurantOpeningHours(restaurantDetailResponse.getOpeningHours());
                    Log.d(TAG, "restaurantList "+mRestaurantList.size());

                    int index = restaurants.indexOf(restaurantToAdd);
                    if (index > 0){
                        restaurants.set(index,modelRestaurant);
                    }

                }
                restaurantList.postValue(restaurants);
            }
            @Override
            public void onFailure(Call<RestaurantDetail> call, Throwable t) {

            }
        });
        return null;
    }
    public MutableLiveData<Restaurant> getGoogleRestaurantDetail(String placeId)  {

        GooglePlacesService googlePlacesService = Retrofit.getClient(BASE_URL_GOOGLE).create(GooglePlacesService.class);
        String fields = "name,address_components,adr_address,formatted_address,formatted_phone_number,geometry,icon,id,international_phone_number,rating,website,utc_offset,opening_hours,photo,vicinity,place_id";

        Call<RestaurantDetail> restaurantDetailCall = googlePlacesService.getRestaurantDetail(key, placeId, fields);
        Log.d(TAG, "getGoogleRestaurantDetailList: ");

        restaurantDetailCall.enqueue(new Callback<RestaurantDetail>() {
            @Override
            public void onResponse(Call<RestaurantDetail> call, Response<RestaurantDetail> response) {
                Log.d(TAG, "onResponse:  restaurantDetailList");
                Log.d(TAG, "restaurantList start "+mRestaurantList.size());

                if (response.isSuccessful()) {
                    RestaurantDetail.Result restaurantDetailResponse = Objects.requireNonNull(response.body().getResult());

                    String photo = null;
                    double rating = 0.0;
                    String address = null;
                    com.example.go4lunch.goforlunch.models.common.Location mLocation = null;
                    RestaurantDetail.OpeningHours openingHours = null;
                    if (restaurantDetailResponse.getPhotos() != null && restaurantDetailResponse.getPhotos().size() > 0) {
                        photo = getPhoto(restaurantDetailResponse.getPhotos().get(0).getPhotoReference(), 400, key);
                    }

                    if (restaurantDetailResponse.getGeometry().getLocation() != null) {
                        mLocation = new com.example.go4lunch.goforlunch.models.common.Location();
                        if (restaurantDetailResponse.getGeometry().getLocation().getLat() != null)
                        {
                            mLocation.setLat(restaurantDetailResponse.getGeometry().getLocation().getLat());
                        }
                        if (restaurantDetailResponse.getGeometry().getLocation().getLng() != null)
                        {
                            mLocation.setLng(restaurantDetailResponse.getGeometry().getLocation().getLng());
                        }
                    }

                    if (restaurantDetailResponse.getVicinity() != null) {
                        address = restaurantDetailResponse.getVicinity();
                    }
                    if (restaurantDetailResponse.getRating() != null) {
                        rating = restaurantDetailResponse.getRating();
                    }
                    if(restaurantDetailResponse.getOpeningHours() != null)
                    {
                        Log.d(TAG, "onResponse: getOpeningHours"+restaurantDetailResponse.getOpeningHours());
                        openingHours = restaurantDetailResponse.getOpeningHours();
                    }
                    Log.d(TAG, "onResponse restaurantDetail: "+restaurantDetailResponse.getName());
                    Restaurant modelRestaurant = new Restaurant(
                            restaurantDetailResponse.getPlaceId(),
                            restaurantDetailResponse.getName(),
                            address,
                            restaurantDetailResponse.getFormattedPhoneNumber(),
                            restaurantDetailResponse.getWebsite(),
                            null,
                            rating,
                            photo,
                            mLocation,
                            openingHours,
                            0,
                            null
                    );
                    Log.d(TAG, "restaurantList "+mRestaurantListDetail.size());
                    restaurantListDetail.postValue(modelRestaurant);
                }
            }
            @Override
            public void onFailure(Call<RestaurantDetail> call, Throwable t) {
            }
        });
        return restaurantListDetail;
    }

    private void displayRestaurantList(List<Restaurant> mRestaurantListDetail) {
        Log.d(TAG, "displayRestaurantList: ");
        api.setRestaurantList(mRestaurantList);
        restaurantList.postValue(mRestaurantListDetail);
    }

    public List<Restaurant> getRestaurantsLoaded(){
        return restaurants;
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

    public void setRestaurantSelected(String restaurantUid){
        this.restaurantSelected = restaurantUid;
    }

    public String getRestaurantSelected(){
        return restaurantSelected;
    }
}