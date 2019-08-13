package com.example.made.api;

import android.net.Uri;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.made.BuildConfig;
import com.example.made.MainView;
import com.example.made.data.Movie;
import com.example.made.data.MovieData;
import com.example.made.data.TvShow;
import com.example.made.data.TvShowData;

public class DBApi {

    private static String getMovie() {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build()
                .toString();
    }

    private static String getTv() {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("discover")
                .appendPath("tv")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("page", "1")
                .build()
                .toString();
    }

    private static String getIdMovie(int idMovie) {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("movie")
                .appendPath("" + idMovie)
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .build()
                .toString();
    }

    private static String getIdTv(int idTv) {
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("tv")
                .appendPath("" + idTv)
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .build()
                .toString();
    }

    public static void doReqMovies(final MainView.MovieDataArrayList callback) {
        Log.e("error", getMovie());
        callback.showLoading();
        AndroidNetworking.get(getMovie())
                .setTag(DBApi.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieData.class, new ParsedRequestListener<MovieData>() {
                    @Override
                    public void onResponse(MovieData response) {
                        callback.onSuccessMovie(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public static void doReqTvShows(final MainView.TvShowDataArrayList callback) {
        Log.e("error", getTv());
        callback.showLoading();
        AndroidNetworking.get(getTv())
                .setTag(DBApi.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(TvShowData.class, new ParsedRequestListener<TvShowData>() {
                    @Override
                    public void onResponse(TvShowData response) {
                        callback.onSuccessTvShow(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public static void doReqIdMovies(int idMovie, final MainView.MovieDataList callback) {
        Log.e("error", getIdMovie(idMovie));
        callback.showLoading();
        AndroidNetworking.get(getIdMovie(idMovie))
                .setTag(DBApi.class)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(Movie.class, new ParsedRequestListener<Movie>() {
                    @Override
                    public void onResponse(Movie response) {
                        callback.hideLoading();
                        callback.onSuccessMovie(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public static void doReqIdTv(int idTv, final MainView.TvShowDataList callback) {
        Log.e("error", getIdTv(idTv));
        callback.showLoading();
        AndroidNetworking.get(getIdTv(idTv))
                .setTag(DBApi.class)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(TvShow.class, new ParsedRequestListener<TvShow>() {
                    @Override
                    public void onResponse(TvShow response) {
                        callback.hideLoading();
                        callback.onSuccessTv(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }
}
