package com.example.go4lunch.goforlunch.ui.restaurant;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.goforlunch.base.BaseFragment;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.ui.signin.SignInViewModel;
import com.go4lunch.R;
import com.go4lunch.databinding.FragmentListLayoutBinding;
import com.go4lunch.databinding.FragmentMapsBinding;

import java.util.List;

public class RestaurantsFragmentList extends BaseFragment {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private RestaurantsViewModel viewModel;
    private FragmentListLayoutBinding binding;

    public RestaurantsFragmentList() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_list_layout; }

    @Override
    protected void configureFragmentOnCreateView(View view) {

        recyclerView = binding.fragmentListLayout;
        initRecyclerView();
    }

    private void initRecyclerView()  {
        adapter = new RestaurantAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = new ViewModelProvider(this).get(RestaurantsViewModel.class);

    }

    private void configureViewModel() {
        viewModel.getRestaurantList().observe(getViewLifecycleOwner(), this::changeAndNotifyAdapterChange);
    }

    /**
     * Inform the recycler view adapter when there is a new data
     * @param restaurantList : list object : restaurant list
     */
    public void changeAndNotifyAdapterChange(List<Restaurant> restaurantList) {
        adapter.setRestaurantList(restaurantList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        configureViewModel();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
