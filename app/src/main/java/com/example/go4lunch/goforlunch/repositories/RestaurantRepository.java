package com.example.go4lunch.goforlunch.repositories;

import android.annotation.SuppressLint;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.go4lunch.goforlunch.models.FirestoreUpdate;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import com.example.go4lunch.goforlunch.service.GooglePlacesService;
import com.example.go4lunch.goforlunch.service.Retrofit;
import com.example.go4lunch.goforlunch.utils.Go4LunchHelper;
import com.go4lunch.BuildConfig;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.model.AutocompletePrediction;
import com.google.android.libraries.places.api.model.AutocompleteSessionToken;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.go4lunch.goforlunch.service.Go4Lunch.ERROR_ON_FAILURE_LISTENER;
import static com.example.go4lunch.goforlunch.service.Go4Lunch.PREF_KEY_BOUND_RADIUS;
import static com.example.go4lunch.goforlunch.service.Go4Lunch.PREF_KEY_PLACE_DETAIL_FIELDS;
import static com.example.go4lunch.goforlunch.service.Go4Lunch.PREF_KEY_RADIUS;
import static com.example.go4lunch.goforlunch.service.Go4Lunch.PREF_KEY_TYPE_GOOGLE_SEARCH;
import static com.example.go4lunch.goforlunch.service.Go4Lunch.api;
import static com.example.go4lunch.goforlunch.service.Go4LunchService.PREF_KEY_LATITUDE;
import static com.example.go4lunch.goforlunch.service.Go4LunchService.PREF_KEY_LONGITUDE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.BASE_URL_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.KEY_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.MAX_WIDTH_GOOGLE;
import static com.example.go4lunch.goforlunch.service.GooglePlacesService.PHOTO_REF_GOOGLE;
import static com.example.go4lunch.goforlunch.utils.PreferencesHelper.preferences;


public class RestaurantRepository {

    public static final String TAG = "TAG_REPO_RESTAURANT";

    public static final String RESTAURANT = "RESTAURANT";
    public static final String TYPE_DEF_VALUE = "restaurant";

    /**
     * Firebase declarations
     */
    private final FirebaseFirestore myDb = FirebaseFirestore.getInstance();
    private final CollectionReference restaurantRef = myDb.collection(String.valueOf(Restaurant.Fields.Restaurant));
    private final CollectionReference restaurantLastUpdRef = myDb.collection(String.valueOf(FirestoreUpdate.RestaurantLastUpdate));
    /**
     * Google  / Retrofit declarations
     */
    private final String key = BuildConfig.MAPS_API_KEY;
    private GooglePlacesService googlePlacesService;

    /**
     * MutableLiveData Declarations
     */
    private final MutableLiveData<List<Restaurant>> restaurantList = new MutableLiveData<>();
    private final MutableLiveData<Restaurant> restaurant = new MutableLiveData<>();
    private final MutableLiveData<List<Restaurant>> autocompleteRestaurantList = new MutableLiveData<>();

 //   private Date firestoreLastUpdate = new Date();

    private Location fusedLocationProvider;
    private Double latitude;
    private Double longitude;
    private boolean isFromAutoComplete = false;

    private List<Restaurant> mRestaurantList = new ArrayList<>();
    private List<Restaurant> restaurantListDetail = new ArrayList<>();

