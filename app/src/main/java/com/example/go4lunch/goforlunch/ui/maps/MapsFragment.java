package com.example.go4lunch.goforlunch.ui.maps;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.go4lunch.goforlunch.base.BaseFragment;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;

import com.go4lunch.databinding.FragmentMapsBinding;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

public class MapsFragment extends BaseFragment implements OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private final String TAG = MapsFragment.class.getSimpleName();

    private String STATE_KEY_MAP_CAMERA = "keymap";

    private FragmentMapsBinding binding;
    private MapsViewModel viewModel;
    private GoogleMap googleMap;
    private MapView mapView;
    private Location userLocation;

    public MapsFragment() {
    }

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
            outState.putParcelable(STATE_KEY_MAP_CAMERA, googleMap.getCameraPosition());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.setIndoorEnabled(false);
        updateLocationUI();
    }

    @Override
    protected void configureFragmentOnCreateView(View view) {
        configureViewModel();
    }

    @Override
    protected void configureViewModel() {
        Go4LunchFactory factory = Injection.go4LunchFactory();
        viewModel = new ViewModelProvider(requireActivity(), factory).get(MapsViewModel.class);
    }

    @Override
    public void getLocationUser(Location location) {
        userLocation = location;
        viewModel.getRestaurantList(location.getLatitude(), location.getLongitude()).observe(this, this::setMapMarkers);
        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory
                    .newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 16));
        }
    }

    private void setMapMarkers(List<Restaurant> restaurants) {
        BitmapDescriptor bitmapDescriptor;
        if (googleMap != null) {
            googleMap.clear();
            for (Restaurant restaurant : restaurants) {
                if ((restaurant.getRestaurantCoworkerList() != null) && (restaurant.getRestaurantCoworkerList().size() > 0)) {
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE);
                } else {
                    bitmapDescriptor = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
                }
                if (restaurant.getRestaurantLocation() != null) {
                    LatLng latLng = new LatLng(restaurant.getRestaurantLocation().getLat(),
                            restaurant.getRestaurantLocation().getLng());
                    Marker marker = googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .icon(bitmapDescriptor));
                    marker.setTag(restaurant);
                }
            }
        }
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
}