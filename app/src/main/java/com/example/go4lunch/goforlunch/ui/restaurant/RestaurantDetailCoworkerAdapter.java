package com.example.go4lunch.goforlunch.ui.restaurant;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.R;

import java.util.List;

public class RestaurantDetailCoworkerAdapter extends RecyclerView.Adapter<RestaurantDetailCoworkerAdapter.CoworkerHolder> {

    private List<Restaurant.CoworkerList> coworkerList;

    public void setCoworkerList(List<Restaurant.CoworkerList> mCoworkerList) {
        coworkerList = mCoworkerList;
    }

    @NonNull
    @Override
    public CoworkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.coworkers_item_layout,
                parent,false);
        return new CoworkerHolder(lView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoworkerHolder coworkerHolder, int position) {
        String lTxtCoworker;

        Context lContext = coworkerHolder.itemView.getContext();

        lTxtCoworker = coworkerList.get(position).getCoworkerName()
                + " " + lContext.getString(R.string.text_coworker_joining);
        coworkerHolder.mTxtViewName.setText(lTxtCoworker);

        Glide.with(coworkerHolder.mImgViewCoworker.getContext())
                .load(coworkerList.get(position).getCoworkerUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(coworkerHolder.mImgViewCoworker);
    }


    @Override
    public int getItemCount() {
        if(coworkerList == null) {
            return 0;
        } else {
            return coworkerList.size();
        }
    }

    static class CoworkerHolder extends RecyclerView.ViewHolder {

        private final TextView mTxtViewName;
        private final ImageView mImgViewCoworker;

        public CoworkerHolder(@NonNull View itemView) {
            super(itemView);

            mTxtViewName = itemView.findViewById(R.id.recycler_view_coworker_item_text_view);
            mImgViewCoworker = itemView.findViewById(R.id.recycler_view_coworker_item_picture_image_view);
        }
    }
}
