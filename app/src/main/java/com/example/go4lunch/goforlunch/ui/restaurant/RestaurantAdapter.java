package com.example.go4lunch.goforlunch.ui.restaurant;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.example.go4lunch.goforlunch.utils.Utils;
import com.go4lunch.R;

import java.util.List;

import static com.example.go4lunch.goforlunch.ui.restaurant.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class RestaurantAdapter extends RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder> {

    private List<Restaurant> restaurantList;
    private Context context;

    public void setRestaurantList(List<Restaurant> mRestaurantList) {
        restaurantList = mRestaurantList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_item_layout,
                parent, false);
        return new RestaurantViewHolder(view);
    }

    @SuppressLint({"SetText", "SetTextI18n"})
    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder restaurantViewHolder, int position) {
        int numberOfWorkmate = 0;
        context = restaurantViewHolder.itemView.getContext();

        restaurantViewHolder.restaurantName.setText(restaurantList.get(position).getRestaurantName());
        restaurantViewHolder.restaurantDistance.setText(restaurantList.get(position).getRestaurantDistanceText());
        if (restaurantList.get(position).getRestaurantAddress() != null){
            restaurantViewHolder.restaurantAddress.setText(Utils.formatAddress(restaurantList.get(position).getRestaurantAddress()));
        }

        if (restaurantList.get(position).getRestaurantCoworkerList() != null) {
            numberOfWorkmate = restaurantList.get(position).getRestaurantCoworkerList().size();
        }

        restaurantViewHolder.restaurantNumberOfCoworker.setText("(" + numberOfWorkmate + ")");

        restaurantViewHolder.itemView.setOnClickListener(v -> {
            Intent intentRestaurantDetail = new Intent(context, RestaurantDetailActivity.class);
            intentRestaurantDetail.putExtra(RESTAURANT_PLACE_ID, restaurantList.get(position).getRestaurantPlaceId());
            context.startActivity(intentRestaurantDetail);
        });
    }

    @Override
    public int getItemCount() {
        if (restaurantList != null){
            return restaurantList.size();
        }
        return 0;
        }

    static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        private final TextView restaurantName;
        private final TextView restaurantDistance;
        private final TextView restaurantAddress;
        private final TextView restaurantNumberOfCoworker;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            restaurantName = itemView.findViewById(R.id.restaurant_item_list_name);
            restaurantDistance = itemView.findViewById(R.id.restaurant_item_list_distance);
            restaurantAddress = itemView.findViewById(R.id.restaurant_item_list_address);
            restaurantNumberOfCoworker = itemView.findViewById(R.id.restaurant_item_list_participants_number);
        }
    }
}