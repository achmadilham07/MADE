package com.example.made;

import com.example.made.data.Movie;
import com.example.made.data.MovieData;
import com.example.made.data.TvShow;
import com.example.made.data.TvShowData;


public interface MainView {

    interface MovieDataArrayList {
        void showLoading();

        void onSuccessMovie(MovieData movieResponse);

        void onFailed(String error);
    }

    interface MovieDataList {
        void showLoading();

        void hideLoading();

        void onSuccessMovie(Movie movieResponse);

        void onFailed(String error);
    }

    interface TvShowDataArrayList {
        void showLoading();

        void onSuccessTvShow(TvShowData tvShowResponse);

        void onFailed(String error);
    }

    interface TvShowDataList {
        void showLoading();

        void hideLoading();

        void onSuccessTv(TvShow movieResponse);

        void onFailed(String error);
    }
}
