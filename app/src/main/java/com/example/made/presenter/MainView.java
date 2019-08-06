package com.example.made.presenter;

import com.example.made.data.MovieData;
import com.example.made.data.TvShowData;


public interface MainView {

    interface MovieDataSourcesCallback {

        void showLoading();

        void onSuccess(MovieData movieResponse);

        void onFailed(String error);
    }

    interface TvShowDataSourcesCallback {

        void showLoading();

        void onSuccess(TvShowData tvShowResponse);

        void onFailed(String error);
    }
}
