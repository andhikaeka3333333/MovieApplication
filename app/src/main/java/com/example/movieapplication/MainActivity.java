

package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;


import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<MovieItem> movieList = new ArrayList<>();
    private MovieAdapter movieAdapter;
    private int currentPage = 1;
    private String currentQuery = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //hapus jika perlu [kode sampah]
        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(getResources().getDrawable(R.color.black));
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.black));
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        movieAdapter = new MovieAdapter(this, movieList, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(MovieItem movieItem) {
                showFullscreenImageDialog("https://image.tmdb.org/t/p/w500" + movieItem.getPosterPath());
            }
        });
        recyclerView.setAdapter(movieAdapter);

        // Inisialisasi tombol-tombol untuk setiap halaman
        Button btnPage1 = findViewById(R.id.btnPage1);
        Button btnPage2 = findViewById(R.id.btnPage2);
        Button btnPage3 = findViewById(R.id.btnPage3);
        Button btnPage4 = findViewById(R.id.btnPage4);
        Button btnPage5 = findViewById(R.id.btnPage5);
        Button btnPage6 = findViewById(R.id.btnPage6);

        SearchView searchView = findViewById(R.id.searchView);




        // ... (inisialisasi tombol untuk setiap halaman yang diinginkan)

        // Set OnClickListener untuk masing-masing tombol
        btnPage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                loadPage(1);
            }
        });

        btnPage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                loadPage(2);
            }
        });

        btnPage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                loadPage(3);
            }
        });

        btnPage4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                loadPage(4);
            }
        });

        btnPage5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                loadPage(5);
            }
        });

        btnPage6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setQuery("", false);
                loadPage(6);
            }
        });


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when the user submits the query
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle the case when the user clears the search query
                if (newText.isEmpty()) {
                    // Clear the current search query and reload the default data
                    currentQuery = "";
                    fetchData();
                }
                return true;
            }
        });

        fetchData();
    }

    private void performSearch(String query) {
        // Check if the query is different from the previous one
        if (!query.equalsIgnoreCase(currentQuery)) {
            // Reset currentPage to 1 for a new search
            currentPage = 1;

            // Save the current search query
            currentQuery = query;

            // Clear the existing movie list
            movieList.clear();
            movieAdapter.notifyDataSetChanged();

            // Load the first page of search results
            fetchData();
        }
    }



    // Metode untuk memuat data dari halaman tertentu
    private void loadPage(int pageNumber) {
        // Reset list dan currentPage
        movieList.clear();
        movieAdapter.notifyDataSetChanged();
        currentPage = pageNumber;

        // Memuat data sesuai dengan halaman yang dipilih
        fetchData();
    }

    private void fetchData() {
        String apiKey = "95f5899241f1efc89ff17c72f32c5041";
        String apiUrl;

        if (currentQuery.isEmpty()) {
            // Jika tidak ada query pencarian, ambil data biasa
            apiUrl = "https://api.themoviedb.org/3/discover/movie" +
                    "?api_key=" + apiKey +
                    "&language=en-US" +
                    "&sort_by=popularity.desc" +
                    "&include_adult=false" +
                    "&include_video=false" +
                    "&page=" + currentPage;
        } else {
            // Jika ada query pencarian, gunakan endpoint pencarian
            apiUrl = "https://api.themoviedb.org/3/search/movie" +
                    "?api_key=" + apiKey +
                    "&language=en-US" +
                    "&query=" + currentQuery;
        }

        AndroidNetworking.get(apiUrl)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");

                            for (int i = 0; i < resultsArray.length(); i++) {
                                JSONObject movieObject = resultsArray.getJSONObject(i);

                                String title = movieObject.getString("title");
                                String posterPath = movieObject.getString("poster_path");
                                String description = movieObject.getString("overview");
                                MovieItem movieItem = new MovieItem(title, posterPath , description);
                                movieList.add(movieItem);
                            }

                            movieAdapter.notifyDataSetChanged();

                            // Setelah berhasil memuat data, tambahkan 1 ke currentPage
                            currentPage++;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("Error", "Error fetching data: " + anError.getErrorBody());
                    }
                });
    }

    public void onMovieItemClick(MovieItem movieItem) {
        showFullscreenImageDialog("https://image.tmdb.org/t/p/w500" + movieItem.getPosterPath());
    }


    private void showFullscreenImageDialog(String imageUrl) {
        Dialog dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_fullscreen_image);

        // Toolbar
        Toolbar toolbar = dialog.findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(Color.TRANSPARENT);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        // Menambahkan tombol back
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss(); // Menutup dialog saat tombol back diklik
            }
        });

        ImageView fullscreenImageView = dialog.findViewById(R.id.fullscreenImageView);

        // Menggunakan Glide untuk memuat dan menampilkan gambar dari URL
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(this)
                .load(imageUrl)
                .apply(requestOptions)
                .into(fullscreenImageView);

        dialog.show();
    }

    public void showFullScreenProfile(View view) {
        Intent intent = new Intent(MainActivity.this, ActivityProfile.class);
        startActivity(intent);
    }

}
