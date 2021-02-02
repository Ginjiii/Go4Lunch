package com.example.go4lunch.goforlunch.ui.coworker;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.example.go4lunch.goforlunch.ui.restaurant.RestaurantDetailActivity;
import com.go4lunch.R;

import java.util.List;

import static com.example.go4lunch.goforlunch.ui.restaurant.RestaurantDetailActivity.RESTAURANT_PLACE_ID;

public class CoworkerAdapter extends RecyclerView.Adapter<CoworkerAdapter.CoworkerHolder> {

    private List<Coworker> coworkerList;
    private Coworker.CoworkerRestaurantChoice mRestaurantChosen;

    public void setCoworkerList(List<Coworker> mCoworkerList) {
        coworkerList = mCoworkerList;
    }

    @NonNull
    @Override
    public CoworkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.coworkers_item_layout,
                parent, false);
        return new CoworkerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CoworkerHolder mCoworkerHolder, int position) {

        String mCoworkerText;

        Context context = mCoworkerHolder.itemView.getContext();

        if (coworkerList.get(position).getCoworkerRestaurantChosen() != null) {
            mRestaurantChosen = coworkerList.get(position).getCoworkerRestaurantChosen();
            mCoworkerText = coworkerList.get(position).getCoworkerName()
                    + " " + context.getString(R.string.text_coworker_eating) + " (" + mRestaurantChosen.getRestaurantName() + ")";
            mCoworkerHolder.mTextViewName.setTextColor(context.getResources().getColor(R.color.colorBlack));
            mCoworkerHolder.mTextViewName.setTypeface(null, Typeface.NORMAL);
        } else {
            mCoworkerText = coworkerList.get(position).getCoworkerName()
                    + " " + context.getString(R.string.text_coworker_not_decided);
            mCoworkerHolder.mTextViewName.setTextColor(context.getResources().getColor(R.color.colorLightGrey));
            mCoworkerHolder.mTextViewName.setTypeface(null, Typeface.ITALIC);
        }
        mCoworkerHolder.mTextViewName.setText(mCoworkerText);

        Glide.with(mCoworkerHolder.mImgViewCoworker.getContext())
                .load(coworkerList.get(position).getCoworkerPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(mCoworkerHolder.mImgViewCoworker);

        mCoworkerHolder.itemView.setOnClickListener(v -> {
            if (coworkerList.get(position).getCoworkerRestaurantChosen() != null) {
                Intent intentRestaurantDetail = new Intent(context, RestaurantDetailActivity.class);
                mRestaurantChosen =  coworkerList.get(position).getCoworkerRestaurantChosen();
                intentRestaurantDetail.putExtra(RESTAURANT_PLACE_ID, mRestaurantChosen.getRestaurantId());
                context.startActivity(intentRestaurantDetail);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (coworkerList == null) {
            return 0;
        } else {
            return coworkerList.size();
        }
    }

    static class CoworkerHolder extends RecyclerView.ViewHolder {

        private final TextView mTextViewName;
        private final ImageView mImgViewCoworker;

        public CoworkerHolder(@NonNull View itemView) {
            super(itemView);

            mTextViewName = itemView.findViewById(R.id.restaurant_item_list_name);
            mImgViewCoworker = itemView.findViewById(R.id.restaurant_image_photo_item);
        }
    }
}