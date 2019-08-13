package com.example.made.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TvShowData {
    @SerializedName("results")
    private ArrayList<TvShow> results;

    public ArrayList<TvShow> getResults() {
        return results;
    }

}