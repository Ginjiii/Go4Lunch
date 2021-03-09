package com.example.go4lunch.goforlunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.go4lunch.goforlunch.ui.coworker.CoworkersFragment;
import com.example.go4lunch.goforlunch.ui.maps.MapsFragment;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsFragmentList;
import com.example.go4lunch.goforlunch.ui.setting.SettingActivity;
import com.go4lunch.R;
import com.go4lunch.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.go4lunch.goforlunch.ui.signin.SignInActivity;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();
        this.configureBottomView();



        //For change title Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.hungry);
        }

        //For connect MapFragment with activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new MapsFragment())
                .commit();
    }
    @Override
    protected void onResume() {
        super.onResume();

    }
    // --------------------
    // UI
    // --------------------

    protected void configureToolbar() {
        setSupportActionBar(binding.mainToolbar);
    }

    //Bottom Nav
    private void configureBottomView() {
        binding.mainBottomNavigationView.setOnNavigationItemSelectedListener(item -> onBottomNavigation(item.getItemId()));
    }

    @SuppressLint("WrongConstant")
    public boolean onBottomNavigation(int itemId) {
        Fragment selectedFragment = null;

        switch (itemId) {
            case R.id.bottom_navigation_menu_map_button:
                selectedFragment = new MapsFragment();
                break;
            case R.id.bottom_navigation_menu_list_button:
                selectedFragment = new RestaurantsFragmentList();
                break;
            case R.id.bottom_navigation_menu_coworkers_button:
                selectedFragment = new CoworkersFragment();
                break;
        }

        if (selectedFragment != null) {
            MainActivity.this
                    .getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_frame_layout, selectedFragment)
                    .commit();
        }
        return true;
    }

    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.mainDrawerLayout, binding.mainToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_menu_lunch_button:
                break;
            case R.id.drawer_menu_settings_button:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.drawer_menu_logout_button:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(this, SignInActivity.class));
                finish();
                break;
        }
        binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configureNavigationView() {
        binding.mainNavigationView.setNavigationItemSelectedListener(this);
        ImageView imageUser = binding.mainNavigationView.getHeaderView(0).findViewById(R.id.user_navigation_header_image_view_picture);
        TextView userNameTextView = binding.mainNavigationView.getHeaderView(0).findViewById(R.id.user_navigation_header_name_text);
        TextView emailTextView = binding.mainNavigationView.getHeaderView(0).findViewById(R.id.user_navigation_header_email_text);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            userNameTextView.setText(user.getDisplayName());
            emailTextView.setText(user.getEmail());
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .transform(new CircleCrop())
                    .into(imageUser);
        }
    }
}