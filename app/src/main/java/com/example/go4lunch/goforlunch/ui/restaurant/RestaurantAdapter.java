package com.example.go4lunch.goforlunch.ui.restaurant;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.databinding.RestaurantItemLayoutBinding;

import java.util.List;

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

        restaurantViewHolder.binding.restaurantItemListDistance.setText(String.valueOf(position));
        if (restaurantList.get(position).getRestaurantAddress() != null) {
            restaurantViewHolder.binding.restaurantItemListAddress.setText(restaurant.getRestaurantAddress());
        }

        if (restaurantList.get(position).getRestaurantCoworkerList() != null) {
            numberOfCoworker = restaurantList.get(position).getRestaurantCoworkerList().size();
        }
        restaurantViewHolder.binding.restaurantItemListParticipantsNumber.setText(String.valueOf(numberOfCoworker));
        int nbStar = (int) restaurantList.get(position).getRestaurantRating();
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