package com.example.trailblazers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.trailblazers.database.entities.Trail;

import java.util.List;

public class TrailAdapter extends RecyclerView.Adapter<TrailAdapter.ViewHolder> {

    private List<Trail> trails;
    private OnTrailClickListener listener;


    public TrailAdapter(List<Trail> trails) {
        this.trails = trails;
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, details;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleTextView);
            details = itemView.findViewById(R.id.detailsTextView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trail trail = trails.get(position);

        holder.title.setText(trail.getTitle());

        String info = trail.getDistance() + " miles • " + trail.getTime() + " sec";
        holder.details.setText(info);

        holder.itemView.setOnClickListener(v -> {
            listener.onTrailClick(trail);
        });
    }

    @Override
    public int getItemCount() {
        return trails.size();
    }

    public interface OnTrailClickListener {
        void onTrailClick(Trail trail);
    }
}