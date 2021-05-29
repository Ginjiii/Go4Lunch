package com.example.go4lunch.goforlunch.ui.coworker;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.go4lunch.goforlunch.models.Coworker;
import com.go4lunch.databinding.CoworkersItemLayoutBinding;

import java.util.List;

public class CoworkerAdapter extends RecyclerView.Adapter<CoworkerViewHolder> {

    private List<Coworker> coworkerList;

    public CoworkerAdapter(List<Coworker> coworkerList) {
        this.coworkerList = coworkerList;
    }

    @NonNull
    @Override
    public CoworkerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CoworkersItemLayoutBinding binding = CoworkersItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new CoworkerViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CoworkerViewHolder holder, int position) {
        holder.updateCoworkers(coworkerList.get(position));
    }

    @Override
    public int getItemCount() {
        if (coworkerList != null) {
            return coworkerList.size();
        }
        return 0;
    }

    public void updateCoworkers(List<Coworker> coworkers) {
        this.coworkerList = coworkers;
        notifyDataSetChanged();
    }
}

