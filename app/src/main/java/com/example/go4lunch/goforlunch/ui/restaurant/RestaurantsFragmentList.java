package com.example.go4lunch.goforlunch.ui.restaurant;

import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.go4lunch.goforlunch.base.BaseFragment;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.databinding.FragmentListLayoutBinding;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class RestaurantsFragmentList extends BaseFragment {

    public static final String TAG = RestaurantsFragmentList.class.getSimpleName();

    private RestaurantAdapter adapter;
    private RestaurantsViewModel viewModel;
    private FragmentListLayoutBinding binding;

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
            fetchRestaurantList(location);
        }
    }

    @Override
    protected void configureFragmentOnCreateView(View view) {
        viewModel = obtainViewModel();
        initRecyclerView();
    }

    private RestaurantsViewModel obtainViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return new ViewModelProvider(requireActivity(), viewModelFactory).get(RestaurantsViewModel.class);
    }

    private void initRecyclerView() {
        adapter = new RestaurantAdapter();
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.restaurantRecyclerView.setAdapter(adapter);
    }

    private void fetchRestaurantList(Location location) {
        viewModel.fetchCoworkersGoing();
        viewModel.coworkersIdMutableLiveData
                .observe(getViewLifecycleOwner(), coworkerIds ->
                        viewModel.getRestaurantList(location.getLatitude(), location.getLongitude())
                                .observe(getViewLifecycleOwner(), list ->
                                        changeAndNotifyAdapterChange(list, coworkerIds)));
    }

    private void changeAndNotifyAdapterChange(List<Restaurant> restaurants, List<String> coworkerIds) {
        adapter.setRestaurantList(restaurants, coworkerIds);
    }
}