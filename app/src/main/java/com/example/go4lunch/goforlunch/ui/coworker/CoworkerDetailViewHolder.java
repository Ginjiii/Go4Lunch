package com.example.go4lunch.goforlunch.ui.coworker;

import android.content.Context;
import android.graphics.Typeface;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Coworker;
import com.go4lunch.R;

public class CoworkerDetailViewHolder extends RecyclerView.ViewHolder {

    public com.go4lunch.databinding.CoworkersItemLayoutBinding binding;

    public CoworkerDetailViewHolder(com.go4lunch.databinding.CoworkersItemLayoutBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void updateCoworkers(Coworker coworker) {
        Context context = binding.getRoot().getContext();
        String coworkerText = coworker.getName()
                + " " + context.getString(R.string.is_joining);
        binding.nameTextView.setTypeface(binding.nameTextView.getTypeface(), Typeface.BOLD);
        binding.nameTextView.setTextColor(context.getResources().getColor(R.color.colorBlack));
        binding.nameTextView.setText(coworkerText);

        Glide.with(binding.recyclerViewCoworkerItemPictureImageView.getContext())
                .load(coworker.getPhotoUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(binding.recyclerViewCoworkerItemPictureImageView);
    }
}