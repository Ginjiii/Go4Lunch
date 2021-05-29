package com.example.go4lunch.goforlunch.ui.coworker;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.go4lunch.databinding.CoworkersItemLayoutBinding;

import java.util.List;

public class CoworkerDetailAdapter extends RecyclerView.Adapter<CoworkerDetailViewHolder> {

    private List<Coworker> coworkerList;

    @NonNull
    @Override
    public CoworkerDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CoworkersItemLayoutBinding binding = CoworkersItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CoworkerDetailViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoworkerDetailViewHolder holder, int position) {
        holder.updateCoworkers(coworkerList.get(position));
    }

    @Override
    public int getItemCount() {
        if (coworkerList != null) {
            return coworkerList.size();
        }
        return 0;
    }

    public void setCoworkerLists(List<Coworker> coworkerLists) {
        this.coworkerList = coworkerLists;
        notifyDataSetChanged();
    }
}
