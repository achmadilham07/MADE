package com.example.made.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

import static com.example.made.db.DatabaseContract.TvShowCol.RUNTIME;

public class DatabaseContract {

    public static final String NAME_TABLE_MOVIE = "movie";
    public static final String NAME_TABLE_TV = "tvshow";
    public static final String AUTHORITY_MOVIE = "com.example.made.data.Movie";
    public static final String AUTHORITY_TV = "com.example.made.data.TvShow";
    private static final String SCHEME = "content";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
    }

    public static int[] getColumnIntArray(Cursor cursor, String columnName) {
        int[] runtime = new int[1];
        runtime[0] = cursor.getInt(cursor.getColumnIndexOrThrow(RUNTIME));
        return runtime;
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static final class MovieCol implements BaseColumns {
        public static final String MOVIEID = "movieId";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String THUMBNAIL = "thumbnail";
        public static final String OVERVIEW = "overview";
        public static final String DATE = "date";
        public static final String RUNTIME = "runtime";
        public static final String STATUS = "status";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_MOVIE)
                .appendPath(NAME_TABLE_MOVIE)
                .build();
    }

    public static final class TvShowCol implements BaseColumns {
        public static final String TVID = "tvId";
        public static final String NAME = "name";
        public static final String IMAGE = "image";
        public static final String THUMBNAIL = "thumbnail";
        public static final String OVERVIEW = "overview";
        public static final String DATE = "date";
        public static final String RUNTIME = "runtime";
        public static final String STATUS = "status";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY_TV)
                .appendPath(NAME_TABLE_TV)
                .build();
    }
}