    /**
     * Get from Firestore the restaurant detail
     * @return : mutable live data object : restaurant information
     */
    public MutableLiveData<Restaurant> getRestaurantDetail() {
        String mRestaurantId = api.getRestaurantId();
        restaurantRef.document(mRestaurantId)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Restaurant mRestaurant = task.getResult().toObject(Restaurant.class);
                        api.setRestaurant(mRestaurant);
                        restaurant.setValue(mRestaurant);
                    }
                })
                .addOnFailureListener(e -> Log.e(TAG, ERROR_ON_FAILURE_LISTENER + e));
        return restaurant;
    }

    /**
     * Manage the recovery of the restaurant list
     * if it's the first start of the application the data are retrieved from Google and Firestore is updated
     * if it's the first connection on a monday, the data are retrieved from Google and Firestore is updated
     * (the Firestore last update date is before now)
     * in the other case, the data are retrieved from Firestore
     * @return : mutable live data list object : list of the restaurants
     */
    public MutableLiveData<List<Restaurant>> getRestaurantList() {
        isFromAutoComplete = false;
        fusedLocationProvider = Go4LunchHelper.setCurrentLocation(latitude, longitude);
        restaurantLastUpdRef.document(String.valueOf(FirestoreUpdate.dateLastUpdateListRestaurant))
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot result = task.getResult();
      //                  Timestamp timestamp;
                        if (result.getData() != null) {
       //                     timestamp = (Timestamp) result.getData().get(String.valueOf(FirestoreUpdate.restaurantLastUpdateList));
     //                       if (timestamp != null) {
      //                          Date date = new Date();
       //                         Calendar calendar = Calendar.getInstance();
       //                         calendar.setTime(date);
        //                        date = calendar.getTime();

         //                       firestoreLastUpdate = timestamp.toDate();

                                //Compare the only dates (today and firestore date)
                                @SuppressLint("SimpleDateFormat")
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

                                // The dates are different and we are on a monday we retrieve information from Google
           //                     if ((!sdf.format(firestoreLastUpdate).equals(sdf.format(date))) && (Go4LunchHelper.getCurrentDayInt() == 2)) {
                                    // 2 = Monday
             //                       getGoogleRestaurantList();
              //                  } else {
               //                     getFirestoreRestaurantList();
                                }
                            }
               //         } else {
                            getGoogleRestaurantList();
                        });
              //      } else {
               //         firestoreLastUpdate = null;
                //        getFirestoreRestaurantList();
               //     }
               // });
        return restaurantList;
    }

    /**
     * Get the restaurant list from Firestore
     */
 //   private void getFirestoreRestaurantList() {
