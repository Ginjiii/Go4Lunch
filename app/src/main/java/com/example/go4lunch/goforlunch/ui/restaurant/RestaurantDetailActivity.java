package com.example.go4lunch.goforlunch.ui.restaurant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.databinding.RestaurantDetailLayoutBinding;

public class RestaurantDetailActivity extends AppCompatActivity {

    public static final String RESTAURANT_PLACE_ID = "placeid";

    private Restaurant restaurant;
    private String restaurantId;

    private RestaurantDetailLayoutBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = RestaurantDetailLayoutBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        TextView restaurantNameTextView = binding.restaurantDetailName;
        TextView restaurantInfoTextView = binding.restaurantDetailInfo;
        ImageView restaurantPictureImageView = binding.restaurantDetailPicture;

    }
}
