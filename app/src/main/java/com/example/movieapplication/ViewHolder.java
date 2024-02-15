package com.example.movieapplication;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView moviePoster;
    TextView movieTitle, movieDescription;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        moviePoster = itemView.findViewById(R.id.moviePoster);
        movieTitle = itemView.findViewById(R.id.movieTitle);
        movieDescription = itemView.findViewById(R.id.movieDescription);
    }
}