package com.example.mobileproject;

public class Movie {
    private String id;
    private String rank;
    private String rankUpDown;
    private String title;
    private String fullTitle;
    private String year;
    private String image;
    private String crew;
    private String imDbRating;

    public String getId() {
        return id;
    }

    public String getRank() {
        return rank;
    }

    public String getRankUpDown() {
        return rankUpDown;
    }

    public String getTitle() {
        return title;
    }

    public String getFullTitle() {
        return fullTitle;
    }

    public String getYear() {
        return year;
    }

    public String getImage() {
        return image;
    }

    public String getCrew() {
        return crew;
    }

    public String getImDbRating() {
        return imDbRating;
    }

    public String getImDbRatingCount() {
        return imDbRatingCount;
    }

    private String imDbRatingCount;
}
