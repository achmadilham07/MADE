package com.example.made.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class MovieData {
    @SerializedName("results")
    private ArrayList<Movie> results;

    public ArrayList<Movie> getResults() {
        return results;
    }
}