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
//                .appendPath("now_playing")
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
//                .appendPath("popular")
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

    private static String getSearchMovie(String query) {
        //https://api.themoviedb.org/3/search/movie?api_key=241d0503954ef8a961d37c3ef7490ede&language=en-US&query=Avenger
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("search")
                .appendPath("movie")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("query", query)
                .build()
                .toString();
    }

    private static String getSearchTv(String query) {
        //https://api.themoviedb.org/3/search/tv?api_key=241d0503954ef8a961d37c3ef7490ede&language=en-US&query=Avenger
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("search")
                .appendPath("tv")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("language", "en-US")
                .appendQueryParameter("query", query)
                .build()
                .toString();
    }

    public static String getReleaseMovie(String query) {
        // https://api.themoviedb.org/3/discover/movie?api_key=123456789&primary_release_date.gte=2019-01-31&primary_release_date.lte=2019-01-31
        return Uri.parse(BuildConfig.BASE_URL).buildUpon()
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key", BuildConfig.API_KEY)
                .appendQueryParameter("primary_release_date.gte", query)
                .appendQueryParameter("primary_release_date.lte", query)
                .build()
                .toString();
    }

    public static void doReqMovies(final MainView.MovieDataArrayList callback) {
        callback.showLoading();
        AndroidNetworking.get(getMovie())
                .setTag(DBApi.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(MovieData.class, new ParsedRequestListener<MovieData>() {
                    @Override
                    public void onResponse(MovieData response) {
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

    public static void doReqTvShows(final MainView.TvShowDataArrayList callback) {
        callback.showLoading();
        AndroidNetworking.get(getTv())
                .setTag(DBApi.class)
                .setPriority(Priority.IMMEDIATE)
                .build()
                .getAsObject(TvShowData.class, new ParsedRequestListener<TvShowData>() {
                    @Override
                    public void onResponse(TvShowData response) {
                        callback.hideLoading();
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

    public static void doReqSearchMovie(String query, final MainView.MovieDataSearchList callback) {
        callback.showLoading();
        AndroidNetworking.get(getSearchMovie(query))
                .setTag(DBApi.class)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(MovieData.class, new ParsedRequestListener<MovieData>() {
                    @Override
                    public void onResponse(MovieData response) {
                        callback.hideLoading();
                        callback.onSuccessSearchList(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public static void doReqSearchTv(String query, final MainView.TvShowDataSearchList callback) {
        callback.showLoading();
        AndroidNetworking.get(getSearchTv(query))
                .setTag(DBApi.class)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(TvShowData.class, new ParsedRequestListener<TvShowData>() {
                    @Override
                    public void onResponse(TvShowData response) {
                        callback.hideLoading();
                        callback.onSuccessSearchList(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("ERROR", "onError: ", anError);
                        callback.onFailed("Terjadi kesalahan saat menghubungi server");
                    }
                });
    }

    public static void doReqReleaseMovie(String query, final MainView.LoadReleaseCallback callback) {
        AndroidNetworking.get(getReleaseMovie(query))
                .setTag(DBApi.class)
                .setPriority(Priority.HIGH)
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
}
