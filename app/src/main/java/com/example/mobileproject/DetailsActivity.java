/*
 * Copyright (c) 2020. Antoine Mairet
 * All Rights Reserved
 */

package com.example.mobileproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {


    String fullTitleString ;
    String rankString ;
    String ratingString ;
    String crewString;
    String yearString ;
    String imageString ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);

        TextView fullTitle = findViewById(R.id.fullTitle);
        TextView rank = findViewById(R.id.rank);
        TextView crew = findViewById(R.id.crew);
        TextView rating = findViewById(R.id.rating);
        TextView year = findViewById(R.id.year);
        ImageView image = findViewById(R.id.imageMovie);
        Bundle extras = getIntent().getExtras();

        if(extras != null){
            //We fetch all the data send by the previous activity and put it in specific variables using each specific key
            fullTitleString = extras.getString("fullTitle");
            rankString = extras.getString("rank");
            ratingString = extras.getString("rating");
            crewString = extras.getString("crew");
            yearString = extras.getString("year");
            imageString = extras.getString("image");
        }

        // We set all the text fields
        fullTitle.setText(fullTitleString);
        rank.setText(String.format("Rank: %s", rankString));
        crew.setText(String.format("Crew: %s", crewString));
        rating.setText(String.format("Rating: %s", ratingString));
        year.setText(String.format("Year: %s", yearString));
        // We set the image from the URL address using Picasso
        Picasso.get().load(imageString).into(image);
    }

}
