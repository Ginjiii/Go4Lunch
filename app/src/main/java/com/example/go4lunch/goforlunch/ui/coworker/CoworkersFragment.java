package com.example.go4lunch.goforlunch.ui.coworker;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.go4lunch.R;


public class CoworkersFragment extends Fragment {

    public static CoworkersFragment newInstance() {
        return new CoworkersFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coworkers_layout, container, false);
    }
}