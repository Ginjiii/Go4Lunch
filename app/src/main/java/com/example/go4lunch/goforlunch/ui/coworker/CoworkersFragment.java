package com.example.go4lunch.goforlunch.ui.coworker;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.go4lunch.databinding.FragmentCoworkersLayoutBinding;

public class CoworkersFragment extends Fragment {

    private FragmentCoworkersLayoutBinding binding;
    private CoworkerAdapter coworkerAdapter;


    public CoworkersFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCoworkersLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        configureFragmentOnCreateView(view);
        return view;
    }

    protected void configureFragmentOnCreateView(View view) {
        initRecyclerView();
        configureViewModel();
    }

    private void initRecyclerView() {
        coworkerAdapter = new CoworkerAdapter();
        binding.restaurantRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL, false));
        binding.restaurantRecyclerView.setAdapter(coworkerAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void configureViewModel() {
        Go4LunchFactory factory = Injection.go4LunchFactory();
        CoworkerViewModel coworkerViewModel = new ViewModelProvider(requireActivity(), factory).get(CoworkerViewModel.class);

        coworkerViewModel.getCoworkerList().observe(getViewLifecycleOwner(), coworkerList -> {
            coworkerAdapter.setCoworkerList(coworkerList);
            coworkerAdapter.notifyDataSetChanged();
        });
    }
}
