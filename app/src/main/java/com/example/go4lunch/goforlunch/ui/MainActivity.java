package com.example.go4lunch.goforlunch.ui;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.ui.coworker.CoworkersFragment;
import com.example.go4lunch.goforlunch.ui.maps.MapsFragment;
import com.example.go4lunch.goforlunch.ui.notification.NotificationLunchService;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantsFragmentList;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity;
import com.example.go4lunch.goforlunch.ui.setting.SettingActivity;
import com.example.go4lunch.goforlunch.ui.signin.SignInActivity;
import com.go4lunch.BuildConfig;
import com.go4lunch.R;
import com.go4lunch.databinding.ActivityMainBinding;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private ActivityMainBinding binding;
    private MainViewModel viewModel;

    private int AUTOCOMPLETE_REQUEST_CODE = 1;

    private PendingIntent pendingIntentAlarm;
    private static int[] TIME_NOTIFICATION = {12, 00};

    private String selectedRestaurantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        viewModel = obtainViewModel();
        configureUI();
        configureNotification();
        getRestaurantUser();

        // for change title Action Bar
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.hungry);
        }

        Places.initialize(getApplicationContext(), BuildConfig.MAPS_API_KEY);

        // for connect MapFragment with activity
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_frame_layout, new MapsFragment())
                .commit();
    }

    private void initView() {
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private MainViewModel obtainViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        return new ViewModelProvider(this, viewModelFactory).get(MainViewModel.class);
    }

    private void configureNotification() {
        this.createNotificationChannel();
        this.configureNotificationIntent();
        this.enableNotifications();
    }

    // --------------------
    // CONFIGURE UI NAVIGATION
    // --------------------

    private void configureUI() {
        this.configureToolbar();
        this.configureBottomView();
        this.configureDrawerLayout();
        this.configureNavigationView();
    }

    //----- TOOLBAR -----

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.search_menu) {
            startAutocompleteActivity();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //----- DRAWER -----

    private void configureDrawerLayout() {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, binding.mainDrawerLayout, binding.mainToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorWhite));
        binding.mainDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
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

    @Override
    public void onBackPressed() {
        if (this.binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void getRestaurantUser() {
        viewModel.getSelectedRestaurant();
        viewModel.selectedRestaurantId.observe(this, id -> selectedRestaurantId = id);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_menu_lunch_button:
                if (selectedRestaurantId != null) {
                    Intent intent = new Intent(MainActivity.this, RestaurantDetailActivity.class);
                    intent.putExtra(RESTAURANT_PLACE_ID, selectedRestaurantId);
                    startActivity(intent);
                }
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
        this.binding.mainDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    // -----------------
// AUTOCOMPLETE CLICK
// -----------------

    public void startAutocompleteActivity() {
        List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.TYPES, Place.Field.LAT_LNG);
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("FR")
                .setTypeFilter(TypeFilter.ESTABLISHMENT)
                .build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.main_frame_layout);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place requestPlace = Autocomplete.getPlaceFromIntent(data);
                if (currentFragment instanceof MapsFragment) {
                    moveToRestaurantLocation((MapsFragment) currentFragment, requestPlace);
                } else {
                    displayDetailRestaurant(requestPlace);
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void moveToRestaurantLocation(MapsFragment mapsFragment, Place requestPlace) {
        if (requestPlace.getTypes() != null) {
            for (Place.Type type : requestPlace.getTypes()) {
                if (type == Place.Type.RESTAURANT) {
                    mapsFragment.displayRestaurant(requestPlace.getLatLng());
                }
            }
        }
    }

    private void displayDetailRestaurant(Place requestPlace) {
        if (requestPlace.getTypes() != null) {
            for (Place.Type type : requestPlace.getTypes()) {
                if (type == Place.Type.RESTAURANT) {
                    Intent detailIntent = new Intent(this, RestaurantDetailActivity.class);
                    detailIntent.putExtra(RESTAURANT_PLACE_ID, requestPlace.getId());
                    startActivity(detailIntent);
                }
            }
        }
    }

    // -----------------
    // NOTIFICATION ALARM MANAGER
    // -----------------

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getString(R.string.notificationChannel);
            CharSequence name = getString(R.string.name_channel);
            String description = getString(R.string.description_channel);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(channelId, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    private void configureNotificationIntent() {
        Intent notificationIntent = new Intent(this, NotificationLunchService.class);
        pendingIntentAlarm = PendingIntent.getBroadcast(this, 0,
                notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private void enableNotifications() {
        Calendar notificationTime = Calendar.getInstance();
        notificationTime.set(Calendar.HOUR_OF_DAY, TIME_NOTIFICATION[0]);
        notificationTime.set(Calendar.MINUTE, TIME_NOTIFICATION[1]);
        notificationTime.set(Calendar.MILLISECOND, 0);

        Calendar calendar = Calendar.getInstance();
        if (notificationTime.before(calendar)) {
            notificationTime.add(Calendar.DATE, 1);
        }

        ComponentName receiver = new ComponentName(getApplicationContext(), NotificationLunchService.class);

        PackageManager pm = getApplicationContext().getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);

        AlarmManager manager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, notificationTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntentAlarm);
    }
}