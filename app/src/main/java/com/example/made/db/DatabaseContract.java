package com.example.made.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    static String NAME_TABLE_MOVIE = "movie";
    static String NAME_TABLE_TV = "tvshow";

    static final class MovieCol implements BaseColumns {
        static String MOVIEID = "movieId";
        static String NAME = "name";
        static String IMAGE = "image";
        static String THUMBNAIL = "thumbnail";
        static String OVERVIEW = "overview";
        static String DATE = "date";
        static String RUNTIME = "runtime";
        static String STATUS = "status";
    }

    static final class TvShowCol implements BaseColumns {
        static String TVID = "tvId";
        static String NAME = "name";
        static String IMAGE = "image";
        static String THUMBNAIL = "thumbnail";
        static String OVERVIEW = "overview";
        static String DATE = "date";
        static String RUNTIME = "runtime";
        static String STATUS = "status";
    }
}