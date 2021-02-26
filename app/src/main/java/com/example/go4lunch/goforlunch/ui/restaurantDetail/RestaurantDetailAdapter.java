package com.example.go4lunch.goforlunch.ui.restaurantDetail;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.RequestManager;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.models.places.RestaurantDetail;
import com.go4lunch.databinding.RestaurantDetailLayoutBinding;


import java.util.List;

public class RestaurantDetailAdapter extends RecyclerView.Adapter<RestaurantDetailAdapter.RestaurantDetailViewHolder> {

    private List<Coworker> coworkers;
    private RequestManager requestManager;
    private RestaurantDetail restaurantDetailList;

    public RestaurantDetailAdapter (List<Coworker> coworkers, RequestManager requestManager) {
        this.coworkers = coworkers;
        this.requestManager = requestManager;
    }

    public void setRestaurantDetailList(RestaurantDetail mRestaurantDetailList) {
        restaurantDetailList = mRestaurantDetailList;
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantDetailAdapter.RestaurantDetailViewHolder holder, int position) {
    }

    @NonNull
    @Override
    public RestaurantDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RestaurantDetailLayoutBinding binding = RestaurantDetailLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RestaurantDetailViewHolder(binding);
    }

    @Override
    public int getItemCount() {
        return coworkers.size();
    }

    void update(List<Coworker> coworkers){
        this.coworkers = coworkers;
    }

    public static class RestaurantDetailViewHolder extends RecyclerView.ViewHolder {

        private final RestaurantDetailLayoutBinding binding;

        public RestaurantDetailViewHolder(RestaurantDetailLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
