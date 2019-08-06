package com.example.made.api;

import android.net.Uri;
import android.util.Log;

import com.example.made.BuildConfig;
import com.example.made.data.MovieData;
import com.example.made.data.TvShowData;
import com.example.made.presenter.MainView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

public class DBApi {

    public static String getMovie(){
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key",BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build()
                .toString();
    }

    public static String getTv(){
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("discover")
                .appendPath("tv")
                .appendQueryParameter("api_key",BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build()
                .toString();
    }

    public static void doReqMovies(final MainView.MovieDataSourcesCallback callback) {
        Log.e("error", getMovie());
        callback.showLoading();
        AndroidNetworking.get(getMovie())
                .setTag(DBApi.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieData.class, new ParsedRequestListener<MovieData>() {
                    @Override
                    public void onResponse(MovieData response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public static void doReqTvShows(final MainView.TvShowDataSourcesCallback callback) {
        Log.e("error", getTv());
        callback.showLoading();
        AndroidNetworking.get(getTv())
                .setTag(DBApi.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(TvShowData.class, new ParsedRequestListener<TvShowData>() {
                    @Override
                    public void onResponse(TvShowData response) {
                        callback.onSuccess(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }
}
