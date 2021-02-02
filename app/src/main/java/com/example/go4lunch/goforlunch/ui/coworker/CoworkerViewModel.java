package com.example.go4lunch.goforlunch.ui.coworker;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;

import java.util.List;


public class CoworkerViewModel extends ViewModel {

    private final CoworkerRepository coworkerRepository;

    public CoworkerViewModel(CoworkerRepository mCoworkerRepository) {
        coworkerRepository = mCoworkerRepository;
    }

    /**
     * Get workmate list
     * @return : list object : Coworker list
     */
    public LiveData<List<Coworker>> getCoworkerList() {
        return coworkerRepository.getCoworkerListData();
    }
}