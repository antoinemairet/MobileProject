/*
 * MIT License
 * Copyright (c) 2020. Antoine Mairet
 */

package com.example.TopMovieList.presentation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.TopMovieList.R;
import com.example.TopMovieList.Singletons;
import com.example.TopMovieList.presentation.controller.DetailsController;
import com.example.TopMovieList.presentation.model.Movie;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class DetailsActivity extends AppCompatActivity {

    public DetailsController controller;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        controller = new DetailsController(this, Singletons.getGson(), Singletons.getSharedPreferences(getApplicationContext()));

        Button buttonAddWatchListShow = findViewById(R.id.AddWatchListButton);
        TextView alreadyAdded = findViewById(R.id.txtAlreadyInWatchList);

        //Hide the display "already added"
        alreadyAdded.setVisibility(View.INVISIBLE);

        ArrayList<Movie> watchList = controller.getWatchList();
        movie = controller.fetchMovieFromPreviousActivity();

        setDetails();

        controller.testAlreadyInWatchList(watchList, movie, alreadyAdded, buttonAddWatchListShow);
        controller.setButtonAddWatchList(buttonAddWatchListShow, movie);

    }

    // Set all the details of the movie on the different text view
    private void setDetails() {

        //Define all the txtView et image with their id
        TextView fullTitle = findViewById(R.id.fullTitle);
        TextView rank = findViewById(R.id.rank);
        TextView crew = findViewById(R.id.crew);
        TextView rating = findViewById(R.id.rating);
        TextView year = findViewById(R.id.year);
        ImageView image = findViewById(R.id.imageMovie);

        // We set all the text fields
        fullTitle.setText(movie.getFullTitle());
        rank.setText(String.format("Rank: %s", movie.getRank()));
        crew.setText(String.format("Crew: %s", movie.getCrew()));
        rating.setText(String.format("Rating: %s", movie.getImDbRating()));
        year.setText(String.format("Year: %s", movie.getYear()));

        // We set the image from the URL address using Picasso
        Picasso.get().load(movie.getImage()).into(image);

    }

}
