package com.example.made.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.made.db.DatabaseContract.NAME_TABLE_MOVIE;
import static com.example.made.db.DatabaseContract.NAME_TABLE_TV;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME_MOVIE = "dbmovie";
    private static final int DATABASE_VERSION = 1;
    private static final String SQL_CREATE_TABLE_MOVIE = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER NOT NULL," +    // MOVIEID
                    "%s TEXT NOT NULL," +       // NAME
                    "%s TEXT NOT NULL," +       // IMAGE
                    "%s TEXT NOT NULL," +       // THUMBNAIL
                    "%s TEXT NOT NULL," +       // OVERVIEW
                    "%s TEXT NOT NULL," +       // DATE
                    "%s INTEGER NOT NULL," +    // RUNTIME
                    "%s TEXT NOT NULL);",       // STATUS
            NAME_TABLE_MOVIE,
            DatabaseContract.MovieCol._ID,
            DatabaseContract.MovieCol.MOVIEID,
            DatabaseContract.MovieCol.NAME,
            DatabaseContract.MovieCol.IMAGE,
            DatabaseContract.MovieCol.THUMBNAIL,
            DatabaseContract.MovieCol.OVERVIEW,
            DatabaseContract.MovieCol.DATE,
            DatabaseContract.MovieCol.RUNTIME,
            DatabaseContract.MovieCol.STATUS
    );

    private static final String SQL_CREATE_TABLE_TV = String.format(
            "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "%s INTEGER NOT NULL," +    // TVID
                    "%s TEXT NOT NULL," +       // NAME
                    "%s TEXT NOT NULL," +       // IMAGE
                    "%s TEXT NOT NULL," +       // THUMBNAIL
                    "%s TEXT NOT NULL," +       // OVERVIEW
                    "%s TEXT NOT NULL," +       // DATE
                    "%s INTEGER NOT NULL," +    // RUNTIME
                    "%s TEXT NOT NULL);",       // STATUS
            NAME_TABLE_TV,
            DatabaseContract.TvShowCol._ID,
            DatabaseContract.TvShowCol.TVID,
            DatabaseContract.TvShowCol.NAME,
            DatabaseContract.TvShowCol.IMAGE,
            DatabaseContract.TvShowCol.THUMBNAIL,
            DatabaseContract.TvShowCol.OVERVIEW,
            DatabaseContract.TvShowCol.DATE,
            DatabaseContract.TvShowCol.RUNTIME,
            DatabaseContract.TvShowCol.STATUS
    );

    DatabaseHelper(Context context) {
        super(context, DATABASE_NAME_MOVIE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_TV);
        onCreate(db);
    }
}
