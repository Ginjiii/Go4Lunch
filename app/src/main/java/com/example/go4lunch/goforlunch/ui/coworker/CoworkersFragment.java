package com.example.go4lunch.goforlunch.ui.coworker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.go4lunch.databinding.FragmentCoworkersLayoutBinding;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CoworkersFragment extends Fragment {

    private FragmentCoworkersLayoutBinding binding;
    private List<Coworker> coworker;
    private CoworkerAdapter coworkerAdapter;
    private CoworkerViewModel coworkerViewModel;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCoworkersLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        configureFragmentOnCreateView();
        return view;
    }

    protected void configureFragmentOnCreateView() {
        coworkerViewModel = obtainViewModel();
        initRecyclerView();
        setupCoworkersList();
    }

    private CoworkerViewModel obtainViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return new ViewModelProvider(requireActivity(), viewModelFactory).get(CoworkerViewModel.class);
    }

    private void initRecyclerView() {
        coworker = new ArrayList<>();
        coworkerAdapter = new CoworkerAdapter(coworker);
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        binding.restaurantRecyclerView.setAdapter(coworkerAdapter);
    }

    private void setupCoworkersList() {
        coworkerViewModel.fetchListUsersFromFirebase();
        coworkerViewModel.getCoworkers().observe(getViewLifecycleOwner(), this::showUsers);
    }

    private void showUsers(List<Coworker> coworkers) {
        this.coworker = coworkers;
        coworkerAdapter.updateCoworkers(this.coworker);
    }
}