//
 //       restaurantRef.get()
 //               .addOnCompleteListener(task -> {
 //                   if (task.isSuccessful()) {
 //                       List<Restaurant> restaurantList = (Objects.requireNonNull(task.getResult()).toObjects(Restaurant.class));
 //                       prepareAndSendRestaurantListForDisplay(restaurantList);
 //                   }
 //               })
 //               .addOnFailureListener(e -> Log.e(TAG, ERROR_ON_FAILURE_LISTENER + e));
 //   }

    /**
     * Get the restaurant list from Google
     * @return
     */
    public LiveData<List<Restaurant>> getGoogleRestaurantList() {

        int proximityRadius = preferences.getInt(PREF_KEY_RADIUS, 150);
        String type = preferences.getString(PREF_KEY_TYPE_GOOGLE_SEARCH, TYPE_DEF_VALUE);

        googlePlacesService = Retrofit.getClient(BASE_URL_GOOGLE).create(GooglePlacesService.class);

        Call<com.example.go4lunch.goforlunch.models.places.Restaurant> restaurantCall = googlePlacesService.getNearByPlaces(key, type,
                latitude + "," + longitude, proximityRadius);

        restaurantCall.enqueue(new Callback<com.example.go4lunch.goforlunch.models.places.Restaurant>() {
            @Override
            public void onResponse(@NonNull Call<com.example.go4lunch.goforlunch.models.places.Restaurant> call, @NonNull Response<com.example.go4lunch.goforlunch.models.places.Restaurant> response) {
                if (response.isSuccessful()) {
                    List<com.example.go4lunch.goforlunch.models.places.Restaurant.Result> restaurantResponse = Objects.requireNonNull(response.body()).getResults();

                    for (com.example.go4lunch.goforlunch.models.places.Restaurant.Result restaurant : restaurantResponse) {
                        getGoogleDetailRestaurant(restaurant.getPlaceId(), restaurantResponse.size());
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<com.example.go4lunch.goforlunch.models.places.Restaurant> call, @NonNull Throwable t) {
                Log.e(TAG, ERROR_ON_FAILURE_LISTENER + t.toString());
            }
        });
        return null;
    }

    /**
     * Get the restaurant detail information from Google
     * @param restaurantListId : string : id of the restaurant
     * @param responseSize : int : size of the restaurant list
     */
    public void getGoogleDetailRestaurant(String restaurantListId, int responseSize) {
        String fields = preferences.getString(PREF_KEY_PLACE_DETAIL_FIELDS, null);

        googlePlacesService = Retrofit.getClient(BASE_URL_GOOGLE).create(GooglePlacesService.class);

        Call<RestaurantDetail> restaurantDetailCall = googlePlacesService.getRestaurantDetail(key,
                restaurantListId, fields);

        restaurantDetailCall.enqueue(new Callback<RestaurantDetail>() {
            @Override
            public void onResponse(@NonNull Call<RestaurantDetail> call, @NonNull Response<RestaurantDetail> response) {
                if (response.isSuccessful()) {
                    RestaurantDetail.Result restaurantDetailResponse = Objects.requireNonNull(response.body()).getResult();

                    //Manage restaurant information
                    String photo = null;
                    double rating = 0.0;
                    String address = null;
                    RestaurantDetail.Location location = null;
                    String name = restaurantDetailResponse.getName();

                    if (restaurantDetailResponse.getPhotos() != null && restaurantDetailResponse.getPhotos().size() > 0) {
                        photo = getPhoto(restaurantDetailResponse.getPhotos().get(0).getPhotoReference(), 400, key);
                    }
                    if (restaurantDetailResponse.getGeometry().getLocation() != null) {
                        location = restaurantDetailResponse.getGeometry().getLocation();
                    }
                    if (restaurantDetailResponse.getVicinity() != null) {
                        address = restaurantDetailResponse.getVicinity();
                    }
                    if (restaurantDetailResponse.getRating() != null) {
                        rating = restaurantDetailResponse.getRating();
                    }

                    Restaurant restaurant = new Restaurant(
                            restaurantListId, name, address, null, null, null,
                            rating, photo, location, null, 0, null
                    );

                    restaurant.setRestaurantOpeningHours(restaurantDetailResponse.getOpeningHours());
                    restaurant.setRestaurantWebSite(restaurantDetailResponse.getWebsite());
                    restaurant.setRestaurantPhone(restaurantDetailResponse.getFormattedPhoneNumber());
                    restaurantListDetail.add(restaurant);

                    //backupDataInFirestore(responseSize);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RestaurantDetail> call, @NonNull Throwable t) {
                Log.e(TAG, ERROR_ON_FAILURE_LISTENER + t.getMessage());
            }
        });
    }

    /**
     * Backup the restaurant list in Firestore if all the restaurant data have been retrieved
     * @param responseSize : int : size of the restaurant list retrieve from Google
     */
 //   private void backupDataInFirestore(int responseSize) {
//
 //       if (restaurantListDetail.size() == responseSize) {
 //           for (Restaurant restaurant : restaurantListDetail) {
 //               saveRestaurantInFirestore(restaurant);
 //           }
 //           updateLastUpdateDateInFirestore();
//
 //           prepareAndSendRestaurantListForDisplay(restaurantListDetail);
 //       }
 //   }
//
 //   /**
 //    * Save the restaurant in Firestore
 //    * @param restaurant : object : restaurant to be saved
 //    */
 //   public void saveRestaurantInFirestore(Restaurant restaurant) {
//
 //       restaurantRef.document(restaurant.getRestaurantPlaceId())
 //               .get()
 //               .addOnSuccessListener(documentSnapshot ->
 //                       restaurantRef.document(restaurant.getRestaurantPlaceId())
 //                               .set(restaurant)
 //                               .addOnSuccessListener(documentReference ->
 //                                       Log.d(TAG, "onSuccess : Document saved " + restaurant.getRestaurantName()))
 //                               .addOnFailureListener(e ->
 //                                       Log.e(TAG, ERROR_ON_FAILURE_LISTENER + e)))
 //               .addOnFailureListener(e -> Log.e(TAG, ERROR_ON_FAILURE_LISTENER + e));
 //   }

    /**
     * Prepare the restaurant list to be display and send it back to the view model
     * @param prepareRestaurantList : list object : restaurant list to be prepared
     */
 //   private void prepareAndSendRestaurantListForDisplay(List<Restaurant> prepareRestaurantList) {
//
 //       mRestaurantList = updateDistanceInRestaurantList(prepareRestaurantList);
 //       Collections.sort(mRestaurantList);
 //       Collections.reverse(mRestaurantList);
 //       if (!isFromAutoComplete) {
 //           removeRestaurantOutOfRadiusFromList(mRestaurantList);
 //       }
 //       api.setRestaurantList(mRestaurantList);
//
 //       if (isFromAutoComplete) {
 //           autocompleteRestaurantList.setValue(mRestaurantList);
 //       } else {
 //           restaurantList.setValue(mRestaurantList);
 //       }
 //   }

    /**
     * Remove the restaurant which have a distance above the radius
     * @param restaurantList : list object : restaurant list
     */
    private void removeRestaurantOutOfRadiusFromList(List<Restaurant> restaurantList) {
        int proximityRadius = preferences.getInt(PREF_KEY_RADIUS, 150);
        try {
            for (Iterator<Restaurant> restaurantIterator = mRestaurantList.iterator(); restaurantIterator.hasNext(); ) {
                Restaurant restaurant = restaurantIterator.next();
                if (restaurant.getRestaurantDistance() > proximityRadius) {
                    restaurantIterator.remove();
                }
            }
        } catch (java.util.ConcurrentModificationException exception) {
            Log.e(TAG, "removeRestaurantOutOfRadiusFromList: " + exception);
        }

        mRestaurantList = restaurantList;
    }

    /**
     * Update in the LiveData the distance of the restaurant
     * @param mRestaurantList : list object: restaurant list
     * @return : list object : restaurant list
     */
  //  private List<Restaurant> updateDistanceInRestaurantList(List<Restaurant> mRestaurantList) {
  //      for (Restaurant restaurant : mRestaurantList) {
  //          int distance = Go4LunchHelper.getRestaurantDistanceToCurrentLocation(
  //                  fusedLocationProvider, restaurant.getRestaurantLocation());
  //          String newDistance = Go4LunchHelper.convertDistance(distance);
  //          restaurant.setRestaurantDistance(distance);
  //          restaurant.setRestaurantDistanceText(newDistance);
  //      }
  //      return mRestaurantList;
  //  }

    /**
     * Update the last update date of and in Firestore
     */
 //   private void updateLastUpdateDateInFirestore() {
//
 //       Date date = new Date();
 //       Map<String, Object> lastUpdate = new HashMap<>();
 //       lastUpdate.put(String.valueOf(FirestoreUpdate.restaurantLastUpdateList), date);
 //       restaurantLastUpdRef.document(String.valueOf(FirestoreUpdate.dateLastUpdateListRestaurant))
 //               .set(lastUpdate)
 //               .addOnSuccessListener(documentReference ->
 //                       Log.d(TAG, "onSuccess : date updated "))
 //               .addOnFailureListener(e ->
 //                       Log.e(TAG, ERROR_ON_FAILURE_LISTENER + e));
 //   }

    /**
     * Get the restaurant list for the autocomplete prediction request
     * @param placesClient : object : placesClient
     * @param query : strung : query to be put in the request to autocomplete
     * @return : mutable live data list object : restaurant list
     */
    public MutableLiveData<List<Restaurant>> getAutocompleteRestaurantList(PlacesClient placesClient, String query) {

        isFromAutoComplete = true;

        manageAutocomplete(placesClient, query);

        return autocompleteRestaurantList;
    }

    /**
     * Manage the autocomplete prediction: generate the request, get and filter the result
     * Call also the data from Firestore to be compared if all the information are already stored
     * @param placesClient : object : placesClient
     * @param query : string : query to be put in the request to autocomplete
     */
    private void manageAutocomplete(PlacesClient placesClient, String query) {
        double lat = api.getLocation().getLatitude();
        double lng = api.getLocation().getLongitude();
        LatLng latLng = new LatLng(lat, lng);

        // Create a new token for the autocomplete session. Pass this to FindAutocompletePredictionsRequest,
        // and once again when the user makes a selection (for example when calling fetchPlace()).
        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();

        int boundRadius = preferences.getInt(PREF_KEY_BOUND_RADIUS, 1000);
        // Create a RectangularBounds object.
        RectangularBounds bounds = RectangularBounds.newInstance(
                Go4LunchHelper.toBounds(latLng, boundRadius).southwest,
                Go4LunchHelper.toBounds(latLng, boundRadius).northeast);

        // Use the builder to create a FindAutocompletePredictionsRequest.
        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
                .setLocationRestriction(bounds)
                .setOrigin(new LatLng(lat, lng))
                .setCountries("FR")
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .setSessionToken(token)
                .setQuery(query)
                .build();

        List<Restaurant> restaurantList = new ArrayList<>();
        List<String> restaurantsId = new ArrayList<>();

        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {

            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
                Restaurant restaurant = new Restaurant(prediction.getPlaceId(), prediction.getPrimaryText(null).toString());
                if (prediction.getPlaceTypes().size() > 0) {
                    for (Place.Type mPlace : prediction.getPlaceTypes()) {
                        if (mPlace.toString().equals(RESTAURANT)) {
                            restaurantList.add(restaurant);
                            restaurantsId.add(prediction.getPlaceId());
                            break;
                        }
                    }
                }
            }

         //   getAutoCompleteRestaurantsFromFirestore(restaurantList, restaurantsId);

        }).addOnFailureListener((exception) -> {
            if (exception instanceof ApiException) {
                ApiException apiException = (ApiException) exception;
                Log.e(TAG, ERROR_ON_FAILURE_LISTENER + apiException.getStatusCode());
            }
        });
    }

    /**
     * Get from Firestore the restaurant list retrieved from the autocomplete prediction result
     * If all the restaurant are in Firestore, the list is prepared and send for the display
     * If there's some restaurant missing, the list is submitted to Google to have their details
     * @param restaurantList : list object : restaurant list from the autocomplete prediction
     * @param restaurantsId : list string : restaurant id from the autocomplete prediction
     */
  // private void getAutoCompleteRestaurantsFromFirestore(List<Restaurant> restaurantList, List<String> restaurantsId) {
  //     int maxRestaurantList = restaurantList.size();
  //     if (maxRestaurantList > 0) {
  //         restaurantRef.whereIn(String.valueOf(Restaurant.Fields.restaurantPlaceId), restaurantsId)
  //                 .get().addOnCompleteListener(task -> {
  //             if (task.isSuccessful()) {
  //                 List<Restaurant> restaurantListFirestore = task.getResult().toObjects(Restaurant.class);
  //                 if (maxRestaurantList != restaurantListFirestore.size()) {
  //                     getAutoCompleteMissingRestaurant(restaurantList);
  //                 } else {
  //                     prepareAndSendRestaurantListForDisplay(restaurantListFirestore);
  //                 }
  //             }
  //         }).addOnFailureListener((exception) -> {
  //             if (exception instanceof ApiException) {
  //                 ApiException apiException = (ApiException) exception;
  //                 Log.e(TAG, ERROR_ON_FAILURE_LISTENER + "Place not found in Firestore : " + apiException.getStatusCode());
  //             }
  //         });
  //     } else {
  //         Log.d(TAG, "getRestaurantsFromFirestore: no restaurant to search");
  //     }
  // }

    /**
     * Retrieve restaurant detail information from Google
     * @param autocompleteRestaurantList : list object : autocomplete prediction restaurant list
     */
    private void getAutoCompleteMissingRestaurant(List<Restaurant> autocompleteRestaurantList) {

        restaurantListDetail = new ArrayList<>();

        if (autocompleteRestaurantList.size() > 0) {
            for (Restaurant autoRestaurant : autocompleteRestaurantList) {
                getGoogleDetailRestaurant(autoRestaurant.getRestaurantPlaceId(),
                        autocompleteRestaurantList.size());
            }
        }
    }

    /**
     * Get the photo from Google
     * @param photoReference : string : photo reference of the restaurant
     * @param maxWidth : int : max width of the photo
     * @param key : string : google key
     * @return : string : the link to the photo
     */
    public static String getPhoto(String photoReference, int maxWidth, String key) {
        return BASE_URL_GOOGLE + PHOTO_REF_GOOGLE + photoReference
                + MAX_WIDTH_GOOGLE + maxWidth + KEY_GOOGLE + key;
    }
}