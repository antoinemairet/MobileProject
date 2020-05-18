package com.example.mobileproject;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        TextView fullTitle = findViewById(R.id.fullTitle);
        TextView rank = findViewById(R.id.rank);
        TextView crew = findViewById(R.id.crew);
        TextView rating = findViewById(R.id.rating);
        TextView year = findViewById(R.id.year);

        String fullTitleString ="fullTitle not set";
        String rankString ="rank not set";
        String ratingString ="rating not set";
        String crewString ="crew not set";
        String yearString ="year not set";

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            fullTitleString = extras.getString("fullTitle");
            rankString = extras.getString("rank");
            ratingString = extras.getString("rating");
            crewString = extras.getString("crew");
            yearString = extras.getString("year");
        }

        fullTitle.setText(fullTitleString);
        rank.setText("Rank: "+rankString);
        crew.setText("Crew: "+crewString);
        rating.setText("Rating: "+ratingString);
        year.setText("Year: "+yearString);
    }

}
