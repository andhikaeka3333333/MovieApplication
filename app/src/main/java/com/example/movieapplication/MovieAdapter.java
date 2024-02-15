package com.example.movieapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapplication.MovieItem;
import com.example.movieapplication.R;
import com.example.movieapplication.ViewHolder;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<ViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(MovieItem movieItem);
    }

    private List<MovieItem> movieList;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public MovieAdapter(Context context, List<MovieItem> movieList, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.movieList = movieList;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MovieItem movieItem = movieList.get(position);

        // Set data ke dalam views
        holder.movieTitle.setText(movieItem.getTitle());
        holder.movieDescription.setText(movieItem.getDescription());


        // Gunakan Glide untuk memuat gambar dari URL
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);


        Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500" + movieItem.getPosterPath())
                .apply(requestOptions)
                .into(holder.moviePoster);

        // Set listener untuk menangani klik item
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(movieItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
