package com.example.go4lunch.goforlunch.ui.restaurant;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.DI.DI;
import com.example.go4lunch.goforlunch.factory.Go4LunchFactory;
import com.example.go4lunch.goforlunch.injections.Injection;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.service.Api;
import com.example.go4lunch.goforlunch.utils.Actions;
import com.example.go4lunch.goforlunch.utils.Go4LunchHelper;
import com.go4lunch.R;
import com.go4lunch.databinding.RestaurantDetailLayoutBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class RestaurantDetailActivity extends AppCompatActivity {

    public static final String RESTAURANT_PLACE_ID = "placeId";

    private Restaurant restaurant;
    private String restaurantId;
    private TextView restaurantNameTextView;
    private TextView restaurantInfoTextView;
    private ImageView restaurantPictureImageView;
    private FloatingActionButton restaurantFab;
    private ImageView restaurantLike;
    private ImageView restaurantStar1;
    private ImageView restaurantStar2;
    private ImageView restaurantStar3;

    private RestaurantDetailViewModel restaurantDetailViewModel;
    private RecyclerView recyclerView;
    private RestaurantDetailCoworkerAdapter coworkerAdapter;
    public static final Api api = DI.getGo4LunchApiService();

    private RestaurantDetailLayoutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RestaurantDetailLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.restaurantDetailName.setText(restaurant.getRestaurantName());
        binding.restaurantDetailInfo.setText(restaurant.getRestaurantDistanceText());
        binding.restaurantDetailsRate.setNumStars((int) restaurant.getRestaurantRating());

        //TextView restaurantNameTextView = binding.restaurantDetailName;
        //TextView restaurantInfoTextView = binding.restaurantDetailInfo;
        //ImageView restaurantPictureImageView = binding.restaurantDetailPicture;

        getIncomingIntent();
        configureViewModel();
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra(RESTAURANT_PLACE_ID)) {
            restaurantId = getIntent().getStringExtra(RESTAURANT_PLACE_ID);
            api.setRestaurantId(restaurantId);
        }
    }

    private void configureViewModel() {
        initializeViewModel();
        initRecyclerView();
    }

    private void initRecyclerView() {
        coworkerAdapter = new RestaurantDetailCoworkerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(coworkerAdapter);
    }

     private void initializeViewModel() {

        Go4LunchFactory factory = Injection.go4LunchFactory();
        restaurantDetailViewModel = new ViewModelProvider(this, factory).get(RestaurantDetailViewModel.class);

        if (restaurantId == null) {
            restaurantDetailViewModel.getCoworkerData().observe(this, mCoworker -> {
                restaurantId = mCoworker.getCoworkerRestaurantChosen().getRestaurantId();
                getRestaurantDetail();
            });
        } else {
            getRestaurantDetail();
        }
    }

    /**
     * Ask the restaurant detail to Firestore
     */
    private void getRestaurantDetail() {

        restaurantDetailViewModel.getRestaurantDetail().observe(this, mRestaurant -> {
            coworkerAdapter.setCoworkerList(mRestaurant.getRestaurantCoworkerList());
            coworkerAdapter.notifyDataSetChanged();
            restaurant = mRestaurant;
            setInfoRestaurant();
            displayChoiceStatus();
            displayLikeStatus();
        });
    }

    /**
     * Display the restaurant information
     */
    private void setInfoRestaurant() {
        restaurantNameTextView.setText(restaurant.getRestaurantName());
        restaurantInfoTextView.setText(restaurant.getRestaurantAddress());

        displayRating(restaurant.getRestaurantRating());

        if (restaurant.getRestaurantPhotoUrl() != null) {
            Glide.with(RestaurantDetailActivity.this)
                    .load(restaurant.getRestaurantPhotoUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(restaurantPictureImageView);
        }

        displayChoiceStatus();

        binding.restaurantDetailCallButton.setOnClickListener(v -> openDialer(restaurant.getRestaurantPhone()));

        binding.restaurantDetailWebsiteButton.setOnClickListener(v -> openWebSite(restaurant.getRestaurantWebSite()));

        binding.restaurantDetailLikeButton.setOnClickListener(v -> saveLikeRestaurant());

        binding.restaurantDetailFab.setOnClickListener(v -> saveChoiceRestaurant());
    }

    /**
     * Save the coworker restaurant choice
     */
    private void saveChoiceRestaurant() {
        restaurantDetailViewModel.getCoworkerChoiceForRestaurant(Actions.SAVED)
                .observe(this, actions -> {
                    switch (actions) {
                        case ADDED:
                            changeChoiceStatus(true);
                            getRestaurantDetail();
                            break;
                        case REMOVED:
                            changeChoiceStatus(false);
                            getRestaurantDetail();
                            break;
                        case ERROR:
                            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.error_unknown_error), Toast.LENGTH_SHORT).show();
                            break;
                        case SAVED_FAILED:
                            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.error_saved_failed), Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                });
    }

    /**
     * Display the status of the coworker choice
     */
    private void displayChoiceStatus() {
        if (restaurantDetailViewModel == null) {
            initializeViewModel();
        }
        restaurantDetailViewModel.getCoworkerChoiceForRestaurant(Actions.TO_SEARCH)
                .observe(this, actions -> {
                    changeChoiceStatus(actions.equals(Actions.IS_CHOSEN));
                });
    }

    /**
     * Chane the status of the coworker choice
     *
     * @param isChosen : boolean : the restaurant is chosen or not
     */
    private void changeChoiceStatus(boolean isChosen) {
        if (isChosen) {
            restaurantFab.setImageResource(R.drawable.ic_baseline_check_circle_24);
            restaurantFab.setTag(Actions.IS_CHOSEN);
        } else {
            restaurantFab.setImageResource(R.drawable.ic_baseline_uncheck_circle_24);
            restaurantFab.setTag(Actions.NOT_CHOSEN);
        }
    }

    /**
     * Display if the restaurant is liked by the coworker
     */
    private void displayLikeStatus() {
        if (restaurantDetailViewModel == null) {
            initializeViewModel();
        }
        restaurantDetailViewModel.getCoworkerLikeForRestaurant(Actions.TO_SEARCH)
                .observe(this, actions -> {
                    changeLikeStatus(actions.equals(Actions.IS_CHOSEN));
                });
    }

    /**
     * Save the coworker choice if he likes or not the restaurant
     */
    private void saveLikeRestaurant() {
        restaurantDetailViewModel.getCoworkerLikeForRestaurant(Actions.TO_SAVE)
                .observe(this, actions -> {

                    switch (actions) {
                        case ADDED:
                            changeLikeStatus(true);
                            break;
                        case REMOVED:
                            changeLikeStatus(false);
                            break;
                        case ERROR:
                            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.error_unknown_error), Toast.LENGTH_SHORT).show();
                            break;
                        case SAVED_FAILED:
                            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.error_saved_failed), Toast.LENGTH_SHORT).show();
                            break;
                        default:

                    }
                });
    }

    /**
     * Change the display like status
     *
     * @param isChosen : boolean : is liked or not by the coworker
     */
    private void changeLikeStatus(boolean isChosen) {
        if (isChosen) {
            restaurantLike.setImageResource(R.drawable.ic_baseline_star_24);
            restaurantLike.setTag(Actions.IS_CHOSEN);
        } else {
            restaurantLike.setImageResource(R.drawable.ic_baseline_star_border_24);
            restaurantLike.setTag(Actions.NOT_CHOSEN);
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
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + Uri.encode(phone)));
            startActivity(intent);
        } else {
            Toast.makeText(RestaurantDetailActivity.this, getString(R.string.text_no_phone_number), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Display the rating with stars
     *
     * @param rating : double : rating of the restaurant
     */
    private void displayRating(double rating) {
        int numberOfStarToDisplay = Go4LunchHelper.ratingNumberOfStarToDisplay(rating);
        switch (numberOfStarToDisplay) {
            case 1:
                restaurantStar2.setVisibility(View.INVISIBLE);
                restaurantStar3.setVisibility(View.INVISIBLE);
                break;
            case 2:
                restaurantStar3.setVisibility(View.INVISIBLE);
                break;
            case 3:
                break;
            default:
                restaurantStar1.setVisibility(View.INVISIBLE);
                restaurantStar2.setVisibility(View.INVISIBLE);
                restaurantStar3.setVisibility(View.INVISIBLE);
                break;
        }
    }
}