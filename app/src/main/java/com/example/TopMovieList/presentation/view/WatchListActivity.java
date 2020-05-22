/*
 * MIT License
 * Copyright (c) 2020. Antoine Mairet
 */

package com.example.TopMovieList.presentation.view;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.TopMovieList.R;
import com.example.TopMovieList.Singletons;
import com.example.TopMovieList.presentation.controller.WatchListController;
import com.example.TopMovieList.presentation.model.Movie;
import java.util.ArrayList;

public class WatchListActivity extends AppCompatActivity {

    public WatchListController controller;
    private ArrayList<Movie> watchListMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_watch_list);

        controller = new WatchListController(this, Singletons.getGson(), Singletons.getSharedPreferences(getApplicationContext()));

        //Step 1 : get the watch list
        watchListMovie = controller.getWatchList();

        // Step 2 : Check if we have a movie to add (if we are from details)
        watchListMovie = controller.ifNewMovieInWatchList(watchListMovie);

        // Step 3 : Display the watch list
        displayOfWatchListActivity(watchListMovie);

    }

    // Manage the display if the watch list is empty and if not called the showWatchList function
    private void displayOfWatchListActivity(ArrayList<Movie> watchListMovie) {

        TextView txtEmptyList = findViewById(R.id.txtWatchListEmpty);
        TextView txtHowAddToTheList = findViewById(R.id.txtHowAddMovie);

        if(watchListMovie.size() != 0) { // If the watch list is empty, set visible specific display
            txtEmptyList.setVisibility(View.INVISIBLE);
            txtHowAddToTheList.setVisibility(View.INVISIBLE);
            showWatchList(watchListMovie);
        }else{ // Watch list not empty and set invisible specific display
            txtEmptyList.setVisibility(View.VISIBLE);
            txtHowAddToTheList.setVisibility(View.VISIBLE);
        }

    }

    // Show the list of movies using an adapter
    private void showWatchList(ArrayList<Movie> watchListMovie) {

        RecyclerView recyclerView = findViewById(R.id.recyclerViewWatchList);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        WatchListAdapter mAdapter = new WatchListAdapter(watchListMovie, new WatchListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie item) { // Function when user click on the movie
                controller.onItemClick(item);
            }

            @Override
            public void deleteMovie(Movie item, ArrayList<Movie> values) { // Function when the user click on delete the movie
                controller.deleteMovieFromWatchList(item,values);
            }
        });
        recyclerView.setAdapter(mAdapter);

    }

}
