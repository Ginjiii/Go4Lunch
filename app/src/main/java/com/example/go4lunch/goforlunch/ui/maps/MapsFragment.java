package com.example.go4lunch.goforlunch.ui.maps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.go4lunch.goforlunch.base.BaseFragment;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity;
import com.go4lunch.R;
import com.go4lunch.databinding.FragmentMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class MapsFragment extends BaseFragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private FragmentMapsBinding binding;
    private MapsViewModel viewModel;
    private GoogleMap googleMap;
    private MapView mapView;
    private Location userLocation;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        configureFragmentOnCreateView(view);
        configureMapView(savedInstanceState);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        if (googleMap != null) {
            String STATE_KEY_MAP_CAMERA = "keymap";
            outState.putParcelable(STATE_KEY_MAP_CAMERA, googleMap.getCameraPosition());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setIndoorEnabled(false);
        updateLocationUI();
        googleMap.setOnCameraMoveListener(this::onMarkerClick);
    }

    @Override
    protected void configureFragmentOnCreateView(View view) {
        viewModel = obtainViewModel();
    }

    private MapsViewModel obtainViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return new ViewModelProvider(requireActivity(), viewModelFactory).get(MapsViewModel.class);
    }

    @Override
    public void getLocationUser(Location location) {
        userLocation = location;
        viewModel.fetchCoworkersGoing();
        viewModel.coworkersIdMutableLiveData
                .observe(getViewLifecycleOwner(), coworkerIds ->
                        viewModel.getRestaurantList(location.getLatitude(), location.getLongitude())
                                .observe(getViewLifecycleOwner(), restaurants ->
                                        setMapMarkers(restaurants, coworkerIds)));
        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
    }

    private void setMapMarkers(List<Restaurant> restaurants, List<String> coworkerIds) {
        if (googleMap != null) {
            googleMap.clear();

            for (Restaurant restaurant : restaurants) {
                int iconResource = (coworkerIds.contains(restaurant.getRestaurantID())) ?
                        R.drawable.icon_location_selected : R.drawable.icon_location_normal;
                LatLng positionRestaurant = new LatLng(restaurant.getLatitude(), restaurant.getLongitude());
                googleMap.addMarker(new MarkerOptions()
                        .position(positionRestaurant)
                        .icon(BitmapDescriptorFactory.fromResource(iconResource))
                        .title(restaurant.getName()))
                        .setTag(restaurant.getRestaurantID());
                onMarkerClick();
            }
        }
    }

    @SuppressLint("PotentialBehaviorOverride")
    private void onMarkerClick() {
        googleMap.setOnMarkerClickListener(marker -> {
            String placeId = (String) marker.getTag();
            Intent intent = new Intent(getActivity(), RestaurantDetailActivity.class);
            intent.putExtra(RESTAURANT_PLACE_ID, placeId);
            startActivity(intent);
            return false;
        });
    }

    private void updateLocationUI() {
        try {
            if (userLocation != null) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
                googleMap.animateCamera(CameraUpdateFactory
                        .newLatLngZoom(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()), 16));
            }
        } catch (SecurityException e) {
            Log.e("Exception:", e.getMessage());
        }
    }

    private void configureMapView(Bundle savedInstanceState) {
        mapView = binding.mapView;
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    public void displayRestaurant(LatLng latLng) {
        if (latLng != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        }
    }
}