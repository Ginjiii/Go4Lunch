package com.example.go4lunch.goforlunch.ui.restaurant;

import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.R;
import com.go4lunch.databinding.FragmentListLayoutBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static com.facebook.FacebookSdk.getApplicationContext;

public class RestaurantsFragment extends Fragment {

    FragmentListLayoutBinding binding;
    private List<Restaurant> restaurants;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0;
    private boolean mLocationPermissionGranted;
    private GoogleMap googleMap;
    private double latitude;
    private double longitude;
    private String position;

    public RestaurantsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        this.configureRecyclerView(view);
        return view;
    }

    private void configureRecyclerView(View view){
        restaurants = new ArrayList<>();
    }

    private void getLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(requireActivity(),
                    new String[]{ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    private void updateLocationUI() {
        try {
            googleMap.setMyLocationEnabled(mLocationPermissionGranted);
            googleMap.getUiSettings().setMyLocationButtonEnabled(mLocationPermissionGranted);
            getLocationPermission();
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                LocationRequest locationRequest = LocationRequest.create();
                locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

                mFusedLocationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        Location location = locationResult.getLastLocation();
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        position = latitude + "," + longitude;
                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(latitude,
                                        longitude), 16));
                        mFusedLocationProviderClient.removeLocationUpdates(this);
                    }
                }, Looper.getMainLooper());
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
