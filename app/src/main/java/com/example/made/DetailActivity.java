package com.example.made;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.made.data.Movie;
import com.example.made.data.TvShow;
import com.squareup.picasso.Picasso;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    public static final String EXTRA_INT = "extra_int";
    private Menu menu;
    private Toolbar toolbar;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        init();

        Intent intent = getIntent();
        int data = intent.getIntExtra(EXTRA_INT, 0);

        Movie movie = new Movie();
        TvShow tvShow = new TvShow();

        if (data == 1) {
            movie = intent.getParcelableExtra(EXTRA_MOVIE);
            getSupportActionBar().setTitle(movie.getName());
        } else if (data == 2) {
            tvShow = intent.getParcelableExtra(EXTRA_TVSHOW);
            getSupportActionBar().setTitle(tvShow.getName());
        }

        TextView tvName, tvInfo;
        ImageView tvImg;
        tvName = findViewById(R.id.item_name);
        tvImg = findViewById(R.id.img_item_photo);
        tvInfo = findViewById(R.id.txt_description);

        if (data == 1) {
            toolbar.setTitle(movie.getName());
            tvInfo.setText(movie.getOverview());

            Picasso.get().load(movie.getThumbnail()).transform(new BlurTransformation(getApplicationContext(), 3)).into(tvImg);
        } else if (data == 2) {
            toolbar.setTitle(tvShow.getName());
            tvInfo.setText(tvShow.getOverview());

            Picasso.get().load(tvShow.getThumbnail()).transform(new BlurTransformation(getApplicationContext(), 3)).into(tvImg);
        }

        progressBar.setVisibility(View.GONE);
    }

    private void init() {
        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        AppBarLayout mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    isShow = true;
                    showOption(R.id.action_change_settings);
                } else if (isShow) {
                    isShow = false;
                    hideOption(R.id.action_change_settings);
                }
            }
        });
    }


    private void hideOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(false);
    }

    private void showOption(int id) {
        MenuItem item = menu.findItem(id);
        item.setVisible(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        hideOption(R.id.action_change_settings);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_change_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
