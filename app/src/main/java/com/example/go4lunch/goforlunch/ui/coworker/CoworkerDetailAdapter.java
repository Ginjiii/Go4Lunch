package com.example.go4lunch.goforlunch.ui.coworker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.go4lunch.goforlunch.models.Restaurant;
import com.go4lunch.R;
import com.go4lunch.databinding.CoworkersItemLayoutBinding;

import java.util.List;

public class CoworkerDetailAdapter extends RecyclerView.Adapter<CoworkerDetailAdapter.CoworkerHolder> {

    private List<Restaurant.CoworkerList> coworkerLists;


    public void setCoworkerLists(List<Restaurant.CoworkerList> coworkerLists) {
        this.coworkerLists = coworkerLists;
    }

    @NonNull
    @Override
    public CoworkerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CoworkersItemLayoutBinding binding = CoworkersItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CoworkerHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoworkerHolder holder, int position) {
        String coworkerText ;

        Context lContext = holder.itemView.getContext();

        coworkerText = coworkerLists.get(position).getCoworkerName()
                + " " + lContext.getString(R.string.is_joining);
        holder.binding.recyclerViewCoworkerItemTextView.setText(coworkerText);

        Glide.with(holder.binding.recyclerViewCoworkerItemPictureImageView.getContext())
                .load(coworkerLists.get(position).getCoworkerUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.binding.recyclerViewCoworkerItemPictureImageView);
    }

    @Override
    public int getItemCount() {
        if(coworkerLists == null) {
            return 0;
        } else {
            return coworkerLists.size();
        }
    }

    static class CoworkerHolder extends RecyclerView.ViewHolder {

        private CoworkersItemLayoutBinding binding;

        public CoworkerHolder(@NonNull CoworkersItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
