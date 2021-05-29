package com.example.go4lunch.goforlunch.ui.restaurantDetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.ui.coworker.CoworkerDetailAdapter;
import com.go4lunch.R;
import com.go4lunch.databinding.ActivityRestaurantDetailBinding;

public class RestaurantDetailActivity extends AppCompatActivity {

    private final String TAG = RestaurantDetailActivity.class.getSimpleName();

    public static final String RESTAURANT_PLACE_ID = "placeId";
    private Restaurant restaurant;
    private ActivityRestaurantDetailBinding binding;
    private RestaurantDetailViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initListener();
        initViewModel();
        getRestaurantPlaceId();
    }

    private void initView() {
        binding = ActivityRestaurantDetailBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
    }

    private void initListener() {
        binding.restaurantDetailCallButton.setOnClickListener(v -> openDialer(restaurant.getPhoneNumber()));
        binding.restaurantDetailLikeButton.setOnClickListener(v -> viewModel.updateRestaurantLiked(restaurant));
        binding.restaurantDetailWebsiteButton.setOnClickListener(v -> openWebSite(restaurant.getWebSite()));
        binding.restaurantDetailFab.setOnClickListener(v -> viewModel.updatePickedRestaurant(restaurant));
    }

    private void initViewModel() {
        Go4LunchFactory viewModelFactory = Injection.provideViewModelFactory();
        viewModel = new ViewModelProvider(this, viewModelFactory).get(RestaurantDetailViewModel.class);
    }

    private void getRestaurantPlaceId() {
        if (getIntent().hasExtra(RESTAURANT_PLACE_ID)) {
            String placeId = getIntent().getStringExtra(RESTAURANT_PLACE_ID);
            getRestaurantDetail(placeId);
            initCoworkersList();
        }
    }

    private void getRestaurantDetail(String placeId) {
        viewModel.getRestaurantDetail(placeId).observe(this, this::displayInfoRestaurant);
    }

    private void displayInfoRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
        binding.restaurantDetailName.setText(restaurant.getName());
        binding.restaurantDetailAddress.setText(restaurant.getAddress());

        if (restaurant.getPhotoReference() != null) {
            Glide.with(RestaurantDetailActivity.this)
                    .load(restaurant.getPhotoReference())
                    .apply(RequestOptions.centerCropTransform())
                    .into(this.binding.restaurantDetailPicture);
        }

        viewModel.fetchInfoRestaurant(restaurant);
        viewModel.fetchCoworkerLike(restaurant);
    }

    private void initCoworkersList() {
        CoworkerDetailAdapter coworkerDetailAdapter = new CoworkerDetailAdapter();
        binding.coworkersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.coworkersRecyclerView.setAdapter(coworkerDetailAdapter);

        viewModel.mCoworkerList.observe(this, coworkerDetailAdapter::setCoworkerLists);
        viewModel.isRestaurantLiked.observe(this, this::changeLikeStatus);
        viewModel.isRestaurantPicked.observe(this, this::changeChoiceStatus);
    }

    /**
     * Display if the restaurant is liked by the workmate
     */
    private void changeLikeStatus(Boolean isLiked) {
        int resourceId = (isLiked) ? R.drawable.ic_baseline_star_rate_24 : R.drawable.ic_baseline_menu_star_border_24;
        Drawable drawable = ContextCompat.getDrawable(this, resourceId);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), 60);
        binding.restaurantDetailLikeButton.setCompoundDrawables(null, drawable, null, null);
        binding.restaurantDetailLikeButton.setBackgroundResource(resourceId);
    }

    private void changeChoiceStatus(Boolean isSelected) {
        int resourceId = (isSelected) ? R.drawable.ic_baseline_check_circle_24_ok : R.drawable.ic_baseline_uncheck_circle_24;
        int color = (isSelected) ? Color.GREEN : Color.GRAY;
        Drawable drawable = ContextCompat.getDrawable(this, resourceId);
        drawable.mutate().setColorFilter(color, PorterDuff.Mode.MULTIPLY);
        binding.restaurantDetailFab.setImageDrawable(drawable);
    }

    /**
     * Open the website
     *
     * @param webSite : string : url to open
     */
    private void openWebSite(String webSite) {
        if (webSite != null) {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(webSite));
            startActivity(intent);
        } else {
            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.text_no_web_site), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Open the dialer
     *
     * @param phone : string : phone number to display
     */
    private void openDialer(String phone) {
        if ((phone != null) && (phone.trim().length() > 0)) {
            Intent lIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)));
            startActivity(lIntent);
        } else {
            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.text_no_phone_number), Toast.LENGTH_SHORT).show();
        }
    }
}

