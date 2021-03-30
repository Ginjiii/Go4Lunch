package com.example.go4lunch.goforlunch.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.repositories.CoworkerRepository;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;

public abstract class BaseViewModel extends ViewModel {

    public final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    protected Coworker coworker;
    protected CoworkerRepository coworkerRepository;
    public abstract void action(Actions actions);

    protected String getCurrentUserUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    public OnFailureListener onFailureListener(Actions actions){
        return e -> {
            isLoading.setValue(false);
        };
    }

}
