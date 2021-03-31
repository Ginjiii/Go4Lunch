package com.example.go4lunch.goforlunch.ui.restaurant;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.example.go4lunch.goforlunch.base.BaseFragment;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.databinding.FragmentListLayoutBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


public class RestaurantsFragmentList extends BaseFragment {

    public static final String TAG = RestaurantsFragmentList.class.getSimpleName();

    private List<Restaurant> restaurants;
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
        this.configureFragmentOnCreateView(view);
        return view;
    }

    @Override
    public void getLocationUser(Location location) {
        if (isAdded()) {
            viewModel.getRestaurantList(location.getLatitude(), location.getLongitude()).observe(getViewLifecycleOwner(), this::changeAndNotifyAdapterChange);
        }
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
        restaurants = new ArrayList<>();
        adapter = new RestaurantAdapter(restaurants, Glide.with(this));
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.restaurantRecyclerView.setAdapter(adapter);
    }

    private void changeAndNotifyAdapterChange(List<Restaurant> restaurants) {
        this.restaurants = restaurants;
        adapter.update(this.restaurants);
        adapter.setRestaurantList(restaurants);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}