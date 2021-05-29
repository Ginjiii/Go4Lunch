package com.example.go4lunch.goforlunch.ui.coworker;

import android.content.Context;
import android.graphics.Typeface;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.go4lunch.R;
import com.go4lunch.databinding.CoworkersItemLayoutBinding;

public class CoworkerViewHolder extends RecyclerView.ViewHolder {

    public CoworkersItemLayoutBinding binding;

    public CoworkerViewHolder(CoworkersItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void updateCoworkers(Coworker coworker) {
        Context context = binding.getRoot().getContext();
        String coworkerTextName = "";
        if (coworker.getCoworkerRestaurantChoice() != null) {
            coworkerTextName = coworker.getName() + " " + context.getString(R.string.is_eating) + " (" + coworker.getCoworkerRestaurantChoice().getRestaurantName() + ")";
            binding.nameTextView.setTypeface(binding.nameTextView.getTypeface(), Typeface.BOLD);
            binding.nameTextView.setTextColor(context.getResources().getColor(R.color.colorBlack));
        } else {
            binding.nameTextView.setTypeface(binding.nameTextView.getTypeface(), Typeface.ITALIC);
            binding.nameTextView.setTextColor(context.getResources().getColor(R.color.colorGrey));
            coworkerTextName = coworker.getName() + " " + context.getString(R.string.has_not_decided_yet);
        }
        binding.nameTextView.setText(coworkerTextName);
        Glide.with(binding.recyclerViewCoworkerItemPictureImageView.getContext())
                .load(coworker.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.recyclerViewCoworkerItemPictureImageView);
    }
}