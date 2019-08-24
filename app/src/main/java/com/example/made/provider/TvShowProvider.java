package com.example.made.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;

import androidx.annotation.Nullable;

import com.example.made.db.TvShowHelper;
import com.example.made.fragment.FavTvShowFrag;

import static com.example.made.db.DatabaseContract.AUTHORITY_TV;
import static com.example.made.db.DatabaseContract.NAME_TABLE_TV;
import static com.example.made.db.DatabaseContract.TvShowCol.CONTENT_URI;

public class TvShowProvider extends ContentProvider {

    private static final int NOTE = 1;
    private static final int NOTE_ID = 2;
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        // content://com.example.made/note
        sUriMatcher.addURI(AUTHORITY_TV, NAME_TABLE_TV, NOTE);
        // content://com.example.made/note/id
        sUriMatcher.addURI(AUTHORITY_TV, NAME_TABLE_TV + "/#", NOTE_ID);
    }

    private TvShowHelper tvShowHelper;

    @Override
    public boolean onCreate() {
        tvShowHelper = TvShowHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@Nullable Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        tvShowHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                cursor = tvShowHelper.queryProvider();
                break;
            case NOTE_ID:
                cursor = tvShowHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@Nullable Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@Nullable Uri uri, @Nullable ContentValues values) {
        tvShowHelper.open();
        long added;
        switch (sUriMatcher.match(uri)) {
            case NOTE:
                added = tvShowHelper.insertProvider(values);
                break;
            default:
                added = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavTvShowFrag.DataObserver(new Handler(), getContext()));
        return Uri.parse(CONTENT_URI + "/" + added);
    }

    @Override
    public int delete(@Nullable Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        tvShowHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                deleted = tvShowHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavTvShowFrag.DataObserver(new Handler(), getContext()));
        return deleted;
    }

    @Override
    public int update(@Nullable Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        tvShowHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case NOTE_ID:
                updated = tvShowHelper.updateProvider(uri.getLastPathSegment(), values);
                break;
            default:
                updated = 0;
                break;
        }
        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavTvShowFrag.DataObserver(new Handler(), getContext()));
        return updated;
    }
}