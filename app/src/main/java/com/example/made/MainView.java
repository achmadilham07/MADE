package com.example.made;

import android.database.Cursor;

import com.example.made.data.Movie;
import com.example.made.data.MovieData;
import com.example.made.data.TvShow;
import com.example.made.data.TvShowData;


public interface MainView {

    interface MovieDataArrayList {
        void showLoading();

        void hideLoading();

        void onSuccessMovie(MovieData movieResponse);

        void onFailed(String error);
    }

    interface MovieDataSearchList {
        void showLoading();

        void hideLoading();

        void onSuccessSearchList(MovieData movieResponse);

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

        void hideLoading();

        void onSuccessTvShow(TvShowData tvShowResponse);

        void onFailed(String error);
    }

    interface TvShowDataSearchList {
        void showLoading();

        void hideLoading();

        void onSuccessSearchList(TvShowData movieResponse);

        void onFailed(String error);
    }

    interface TvShowDataList {
        void showLoading();

        void hideLoading();

        void onSuccessTv(TvShow movieResponse);

        void onFailed(String error);
    }

    interface LoadMovieCallback {
        void preExecute();

        void postExecute(Cursor notes);
    }

    interface LoadTvShowCallback {
        void preExecute();

        void postExecute(Cursor notes);
    }
}
