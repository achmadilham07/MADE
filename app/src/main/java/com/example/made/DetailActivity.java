package com.example.made;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.made.data.Movie;
import com.example.made.data.TvShow;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE = "extra_movie";
    public static final String EXTRA_TVSHOW = "extra_tvshow";
    public static final String EXTRA_INT = "extra_int";
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            tvName.setText(movie.getName());
            tvInfo.setText(movie.getOverview());

            Glide.with(getApplicationContext())
                    .load(movie.getImage())
                    .apply(new RequestOptions().override(350, 350))
                    .into(tvImg);
        } else if (data == 2) {
            tvName.setText(tvShow.getName());
            tvInfo.setText(tvShow.getOverview());

            Glide.with(getApplicationContext())
                    .load(tvShow.getImage())
                    .apply(new RequestOptions().override(350, 350))
                    .into(tvImg);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
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
