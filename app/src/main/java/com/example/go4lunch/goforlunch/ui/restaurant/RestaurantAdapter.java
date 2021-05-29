package com.example.go4lunch.goforlunch.ui.restaurant;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.databinding.RestaurantItemLayoutBinding;

import java.util.List;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private List<String> coworkerIds;

    public RestaurantAdapter() {
    }

    public void setRestaurantList(List<Restaurant> restaurantList, List<String> coworkerIds) {
        this.restaurantList = restaurantList;
        this.coworkerIds = coworkerIds;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantItemLayoutBinding binding = RestaurantItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RestaurantViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        holder.updateRestaurantInfo(restaurantList.get(position), coworkerIds);
    }

    @Override
    public int getItemCount() {
        if (restaurantList != null)
            return restaurantList.size();
        return 0;
    }
}