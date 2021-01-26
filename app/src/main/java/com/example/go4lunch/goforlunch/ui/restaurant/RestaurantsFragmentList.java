package com.example.go4lunch.goforlunch.ui.restaurant;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.goforlunch.base.BaseFragment;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.RestaurantRepository;
import com.example.go4lunch.goforlunch.ui.signin.SignInViewModel;
import com.go4lunch.R;
import com.go4lunch.databinding.FragmentListLayoutBinding;
import com.go4lunch.databinding.FragmentMapsBinding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class RestaurantsFragmentList extends BaseFragment {

    private RecyclerView recyclerView;
    private RestaurantAdapter adapter;
    private RestaurantsViewModel viewModel;
    private FragmentListLayoutBinding binding;
    public static final String TAG = "TAG_REPO_RESTAURANT";

    public RestaurantsFragmentList() {}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "Create View");

        binding = FragmentListLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        configureFragmentOnCreateView(view);
        return view;
    }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_list_layout; }

    @Override
    protected void configureFragmentOnCreateView(View view) {
        Log.d(TAG, "configureFragmentOnCreateView");

        recyclerView = binding.fragmentListLayout;


        configureViewModel();
    }

    private void initRecyclerView()  {


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Go4LunchFactory mFactory = Injection.go4LunchFactory();

        viewModel = new ViewModelProvider(requireActivity(), mFactory).get(RestaurantsViewModel.class);
        adapter = new RestaurantAdapter();
        List<Restaurant> rest = new ArrayList<Restaurant>();
        rest = viewModel.getRestaurantList().getValue();
        Log.d(TAG, "j'ai fais l'appel");
        adapter.setRestaurantList(rest);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        recyclerView.setAdapter(adapter);
    }

    private void configureViewModel() {
        Log.d(TAG, "TAG_REPO_RESTAURANT: ");
//        viewModel.getRestaurantList().observe(getViewLifecycleOwner(), this::changeAndNotifyAdapterChange);
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
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}
