package com.example.go4lunch.goforlunch.ui.restaurant;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.multidex.BuildConfig;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.go4lunch.goforlunch.models.places.RestaurantResult;
import com.go4lunch.R;
import com.go4lunch.databinding.FragmentListLayoutBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.Task;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RestaurantsFragment extends Fragment {


    private List<String> chosenRestaurantsList;

    private FragmentListLayoutBinding binding;
    private RestaurantAdapter restaurantAdapter;
    private RestaurantsViewModel restaurantViewModel;
    private String radius;
    private double latitude;
    private double longitude;
    private String currentLocation;
    private List<RestaurantResult> restaurantResultList;
    private List<RestaurantResult> restaurantResultPredictionsList;
    private boolean stateName = true;
    private boolean stateDistance = true;
    private boolean stateRating = true;

    private List<String> predictionsPlaceIdList;

    @SuppressLint("LongLogTag")
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListLayoutBinding.inflate(inflater, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext());
        radius = sharedPreferences.getString("key_pref_radius_2", "500");

        restaurantViewModel = new ViewModelProvider(this).get(RestaurantsViewModel.class);


        binding.fragmentListLayout.setLayoutManager(new LinearLayoutManager(requireContext()));
        restaurantAdapter = new RestaurantAdapter();
        restaurantResultList = new ArrayList<>();

        restaurantResultPredictionsList = new ArrayList<>();
        Log.d("restaurantResultPredictionsList NEW : %s", restaurantResultPredictionsList.toString());

        getCurrentLocation();

        return binding.getRoot();
    }

    // Setup Toolbar
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_search_menu, menu);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    // Get details restaurant from api
    private void getRestaurantDetails(String placeId) {
        restaurantViewModel.getDetailsRestaurant(placeId, BuildConfig.BUILD_TYPE).observe(getViewLifecycleOwner(), detailsResult -> {

            Location currentLocation = new Location("");
            currentLocation.setLatitude(latitude);
            currentLocation.setLongitude(longitude);

            Location restaurantLocation = new Location("");
            restaurantLocation.setLatitude(detailsResult.getDetailsGeometry().getLocation().getLat());
            restaurantLocation.setLongitude(detailsResult.getDetailsGeometry().getLocation().getLng());

            int occurrences = Collections.frequency(chosenRestaurantsList, placeId);

            if (!restaurantResultPredictionsList.isEmpty()) {
                if (!placeId.equals(restaurantResultPredictionsList.get(0).getPlaceId())) {
                    restaurantResultPredictionsList.add(0, new RestaurantResult(detailsResult.getName(), detailsResult.getRestaurantOpeningHours(),
                            detailsResult.getAddress(), placeId, detailsResult.getDetailsPhotos(), detailsResult.getRating() * 3 / 5,
                            occurrences, (int) currentLocation.distanceTo(restaurantLocation)));
                }
            } else {
                restaurantResultPredictionsList.add(0, new RestaurantResult(detailsResult.getName(), detailsResult.getRestaurantOpeningHours(),
                        detailsResult.getAddress(), placeId, detailsResult.getDetailsPhotos(), detailsResult.getRating() * 3 / 5, occurrences,
                        (int) currentLocation.distanceTo(restaurantLocation)));
            }

            restaurantAdapter.setData(restaurantResultPredictionsList);
            binding.fragmentListLayout.setAdapter(restaurantAdapter);
        });
    }

    // Get nearby restaurants from api
    private void getAllRestaurants() {
        String type = "restaurant";
        restaurantViewModel.getRestaurants(currentLocation, Integer.parseInt(radius), type, BuildConfig.BUILD_TYPE).observe(getViewLifecycleOwner(),
                restaurants -> {

                    Location currentLocation = new Location("");
                    currentLocation.setLatitude(latitude);
                    currentLocation.setLongitude(longitude);

                    for (int i = 0; i < restaurants.size(); i++) {

                        int occurrences = Collections.frequency(chosenRestaurantsList, restaurants.get(i).getPlaceId());

                        Location restaurantLocation = new Location("");
                        restaurantLocation.setLatitude(restaurants.get(i).getGeometry().getLocation().getLat());
                        restaurantLocation.setLongitude(restaurants.get(i).getGeometry().getLocation().getLng());

                        restaurantResultList.add(new RestaurantResult(restaurants.get(i).getName(), restaurants.get(i).getRestaurantOpeningHours(),
                                restaurants.get(i).getAddress(), restaurants.get(i).getPlaceId(), restaurants.get(i).getRestaurantPhotos(),
                                restaurants.get(i).getRating() * 3 / 5, occurrences, (int) currentLocation.distanceTo(restaurantLocation)));
                    }

                    restaurantAdapter.setData(restaurantResultList);
                    binding.fragmentListLayout.setAdapter(restaurantAdapter);
                });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireContext());

        try {
            Task<Location> taskLocation = fusedLocationProviderClient.getLastLocation();
            taskLocation.addOnCompleteListener(task -> {
                if (task.isSuccessful() && task.getResult() != null) {
                    Location location = task.getResult();
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    currentLocation = latitude + ", " + longitude;
                    getAllRestaurants();
                }
            });
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
//        workmateListener.remove();
    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}