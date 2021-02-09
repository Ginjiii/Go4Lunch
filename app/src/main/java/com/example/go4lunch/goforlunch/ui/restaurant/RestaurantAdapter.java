package com.example.go4lunch.goforlunch.ui.restaurant;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.databinding.RestaurantItemLayoutBinding;

import java.util.List;

import static com.example.go4lunch.goforlunch.utils.Utils.distFrom;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;

    public void setRestaurantList(List<Restaurant> mRestaurantList) {
        restaurantList = mRestaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantItemLayoutBinding binding = RestaurantItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        restaurantViewHolder.binding.restaurantItemListName.setText(String.valueOf(restaurant.getRestaurantName()));
        int numberOfCoworker = 0;
        String statusOpening = "Closed";

        if (restaurant.getRestaurantDistance() > 0) {
            restaurantViewHolder.binding.restaurantItemListDistance.setText(String.valueOf((int)restaurant.getRestaurantDistance())+" m");
        }
        if (restaurant.getRestaurantAddress() != null) {
            restaurantViewHolder.binding.restaurantItemListAddress.setText(restaurant.getRestaurantAddress());
        }

        if (restaurant.getRestaurantCoworkerList() != null) {
            numberOfCoworker =restaurant.getRestaurantCoworkerList().size();
        }
        Log.d("Yo", "onBindViewHolder: off ");

        if (restaurant.getRestaurantOpeningHours() != null) {

            if (restaurant.getRestaurantOpeningHours().getOpenNow() != null)
            {
                Log.d("Yo", "onBindViewHolder: "+restaurant.getRestaurantOpeningHours().getOpenNow());
                if (restaurant.getRestaurantOpeningHours().getOpenNow())
                {
                    Log.d("Yo", "getRestaurantOpeningHours: "+restaurant.getRestaurantOpeningHours().getOpenNow());

                    statusOpening = "Open" ;

                }else{
                    statusOpening = "Closed";
                }
            }
            else{
                statusOpening = "Closed";
            }
        }
        restaurantViewHolder.binding.restaurantItemListParticipantsNumber.setText(String.valueOf(numberOfCoworker));
        restaurantViewHolder.binding.restaurantItemListInfo.setText(statusOpening);
        int nbStar = (int) restaurant.getRestaurantRating();
        restaurantViewHolder.binding.restaurantItemListRate.setNumStars(nbStar);
        displayRestaurantImage(restaurantViewHolder, position);
    }

    private void displayRestaurantImage(RestaurantViewHolder restaurantViewHolder, int position) {
        if (restaurantList.get(position).getRestaurantPhotoUrl() != null) {
            Glide.with(restaurantViewHolder.binding.restaurantImagePhotoItem.getContext())
                    .load(restaurantList.get(position).getRestaurantPhotoUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(restaurantViewHolder.binding.restaurantImagePhotoItem);
        }
    }

    @Override
    public int getItemCount() {
        if (restaurantList != null)
            return restaurantList.size();
        return 0;
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantItemLayoutBinding binding;

        public RestaurantViewHolder(RestaurantItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}