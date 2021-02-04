package com.example.go4lunch.goforlunch.ui.restaurant;


import android.content.Intent;
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

import static com.example.go4lunch.goforlunch.ui.restaurant.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
  //  private Context context;

    public void setRestaurantList(List<Restaurant> mRestaurantList) {
        restaurantList = mRestaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_layout,
         //       parent, false);
        RestaurantItemLayoutBinding binding = RestaurantItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new RestaurantViewHolder(binding);
       // return new RestaurantViewHolder(FragmentListLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        Restaurant restaurant = restaurantList.get(position);
        Log.d("DETAIL", "onBindViewHolder: "+restaurant.getRestaurantName());
        restaurantViewHolder.binding.restaurantItemListName.setText(String.valueOf(restaurant.getRestaurantName()));
        int numberOfCoworker = 0;
      //  context = restaurantViewHolder.itemView.getContext();

       restaurantViewHolder.binding.restaurantItemListDistance.setText(String.valueOf(position));
       if (restaurantList.get(position).getRestaurantAddress() != null){
           restaurantViewHolder.binding.restaurantItemListAddress.setText(restaurant.getRestaurantAddress());
       }

       if (restaurantList.get(position).getRestaurantCoworkerList() != null) {
           numberOfCoworker = restaurantList.get(position).getRestaurantCoworkerList().size();
       }
       restaurantViewHolder.binding.restaurantItemListParticipantsNumber.setText(String.valueOf(numberOfCoworker));
       //restaurantViewHolder.restaurantNumberOfCoworker.setText("(" + numberOfCoworker + ")")
        int nbStar = (int) restaurantList.get(position).getRestaurantRating();
        restaurantViewHolder.binding.restaurantItemListRate.setNumStars(nbStar);
        displayRestaurantImage(restaurantViewHolder, position);

       restaurantViewHolder.itemView.setOnClickListener(v -> {
           Intent intentRestaurantDetail = new Intent(v.getContext(), RestaurantDetailActivity.class);
           intentRestaurantDetail.putExtra(RESTAURANT_PLACE_ID, restaurant.getRestaurantPlaceId());
           v.getContext().startActivity(intentRestaurantDetail);
      });
   }

   private void displayRestaurantImage(RestaurantViewHolder restaurantViewHolder, int position) {
       Log.d("DETAIL", "displayRestaurantImage: c''est prti avec"+restaurantList.get(position).getRestaurantOpeningHours());
        if (restaurantList.get(position).getRestaurantPhotoUrl() != null) {
            Log.d("DETAIL", "displayRestaurantImage: c'est parti");

            Glide.with(restaurantViewHolder.binding.restaurantImagePhotoItem.getContext())
                    .load(restaurantList.get(position).getRestaurantPhotoUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .into(restaurantViewHolder.binding.restaurantImagePhotoItem);
        }else{

           /* Glide.with(restaurantViewHolder.binding.restaurantImagePhotoItem.getContext())
                    .load(R.drawable.restaurant_default_img)
                    .apply(RequestOptions.centerCropTransform())
                    .into(restaurantViewHolder.binding.restaurantImagePhotoItem);
        */
        }
   }

    @Override
    public int getItemCount() {
        if (restaurantList != null){
            return restaurantList.size();
        }
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