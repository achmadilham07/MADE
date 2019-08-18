package com.example.made.widget;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.made.R;
import com.example.made.data.Movie;
import com.example.made.data.TvShow;
import com.example.made.db.MovieHelper;
import com.example.made.db.TvShowHelper;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final List<Bitmap> mWidgetItems = new ArrayList<>();
    private final List<String> mWidgetString = new ArrayList<>();
    private final Context mContext;
    private ArrayList<TvShow> tvShows = new ArrayList<>();
    private ArrayList<Movie> movies = new ArrayList<>();

    StackRemoteViewsFactory(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {
        TvShowHelper tvShowHelper = TvShowHelper.getInstance(mContext);
        tvShowHelper.read();
        MovieHelper movieHelper = MovieHelper.getInstance(mContext);
        movieHelper.read();
        tvShows = tvShowHelper.getAllNotes();
        movies = movieHelper.getAllNotes();
    }

    @Override
    public void onDataSetChanged() {
        try {
            Bitmap b;
            for (Movie movie : movies) {
                b = Picasso.get().load(movie.getImage()).get();
                mWidgetItems.add(b);
                mWidgetString.add(movie.getName());
            }
            for (TvShow tvShow : tvShows) {
                b = Picasso.get().load(tvShow.getImage()).get();
                mWidgetItems.add(b);
                mWidgetString.add(tvShow.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return mWidgetItems.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);
        rv.setImageViewBitmap(R.id.imageView, mWidgetItems.get(position));
        Bundle extras = new Bundle();
        extras.putString(ImageBannerWidget.EXTRA_ITEM, mWidgetString.get(position));
        Intent fillInIntent = new Intent();
        fillInIntent.putExtras(extras);
        rv.setOnClickFillInIntent(R.id.imageView, fillInIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
