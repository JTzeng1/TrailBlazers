package com.example.trailblazers;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trailblazers.database.entities.Trail;

import java.util.List;

/**
 * This adapter connects the list of Trail objects to the RecyclerView.
 */
public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.ViewHolder> {
    
    private final List<Trail> trails;
    private final OnTrailClickListener listener;

    public TrailAdapter(List<Trail> trails, OnTrailClickListener listener) {
        this.trails = trails;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, details;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            details = itemView.findViewById(R.id.detailsTextView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Trail trail = trails.get(position);
        if (trail != null) {
            holder.title.setText(trail.getTitle());
            
            // Qualify Timer to avoid conflict with java.util.Timer
            String formattedTime = com.example.trailblazers.Timer.formatTime(trail.getTime());
            holder.details.setText(trail.getDistance() + " miles • " + formattedTime);

            holder.itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTrailClick(trail);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return trails == null ? 0 : trails.size();
    }

    public interface OnTrailClickListener {
        void onTrailClick(Trail trail);
    }
}
