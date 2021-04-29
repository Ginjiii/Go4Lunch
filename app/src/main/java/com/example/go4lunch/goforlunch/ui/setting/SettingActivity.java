package com.example.go4lunch.goforlunch.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.go4lunch.databinding.SettingLayoutBinding;

import pub.devrel.easypermissions.EasyPermissions;


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
        settingViewModel = getViewModel();
        settingViewModel.configureSaveDataRepo(getApplicationContext());
    }

    private void initView() {
        binding = SettingLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.notificationSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            settingViewModel.notificationStateChanged(isChecked);
        });
    }

    private SettingViewModel getViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return ViewModelProviders.of(this, viewModelFactory)
                .get(SettingViewModel.class);
    }
}
