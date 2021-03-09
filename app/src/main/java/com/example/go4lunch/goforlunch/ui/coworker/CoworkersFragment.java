package com.example.go4lunch.goforlunch.ui.coworker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity;
import com.example.go4lunch.goforlunch.utils.ItemClickSupport;
import com.go4lunch.databinding.FragmentCoworkersLayoutBinding;

import java.util.ArrayList;
import java.util.List;

public class CoworkersFragment extends Fragment {

    private FragmentCoworkersLayoutBinding binding;
    private List<Coworker> coworker;
    private CoworkerAdapter coworkerAdapter;
    private CoworkerViewModel coworkerViewModel;
    private RecyclerView recyclerView;

    public CoworkersFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCoworkersLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        initRecyclerView();
        configureFragmentOnCreateView();
        //coworkerViewModel.createUser();
        return view;
    }

    private void configureViewModel() {
        Go4LunchFactory factory = Injection.go4LunchFactory();
        CoworkerViewModel coworkerViewModel = new ViewModelProvider(requireActivity(), factory).get(CoworkerViewModel.class);
        coworkerViewModel.fetchListUsersFromFirebase();

        coworkerViewModel.getCoworkers().observe(getViewLifecycleOwner(), coworkerList -> {

            coworkerAdapter.setCoworkerList(coworkerList);
            coworkerAdapter.notifyDataSetChanged();
            setupCoworkersList();
        });
    }

    protected void configureFragmentOnCreateView() {
        initRecyclerView();
        configureViewModel();
        coworkerViewModel = getViewModel();
    }

    private void setupCoworkersList(){
        coworkerViewModel.getCoworkers().observe(getViewLifecycleOwner(), this::showUsers);
    }

    private CoworkerViewModel getViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return ViewModelProviders.of(this, viewModelFactory)
                .get(CoworkerViewModel.class);
    }

    private void setupOpenDetailRestaurant(){
        coworkerViewModel.getOpenDetailRestaurant().observe(this, openEvent -> {
            if(openEvent.getContentIfNotHandle() != null) {
                displayRestaurantDetail();
            }
        });
    }

    private void initRecyclerView() {
        coworker = new ArrayList<>();
        coworkerAdapter = new CoworkerAdapter(coworker, Glide.with(this));
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.restaurantRecyclerView.setAdapter(coworkerAdapter);
    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recyclerView, binding.restaurantRecyclerView.getId())
                .setOnItemClickListener((recyclerView, position, v)
                        -> coworkerViewModel.updateRestaurantToDisplay(coworkerAdapter.getCoworker(position)));

    }

    private void showUsers(List<Coworker> coworkers) {
        this.coworker = coworkers;
        coworkerAdapter.update(this.coworker);
 //       this.configureOnClickRecyclerView();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void displayRestaurantDetail() {
        RestaurantDetailActivity restaurantDetailActivity = new RestaurantDetailActivity();
        restaurantDetailActivity.getSupportFragmentManager();
    }
}
