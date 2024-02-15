package com.example.movieapplication;

// MovieItem.java
public class MovieItem {
    private String title;
    private String posterPath;

    private String description;

    public MovieItem(String title, String posterPath , String description) {
        this.title = title;
        this.posterPath = posterPath;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }
}

