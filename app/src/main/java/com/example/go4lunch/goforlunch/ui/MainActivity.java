package com.example.go4lunch.goforlunch.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.go4lunch.goforlunch.ui.maps.MapsFragment;
import com.go4lunch.R;
import com.go4lunch.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolbar;
    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ActivityMainBinding binding;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        toolbar = findViewById(R.id.main_toolbar);
        bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        navigationView = findViewById(R.id.main_navigation_view);

        this.configureToolbar();
        this.configureDrawerLayout();
        this.configureNavigationView();

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

    // --------------------
    // UI
    // --------------------

    protected void configureToolbar() {
        setSupportActionBar(toolbar);
    }

     //Bottom Nav
     private BottomNavigationView.OnNavigationItemSelectedListener navigationlistener =
             new BottomNavigationView.OnNavigationItemSelectedListener() {
                 @SuppressLint("WrongConstant")
                 @Override
                 public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                     Fragment selectedFragment = null;

                     switch (menuItem.getItemId()) {
                         case R.id.bottom_navigation_menu_map_button:
                              selectedFragment = new MapsFragment();

                             break;
                         case R.id.bottom_navigation_menu_list_button:
                             // selectedFragment = new ListFragment();
                             break;
                         case R.id.bottom_navigation_menu_coworkers_button:
                            // selectedFragment = new CoworkersFragment();

                             break;
                    }

                    if (selectedFragment != null) {
                       MainActivity.this.getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,
                               selectedFragment).commit();
                  }
                   return true;
             }
           };

    // Navigation Drawer

    private void configureDrawerLayout() {
        this.drawerLayout = findViewById(R.id.main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_menu_lunch_button:
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    //NavigationView
    private void configureNavigationView() {
        this.navigationView = findViewById(R.id.main_navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
}