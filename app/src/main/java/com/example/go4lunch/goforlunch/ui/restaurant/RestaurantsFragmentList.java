package com.example.go4lunch.goforlunch.ui.restaurant;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.goforlunch.base.BaseFragment;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;

import com.go4lunch.R;
import com.go4lunch.databinding.FragmentListLayoutBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantsFragmentList extends BaseFragment {

    public static final String TAG = RestaurantsFragmentList.class.getSimpleName();

    private RestaurantAdapter adapter;
    private RestaurantsViewModel viewModel;
    private FragmentListLayoutBinding binding;

    public RestaurantsFragmentList() {
    }

    @Nullable
    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        configureFragmentOnCreateView(view);
        return view;
    }

    @Override
    public void getLocationUser(Location location) {
        viewModel.getRestaurantList(location.getLatitude(), location.getLongitude()).observe(getViewLifecycleOwner(), this::changeAndNotifyAdapterChange);
    }

    @Override
    protected void configureFragmentOnCreateView(View view) {
        initRecyclerView();
        configureViewModel();
    }

    @Override
    protected void configureViewModel() {
        Go4LunchFactory factory = Injection.go4LunchFactory();
        viewModel = new ViewModelProvider(requireActivity(), factory).get(RestaurantsViewModel.class);
    }

    private void initRecyclerView() {
        adapter = new RestaurantAdapter();
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.restaurantRecyclerView.setAdapter(adapter);
    }

    private void changeAndNotifyAdapterChange(List<Restaurant> restaurants) {
        adapter.setRestaurantList(restaurants);
        adapter.notifyDataSetChanged();
    }
}