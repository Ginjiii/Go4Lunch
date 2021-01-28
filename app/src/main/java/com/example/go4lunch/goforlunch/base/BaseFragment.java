package com.example.go4lunch.goforlunch.base;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.models.common.Location;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseFragment extends Fragment {

    protected RestaurantsViewModel viewModel;
    private boolean mLocationPermissionGranted = true;
    protected FusedLocationProviderClient mFusedLocationProviderClient ;
    private double latitude;
    private double longitude;
    private String position;
    private GoogleMap googleMap;
    private String  TAG = "BaseFragment";
    // FOR LOCATION
    protected FusedLocationProviderClient fusedLocationClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;

    protected abstract int getFragmentLayout();
    protected abstract void configureFragmentOnCreateView(View view);

    public abstract void configureLocation(LatLng location);

    protected abstract void configureLocation();


    // @Nullable
  // @Override
  // public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  //     View view = LayoutInflater.from(container.getContext()).inflate(getFragmentLayout(), container, false);
  //     this.configureFragmentOnCreateView(view);
  //     return(view);


//    public Location getUserLocation() {
//     Location loc = null;
//        try {
//            if (mLocationPermissionGranted) {
//                Log.d(TAG, "Get getUserLocation");
//
//                LocationRequest locationRequest = LocationRequest.create();
//                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {
//
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        android.location.Location location = locationResult.getLastLocation();
//                        Log.d(TAG, "Get lastlocation");
//                        loc.setLat(location.getLatitude());
//                        loc.setLng(location.getLongitude());
//                        latitude = location.getLatitude();
//                        longitude = location.getLongitude();
//                        position = latitude + "," + longitude;
//                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
//                                new LatLng(latitude,
//                                        longitude), 16));
//                        mFusedLocationProviderClient.removeLocationUpdates(this);
//                    }
//                }, Looper.getMainLooper());
//            }
//        } catch (SecurityException e) {
//            Log.e("Exception: %s", e.getMessage());
//        }
//        return loc;
//    }
}


