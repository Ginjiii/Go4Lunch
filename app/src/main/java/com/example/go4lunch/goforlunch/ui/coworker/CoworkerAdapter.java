package com.example.go4lunch.goforlunch.ui.coworker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity;
import com.go4lunch.R;
import com.go4lunch.databinding.CoworkersItemLayoutBinding;


import java.util.List;

import static com.example.go4lunch.goforlunch.ui.restaurantDetail.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class CoworkerAdapter extends RecyclerView.Adapter<CoworkerAdapter.CoworkerViewHolder> {

    private List<Coworker> coworkerList;
    private Coworker.CoworkerRestaurantChoice restaurantChoice;

    public void setCoworkerList(List<Coworker> mCoworkerList) {
        coworkerList = mCoworkerList;
    }

    @NonNull
    @Override
    public CoworkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CoworkersItemLayoutBinding binding = CoworkersItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CoworkerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoworkerAdapter.CoworkerViewHolder coworkerViewHolder, int position) {

        String coworkerTextName;
        Context context = coworkerViewHolder.itemView.getContext();

        if (coworkerList.get(position).getCoworkerRestaurantChosen() != null) {
            restaurantChoice = coworkerList.get(position).getCoworkerRestaurantChosen();
            coworkerTextName = coworkerList.get(position).getCoworkerName() + " " + context.getString(R.string.is_eating) + " (" + restaurantChoice.getRestaurantName() + ")";
        } else {
            coworkerTextName = coworkerList.get(position).getCoworkerName() + " " + context.getString(R.string.has_not_decided_yet);
        }
        coworkerViewHolder.binding.recyclerViewCoworkerItemTextView.setText(coworkerTextName);

        Glide.with(coworkerViewHolder.binding.recyclerViewCoworkerItemPictureImageView.getContext())
                .load(coworkerList.get(position).getCoworkerPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(coworkerViewHolder.binding.recyclerViewCoworkerItemPictureImageView);

        coworkerViewHolder.itemView.setOnClickListener(v -> {
            if (coworkerList.get(position).getCoworkerRestaurantChosen() != null) {
                Intent intent = new Intent(context, RestaurantDetailActivity.class);
                restaurantChoice = coworkerList.get(position).getCoworkerRestaurantChosen();
                intent.putExtra(RESTAURANT_PLACE_ID, restaurantChoice.getRestaurantId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (coworkerList != null) {
            return coworkerList.size();
        }
        return 0;
    }

    static class CoworkerViewHolder extends RecyclerView.ViewHolder{

        private CoworkersItemLayoutBinding binding;

        public CoworkerViewHolder(CoworkersItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
