package com.example.go4lunch.goforlunch.ui.restaurant;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Restaurant;

import com.go4lunch.R;
import com.go4lunch.databinding.RestaurantDetailLayoutBinding;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    private RestaurantDetailLayoutBinding binding;

    public RestaurantViewHolder(RestaurantDetailLayoutBinding restaurantDetailLayoutBinding) {
        super(restaurantDetailLayoutBinding.getRoot());
        TextView restaurantDetailNameTextView = binding.findViewById(R.id.restaurant_item_list_name);
        TextView restaurantDetailInfoTextView = binding.restaurantDetailInfo;
        ImageView restaurantDetailPictureImageView = binding.restaurantDetailPicture;
    }

    public void updateRestaurantInfo(Restaurant restaurant, RequestManager glide){
        name.setText(restaurant.getName());

        String address = restaurant.getAddress();
        typeAdress.setText(address);

        String distanceToDisplay = "0m";
        if(restaurant.getDistance() != null){
            distanceToDisplay = String.format("%sm", restaurant.getDistance().toString());
        }
        distance.setText(distanceToDisplay);

        if(restaurant.getUrlPhoto() != null){
            glide.load(restaurant.getUrlPhoto())
                    .into(imageView);
        }

        glide.load(restaurant.getUrlPhoto())
                .apply(RequestOptions
                        .centerCropTransform())
                .into();
    }
}
