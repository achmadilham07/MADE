package com.example.made.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.made.MainView;
import com.example.made.R;
import com.example.made.api.DBApi;
import com.example.made.data.Movie;
import com.example.made.data.TvShow;
import com.example.made.db.MovieHelper;
import com.example.made.db.TvShowHelper;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;

public class DetailActivity extends AppCompatActivity implements MainView.MovieDataList, MainView.TvShowDataList {

    public static final String EXTRA_MOVIE = "id_movie";
    public static final String MOVIE = "movie";
    public static final String EXTRA_TVSHOW = "id_tvshow";
    public static final String TVSHOW = "tvshow";
    public static final String EXTRA_INT = "id_int";
    private CollapsingToolbarLayout toolbarLayout;
    private ProgressBar progressBar;
    private TextView tvInfo, tvRelease, tvStat, tvTime;
    private ImageView tvImgBack, tvImgPoster;
    private Menu menuItem;
    private Movie movie;
    private TvShow tvShow;
    private int data, idMovie, idTv;
    private Boolean isFavorite = false;
    private MovieHelper movieHelper;
    private TvShowHelper tvShowHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        Intent intent = getIntent();
        data = intent.getIntExtra(EXTRA_INT, 1);

        if (savedInstanceState == null) {
            if (data == 1) {
                idMovie = intent.getIntExtra(EXTRA_MOVIE, 0);
                DBApi.doReqIdMovies(idMovie, this);
            } else if (data == 2) {
                idTv = intent.getIntExtra(EXTRA_TVSHOW, 0);
                DBApi.doReqIdTv(idTv, this);
            }
        } else {
            if (data == 1) {
                movie = savedInstanceState.getParcelable(MOVIE);
                if (movie != null)
                    setIDMovie(movie);
            } else if (data == 2) {
                tvShow = savedInstanceState.getParcelable(TVSHOW);
                if (tvShow != null)
                    setIDTv(tvShow);
            }
        }
        favoriteState();
    }

    private void init() {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarLayout = findViewById(R.id.toolbarlayout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvImgBack = findViewById(R.id.img_item_photo);
        tvImgPoster = findViewById(R.id.poster);
        tvRelease = findViewById(R.id.releaseDate);
        tvStat = findViewById(R.id.status);
        tvInfo = findViewById(R.id.overview);
        tvTime = findViewById(R.id.runtime);

        setVisibleText(View.GONE);

        movieHelper = MovieHelper.getInstance(getApplicationContext());
        tvShowHelper = TvShowHelper.getInstance(getApplicationContext());
    }

    private void setVisibleText(int gone) {
        findViewById(R.id.textView1).setVisibility(gone);
        findViewById(R.id.textView2).setVisibility(gone);
        findViewById(R.id.textView3).setVisibility(gone);
        findViewById(R.id.textView4).setVisibility(gone);
    }

    private void favoriteState() {
        try {
            long result = 0;
            if (data == 1) {
                movieHelper.read();
                result = movieHelper.selectNote(idMovie);
            } else if (data == 2) {
                tvShowHelper.read();
                result = tvShowHelper.selectNote(idTv);
            }
            Log.e("DATA_RESULT", String.valueOf(result));
            if (result != 0)
                isFavorite = true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favourite, menu);
        menuItem = menu;
        setFavorite();
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (item.getItemId() == R.id.id_fav) {
            if (isFavorite)
                removeFromFavorite();
            else
                addToFavorite();

            isFavorite = !isFavorite;
            setFavorite();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFavorite() {
        if (isFavorite)
            menuItem.getItem(0).setIcon(R.drawable.ic_favorite);
        else
            menuItem.getItem(0).setIcon(R.drawable.ic_favorite_border);
    }

    private void removeFromFavorite() {
        try {
            long result = 0;
            if (data == 1) {
                result = movieHelper.deleteNote(idMovie);
            } else if (data == 2) {
                result = tvShowHelper.deleteNote(idTv);
            }
            Toast.makeText(getApplicationContext(), "Removed to favorite", Toast.LENGTH_LONG).show();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addToFavorite() {
        try {
            long result = 0;
            if (data == 1) {
                result = movieHelper.insertNote(movie);
            } else if (data == 2) {
                result = tvShowHelper.insertNote(tvShow);
            }
            Toast.makeText(getApplicationContext(), "Added to favorite", Toast.LENGTH_LONG).show();
        } catch (SQLiteConstraintException e) {
            Toast.makeText(getApplicationContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessTv(TvShow movieResponse) {
        tvShow = movieResponse;
        setVisibleText(View.VISIBLE);
        setIDTv(movieResponse);
    }

    @Override
    public void onSuccessMovie(Movie movieResponse) {
        movie = movieResponse;
        setVisibleText(View.VISIBLE);
        setIDMovie(movieResponse);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
    }

    private void setIDMovie(Movie movie) {
        toolbarLayout.setTitle(movie.getName());
        tvInfo.setText(movie.getOverview());
        tvRelease.setText(movie.getRelease_date());
        tvStat.setText(movie.getStatus());
        Log.e("data_error", "" + movie.getRuntime());
        tvTime.setText(String.format("%d %s", movie.getRuntime(), getResources().getString(R.string.minute)));
        Picasso.get().load(movie.getThumbnail()).transform(new BlurTransformation(getApplicationContext(), 3)).into(tvImgBack);
        Picasso.get().load(movie.getImage()).into(tvImgPoster);
    }

    private void setIDTv(TvShow tvShow) {
        toolbarLayout.setTitle(tvShow.getName());
        tvInfo.setText(tvShow.getOverview());
        tvRelease.setText(tvShow.getRelease_date());
        tvStat.setText(tvShow.getStatus());
        tvTime.setText(String.format("%d %s", tvShow.getRuntime()[0], getResources().getString(R.string.minute)));
        Picasso.get().load(tvShow.getThumbnail()).transform(new BlurTransformation(getApplicationContext(), 3)).into(tvImgBack);
        Picasso.get().load(tvShow.getImage()).into(tvImgPoster);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (data == 1) {
            outState.putParcelable(MOVIE, movie);

        } else if (data == 2) {
            outState.putParcelable(TVSHOW, tvShow);
        }
        super.onSaveInstanceState(outState);
    }
}
