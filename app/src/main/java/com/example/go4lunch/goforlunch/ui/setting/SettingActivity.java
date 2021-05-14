package com.example.go4lunch.goforlunch.ui.setting;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.go4lunch.databinding.SettingLayoutBinding;


public class SettingActivity extends AppCompatActivity {

    private SettingLayoutBinding binding;
    private SettingViewModel settingViewModel;

    // --------------------
    // LIFE CYCLE STATE
    // --------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        binding = SettingLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        settingViewModel = getViewModel();
        settingViewModel.configureSaveDataRepo(getApplicationContext());

        boolean statusNotification = settingViewModel.getStatusNotification(getApplicationContext());
        binding.notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingViewModel.notificationStateChanged(isChecked, getApplicationContext());
        });

        binding.notificationSwitch.setChecked(statusNotification);
    }

    private SettingViewModel getViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return ViewModelProviders.of(this, viewModelFactory)
                .get(SettingViewModel.class);
    }
}
