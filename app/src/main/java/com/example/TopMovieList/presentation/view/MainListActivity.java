/*
 * MIT License
 * Copyright (c) 2020. Antoine Mairet
 */

package com.example.TopMovieList.presentation.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.Toast;
import com.example.TopMovieList.Singletons;
import com.example.TopMovieList.presentation.controller.MainListController;
import com.example.TopMovieList.presentation.model.Movie;
import com.example.TopMovieList.R;
import java.util.ArrayList;

public class MainListActivity extends AppCompatActivity  {

    public MainListController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = new MainListController(this, Singletons.getGson(), Singletons.getSharedPreferences(getApplicationContext()));
        controller.onStart();

    }

    // Display the list of movies using an listadapter
    public void showList(ArrayList<Movie> listMovie) {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ListAdapter mAdapter = new ListAdapter(listMovie, new ListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Movie item) {
                controller.onItemClick(item);
            }
        });
        recyclerView.setAdapter(mAdapter);

    }

    // Show an error when we can't access to the API
    public void showError() {
        Toast.makeText(getApplicationContext(),"API Error",Toast.LENGTH_SHORT).show();
    }

}
