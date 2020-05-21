/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.TopMovieList.presentation.view;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.TopMovieList.R;
import com.example.TopMovieList.Singletons;
import com.example.TopMovieList.presentation.controller.WatchListController;
import com.example.TopMovieList.presentation.model.Movie;
import java.util.ArrayList;

public class WatchListActivity extends AppCompatActivity {

    private TextView txtHowAddToTheList;
    private TextView txtEmptyList;
    public WatchListController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_watch_list);
        txtEmptyList = findViewById(R.id.txtWatchListEmpty);
        txtHowAddToTheList = findViewById(R.id.txtHowAddMovie);

        controller = new WatchListController(this, Singletons.getGson(), Singletons.getSharedPreferences(getApplicationContext()));

        ArrayList<Movie> watchListMovie = controller.getWatchList();
        watchListMovie = controller.ifNewMovieInWatchList(watchListMovie); // Add a new movie if user add one
        controller.saveWatchList(watchListMovie); // Save the modification of the watch list
        watchListMovie = controller.getWatchList(); // get the watch list
        displayOfWatchListActivity(watchListMovie);

    }

    private void displayOfWatchListActivity(ArrayList<Movie> watchListMovie) {

        if(watchListMovie.size() != 0) {
            txtEmptyList.setVisibility(View.INVISIBLE);
            txtHowAddToTheList.setVisibility(View.INVISIBLE);
            showWatchList(watchListMovie);
        }else{
            txtEmptyList.setVisibility(View.VISIBLE);
            txtHowAddToTheList.setVisibility(View.VISIBLE);
            Toast.makeText(getApplicationContext(),"WatchList Null",Toast.LENGTH_SHORT).show();
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
            public void onItemClick(Movie item) {
                controller.onItemClick(item);
            }

            @Override
            public void deleteMovie(Movie item, ArrayList<Movie> values) {
                controller.deleteMovieFromWatchList(item,values);
            }
        });
        recyclerView.setAdapter(mAdapter);

    }


}
