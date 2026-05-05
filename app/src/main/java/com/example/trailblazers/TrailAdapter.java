package com.example.trailblazers;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.trailblazers.database.entities.Trail;

import java.util.List;

// This adapter connects the list of Trail objects to the RecyclerView.
// It controls how each trail item is displayed and handles click events.
//TrailAdapter turns your Trail list into scrollable UI rows and handles what happens when a user taps one.
public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.ViewHolder> {
    // List of trails that will be displayed in the RecyclerView

    private List<Trail> trails;
    // handles what happens when a trail is clicked
    private OnTrailClickListener listener;


    public TrailAdapter(List<Trail> trails, OnTrailClickListener listener) {
        this.trails = trails;
        this.listener = listener;
    }

    //This class stores the UI elements like TextViews for one single thing in the RecyclerView,
    // so android doesn’t keep searching for them again and again
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, details;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            details = itemView.findViewById(R.id.detailsTextView);
        }
    }

    //it creates a new row by converting the XML layout into a
    // view that recyclerView can display
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_item, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    // puts trail data into each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trail trail = trails.get(position);
        holder.title.setText(trail.getTitle());

        // showing distance + time in one line
        holder.details.setText(trail.getDistance() + " miles • " + Timer.formatTime(trail.getTime()));

        //When a trail item is clicked, it sends the selected trail back to trial activity
        // using the click listener

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTrailClick(trail);
            }
        });
    }

    //keep track of the number of trails in the list

    public int getItemCount() {
        if (trails == null) {
            return 0;
        } else {
            return trails.size();
        }
    }


    public interface OnTrailClickListener {
        void onTrailClick(Trail trail);
    }
}