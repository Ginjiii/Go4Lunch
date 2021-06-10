package com.example.go4lunch.goforlunch.repositories;

import android.location.Location;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.models.ApiDetailsRestaurantResponse;
import com.example.go4lunch.goforlunch.models.ApiRestaurantResponse;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.RestaurantApi;
import com.example.go4lunch.goforlunch.service.GooglePlacesService;
import com.example.go4lunch.goforlunch.service.Retrofit;
import com.example.go4lunch.goforlunch.utils.Utils;
import com.go4lunch.BuildConfig;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.go4lunch.goforlunch.service.GooglePlacesService.BASE_URL_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.KEY_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.MAX_WIDTH_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.PHOTO_REF_GOOGLE;

public class RestaurantRepository {

    private static final String COLLECTION_NAME = "restaurant";
    private final CollectionReference restaurantCollection;
    private RestaurantRepository restaurantRepository;
    private Restaurant restaurant;

    public static final String TAG = RestaurantRepository.class.getSimpleName();

    private double longUser = 0.0;
    private double latUser = 0.0;

    private final String key = BuildConfig.MAPS_API_KEY;

    private final MutableLiveData<List<Restaurant>> restaurantListMutableLiveData = new MutableLiveData<>();
    private final List<Restaurant> restaurants = new ArrayList<>();

    private static volatile RestaurantRepository INSTANCE;

    public RestaurantRepository() {this.restaurantCollection = getRestaurantCollection();}

    public static RestaurantRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RestaurantRepository();
        }
        return INSTANCE;
    }

    private CollectionReference getRestaurantCollection() {
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    /**
     * Get the restaurant list from Google
     */
    public LiveData<List<Restaurant>> getGoogleRestaurantList(double latitude, double longitude) {
        longUser = longitude;
        latUser = latitude;
        GooglePlacesService googlePlacesService = Retrofit.getClient().create(GooglePlacesService.class);
        String type = "restaurant";
        int proximityRadius = 300;
        Call<ApiRestaurantResponse> restaurantCall = googlePlacesService.getNearByPlaces(key, type,
                latitude + "," + longitude, proximityRadius);
        restaurantCall.enqueue(new Callback<ApiRestaurantResponse>() {
            @Override
            public void onResponse(@NotNull Call<ApiRestaurantResponse> call, @NotNull Response<ApiRestaurantResponse> response) {
                Log.d(TAG, "onResponse getGoogleRestaurantList");
                for (RestaurantApi restaurant : response.body().getResults()) {
                    getGoogleRestaurantDetail(restaurant.getPlaceId());
                }
            }

            @Override
            public void onFailure(@NotNull Call<ApiRestaurantResponse> call, @NotNull Throwable throwable) {
                Log.e(TAG, "onFailure getGoogleRestaurantList: " + throwable.getMessage());
            }
        });

        return restaurantListMutableLiveData;
    }

    public LiveData<Restaurant> getGoogleRestaurantDetail(String placeId) {
        MutableLiveData<Restaurant> restaurantLiveData = new MutableLiveData<>();

        getRestaurantFromFirebase(placeId).addOnSuccessListener(documentSnapshot -> {
            Restaurant restaurantFirebase = documentSnapshot.toObject(Restaurant.class);
            if (restaurantFirebase != null) {
                restaurants.add(restaurantFirebase);
                restaurantLiveData.setValue(restaurantFirebase);
                restaurantListMutableLiveData.postValue(restaurants);

            }else {
                GooglePlacesService googlePlacesService = Retrofit.getClient().create(GooglePlacesService.class);
                String fields = "name,address_components,adr_address,formatted_address,formatted_phone_number,geometry,icon,id,international_phone_number,rating,website,utc_offset,opening_hours,photo,vicinity,place_id";

                Call<ApiDetailsRestaurantResponse> restaurantDetailCall = googlePlacesService.getRestaurantDetail(key, placeId, fields);

                restaurantDetailCall.enqueue(new Callback<ApiDetailsRestaurantResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<ApiDetailsRestaurantResponse> call, @NotNull Response<ApiDetailsRestaurantResponse> response) {
                        Log.d(TAG, "onResponse getGoogleRestaurantDetail");

                        if (response.isSuccessful() && response.body().getResult() != null) {
                            Restaurant restaurant = createRestaurant(response.body().getResult());
                            saveRestaurantInFirestore(restaurant);
                            restaurants.add(restaurant);
                            restaurantLiveData.setValue(restaurant);
                            restaurantListMutableLiveData.postValue(restaurants);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<ApiDetailsRestaurantResponse> call, @NotNull Throwable throwable) {
                        Log.e(TAG, "onFailure getGoogleRestaurantDetail");
                    }
                });

            }


        });


    return restaurantLiveData;
    }

    private Restaurant createRestaurant(RestaurantApi result) {
        String uid = result.getPlaceId();
        String name = result.getName();
        double latitude = result.getGeometry().getLocation().getLat();
        double longitude = result.getGeometry().getLocation().getLng();
        String photo = (result.getPhotos() != null) ? getPhoto(result.getPhotos().get(0).getPhotoReference()) : null;
        String address = result.getVicinity();
        int distance = (int) getDistance(latitude, longitude);
        int openingHours = Utils.getOpeningTime(result.getOpeningHours());
        String webSite = result.getWebsite();
        String phoneNumber = result.getPhoneNumber();
        float rating = result.getRating();
        return new Restaurant(uid, name, latitude, longitude, address, openingHours, distance, photo, rating, phoneNumber, webSite);
    }

    /**
     * Save the restaurant in Firestore
     */
    public Task<Void> saveRestaurantInFirestore(Restaurant restaurant){
    this.restaurant = restaurant;
    return restaurantCollection.document(restaurant.getRestaurantID()).set(restaurant);
    }

    public Task<DocumentSnapshot> getRestaurantFromFirebase(String restaurantID) {
        return restaurantCollection.document(restaurantID).get();
    }

    public Task<Void> createRestaurantInFirestore(String restaurantID, String name, Double latitude, Double longitude, @Nullable String address,
                                                  int openingHours, int distance, @Nullable String photoReference, float rating, String phoneNumber, String webSite) {
        Restaurant newRestaurant = new Restaurant(restaurantID, name, latitude, longitude, address, openingHours, distance, photoReference, rating, phoneNumber, webSite);
        return getRestaurantCollection().document(restaurantID).set(newRestaurant);
    }

    /**
     * Get the photo from Google
     */
    private String getPhoto(String photoReference) {
        return BASE_URL_GOOGLE + PHOTO_REF_GOOGLE + photoReference
                + MAX_WIDTH_GOOGLE + 400 + KEY_GOOGLE + BuildConfig.MAPS_API_KEY;
    }

    /**
     * Calculate the distance
     */
    private float getDistance(double latRestaurant, double lonRestaurant) {
        float[] distance = new float[1];
        Location.distanceBetween(latUser, longUser, latRestaurant, lonRestaurant, distance);
        return distance[0];
    }
}