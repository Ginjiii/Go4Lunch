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
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;

import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.go4lunch.R;
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
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
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
        if (googleMap != null) {
            googleMap.clear();

            CoworkerRepository.getAllCoworker().addOnSuccessListener(queryDocumentSnapshots -> {
                List<Coworker> coworkerList = new ArrayList<>();

                for (DocumentSnapshot documentSnapshot : queryDocumentSnapshots.getDocuments()){
                    Coworker userFetched = documentSnapshot.toObject(Coworker.class);
                    coworkerList.add(userFetched);
                }
                Log.d("tag", " get size?" + coworkerList.size());
                for (Restaurant restaurant : restaurants) {
                    Double latitude = restaurant.getRestaurantLocation().getLat();
                    Double longitude = restaurant.getRestaurantLocation().getLng();
                    LatLng restaurantPosition = new LatLng(latitude, longitude);
                    Log.d("tag", "restaurant"+ restaurant.getRestaurantPlaceId());
                    for (Coworker coworker : coworkerList) {

                        Log.d("tag", "place id" + coworker.getRestaurantUid());
                        Log.d("tag", "name" + coworker.getCoworkerName());

                        if(restaurant.getRestaurantPlaceId().equals(coworker.getRestaurantUid())){

                            Marker marker = googleMap.addMarker(new MarkerOptions()
                                    .position(restaurantPosition)
                                    .title(restaurant.getRestaurantName())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_selected)));
                            marker.setTag(restaurant.getRestaurantCoworkerList());
                        } else {
                            Marker marker = googleMap.addMarker(new MarkerOptions()
                                    .position(restaurantPosition)
                                    .title(restaurant.getRestaurantName())
                                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location_normal)));
                            marker.setTag(restaurant.getRestaurantPlaceId());
                        }
                    }
                    Log.d("tag", "getCoworkerChoice: get it?" + restaurant.getCoworkerChoice());

                }
            });
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