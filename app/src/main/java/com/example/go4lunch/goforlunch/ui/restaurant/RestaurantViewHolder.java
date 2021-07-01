package com.example.go4lunch.goforlunch.ui.restaurant;

import android.content.Context;
import android.content.Intent;

import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity;
import com.go4lunch.R;
import com.go4lunch.databinding.RestaurantItemLayoutBinding;

import java.util.List;

import static com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class RestaurantViewHolder extends RecyclerView.ViewHolder {

    public final RestaurantItemLayoutBinding binding;
    private Context context;

    public RestaurantViewHolder(RestaurantItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void updateRestaurantInfo(Restaurant restaurant, List<String> coworkerIds) {
        binding.restaurantItemListName.setText(restaurant.getName());

        context = binding.getRoot().getContext();

        if (restaurant.getDistance() > 0) {
            binding.restaurantItemListDistance.setText(restaurant.getDistance() + " m");
        }
        if (restaurant.getAddress() != null) {
            binding.restaurantItemListAddress.setText(restaurant.getAddress());
        }
        int counter = checkCoworkersEatingHere(restaurant.getRestaurantID(), coworkerIds);
        binding.restaurantItemListParticipantsNumber.setText("(" + counter + ")");
        binding.restaurantItemListInfo.setText((restaurant.isOpenNow()) ? R.string.restaurant_on : R.string.restaurant_closed_today);
        TextViewCompat.setTextAppearance(binding.restaurantItemListInfo, R.style.TimeRestaurantOpen);
        if (restaurant.getPhotoReference() != null) {
            Glide.with(context)
                    .load(restaurant.getPhotoReference())
                    .centerCrop()
                    .into(binding.restaurantImagePhotoItem);
        }

        binding.restaurantItemListRate.setRating(restaurant.getRating());
        binding.restaurantItemListRate.getNumStars();

        itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, RestaurantDetailActivity.class);
            intent.putExtra(RESTAURANT_PLACE_ID, restaurant.getRestaurantID());
            context.startActivity(intent);
        });
    }

    private int checkCoworkersEatingHere(String restaurantId, List<String> coworkerIds) {
        int counter = 0;
        if (coworkerIds != null) {
            for (String id : coworkerIds) {
                if (id.equals(restaurantId)) {
                    counter++;
                }
            }
        }
        return counter;
    }
}