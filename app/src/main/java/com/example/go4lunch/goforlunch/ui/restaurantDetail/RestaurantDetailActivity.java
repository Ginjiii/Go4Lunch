package com.example.go4lunch.goforlunch.ui.restaurantDetail;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.go4lunch.R;
import com.go4lunch.databinding.RestaurantDetailLayoutBinding;

import static com.example.go4lunch.goforlunch.service.Go4Lunch.api;

public class RestaurantDetailActivity extends AppCompatActivity {

    public static final String RESTAURANT_PLACE_ID = "placeId";
    public static final String RESTAURANT_DETAIL = "restaurantDetail";
    private LifecycleRegistry mLifecycleRegistry;
    private Restaurant restaurant;
    private String restaurantId;
    RestaurantDetailLayoutBinding binding;
    RestaurantDetailViewModel restaurantDetailViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RestaurantDetailLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mLifecycleRegistry = new LifecycleRegistry(this);
        mLifecycleRegistry.markState(Lifecycle.State.CREATED);
        String placeId = getRestaurantPlaceId();

        if (placeId != null) {
            getRestaurantDetail(placeId);
        }
    }

    private String getRestaurantPlaceId() {
        if (getIntent().hasExtra(RESTAURANT_PLACE_ID)) {
            restaurantId = getIntent().getStringExtra(RESTAURANT_PLACE_ID);
            api.setRestaurantId(restaurantId);

            return restaurantId;
        }
        return null;
    }

    private void initViewModel() {
        Go4LunchFactory factory = Injection.go4LunchFactory();
        restaurantDetailViewModel = new ViewModelProvider(this, factory).get(RestaurantDetailViewModel.class);
    }

    private void getRestaurantDetail(String placeId) {
        Go4LunchFactory factory = Injection.go4LunchFactory();
        restaurantDetailViewModel = new ViewModelProvider(this, factory).get(RestaurantDetailViewModel.class);
        restaurantDetailViewModel.getRestaurantDetail(placeId).observe(this, this::displayInfoRestaurant);
        displayChoiceStatus();

    }

    private void displayInfoRestaurant(Restaurant restaurant) {
        binding.restaurantDetailName.setText(restaurant.getRestaurantName());
        binding.restaurantDetailAddress.setText(restaurant.getRestaurantAddress());

        if (restaurant.getRestaurantPhotoUrl() != null) {
            Glide.with(RestaurantDetailActivity.this)
                    .load(restaurant.getRestaurantPhotoUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(this.binding.restaurantDetailPicture);
        }
        //displayChoiceStatus();



        binding.restaurantDetailCallButton.setOnClickListener(v -> openDialer(restaurant.getRestaurantPhone()));
        binding.restaurantDetailWebsiteButton.setOnClickListener(v -> openWebSite(restaurant.getRestaurantWebSite()));
        binding.restaurantDetailLikeButton.setOnClickListener(v -> restaurantDetailViewModel.updateRestaurantLiked(restaurant));
        //binding.restaurantDetailFab.setOnClickListener(v -> {
         //   Log.d("isRestaurantPicked", "displayInfoRestaurant: LetsGo ");
          //  restaurantDetailViewModel.updatePickedRestaurant(restaurant);

        //});
        //Initiation à la vue de l'affichage


        //binding.restaurantDetailFab.setOnClickListener(v1 -> changeChoiceStatus());
        restaurantDetailViewModel.isRestaurantPicked.observe(this, this::displayChoiceStatus);
        //restaurantDetailViewModel.checkIfRestaurantIsChosen(restaurant).observe(this, this::changeChoiceStatus);
        binding.restaurantDetailFab.setOnClickListener(v1 -> {
            restaurantDetailViewModel.updatePickedRestaurant(restaurant);
        });

    }


    private void displayChoiceStatus(Boolean aBoolean) {
        if(aBoolean)
        {
            Drawable checkButton = getResources().getDrawable(R.drawable.ic_baseline_check_circle_24_ok);
            //  //set the color filter, you can use also Mode.SRC_ATOP
            checkButton.mutate().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            //set it to your fab button initialized before
            binding.restaurantDetailFab.setImageDrawable(checkButton);

        }else{
            Drawable checkButton = getResources().getDrawable(R.drawable.ic_baseline_uncheck_circle_24);
            //  //set the color filter, you can use also Mode.SRC_ATOP
            checkButton.mutate().setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
            //set it to your fab button initialized before
            binding.restaurantDetailFab.setImageDrawable(checkButton);
        }
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

    private void displayChoiceStatus() {
        if (restaurantDetailViewModel == null) {
            initViewModel();
        }
    }

    /**
     * Change the coworker choice status
     *
     */
    private void changeChoiceStatus() {
        Log.d("change choice", "changeChoiceStatus: test");
        if (true) {

            //get the drawable
            Drawable checkButton = getResources().getDrawable(R.drawable.ic_baseline_check_circle_24_ok);
            //  //set the color filter, you can use also Mode.SRC_ATOP
            checkButton.mutate().setColorFilter(Color.GREEN, PorterDuff.Mode.MULTIPLY);
            //set it to your fab button initialized before
            binding.restaurantDetailFab.setImageDrawable(checkButton);
            binding.restaurantDetailFab.setTag(Actions.IS_CHOSEN);
        } else {
            binding.restaurantDetailFab.setImageResource(R.drawable.ic_baseline_uncheck_circle_24);
            binding.restaurantDetailFab.setTag(Actions.NOT_CHOSEN);
        }
    }
}

