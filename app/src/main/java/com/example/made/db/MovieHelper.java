package com.example.made.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.made.data.Movie;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.example.made.db.DatabaseContract.MovieCol.MOVIEID;
import static com.example.made.db.DatabaseContract.NAME_TABLE_MOVIE;
import static com.example.made.db.DatabaseContract.TvShowCol.DATE;
import static com.example.made.db.DatabaseContract.TvShowCol.IMAGE;
import static com.example.made.db.DatabaseContract.TvShowCol.NAME;
import static com.example.made.db.DatabaseContract.TvShowCol.OVERVIEW;
import static com.example.made.db.DatabaseContract.TvShowCol.RUNTIME;
import static com.example.made.db.DatabaseContract.TvShowCol.STATUS;
import static com.example.made.db.DatabaseContract.TvShowCol.THUMBNAIL;

public class MovieHelper {
    private static final String DATABASE_TABLE = NAME_TABLE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static MovieHelper INSTANCE;
    private static SQLiteDatabase database;

    private MovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static MovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MovieHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    public void read() throws SQLException {
        database = databaseHelper.getReadableDatabase();
    }

    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<Movie> getAllNotes() {
        ArrayList<Movie> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MOVIEID)));
                movie.setName(cursor.getString(cursor.getColumnIndexOrThrow(NAME)));
                movie.setImage(setDataImage(cursor, IMAGE));
                movie.setThumbnail(setDataImage(cursor, THUMBNAIL));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                movie.setRelease_date(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setRuntime(cursor.getInt(cursor.getColumnIndexOrThrow(RUNTIME)));
                movie.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(STATUS)));

                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    private String setDataImage(Cursor cursor, String image) {
        String s = cursor.getString(cursor.getColumnIndexOrThrow(image));
        String[] tokens = s.split("//");

        for (String t : tokens)
            Log.e("data_gambar", t);

        return tokens[2];
    }

    public long insertNote(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(MOVIEID, movie.getId());
        args.put(NAME, movie.getName());
        args.put(IMAGE, movie.getImage());
        args.put(THUMBNAIL, movie.getThumbnail());
        args.put(OVERVIEW, movie.getOverview());
        args.put(DATE, movie.getRelease_date());
        args.put(RUNTIME, movie.getRuntime());
        args.put(STATUS, movie.getStatus());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateNote(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(MOVIEID, movie.getId());
        args.put(NAME, movie.getName());
        args.put(IMAGE, movie.getImage());
        args.put(THUMBNAIL, movie.getThumbnail());
        args.put(OVERVIEW, movie.getOverview());
        args.put(DATE, movie.getRelease_date());
        args.put(RUNTIME, movie.getRuntime());
        args.put(STATUS, movie.getStatus());
        return database.update(DATABASE_TABLE, args, MOVIEID + "= '" + movie.getId() + "'", null);
    }

    public int deleteNote(int id) {
        return database.delete(DATABASE_TABLE, MOVIEID + " = '" + id + "'", null);
    }

    public int selectNote(int id) {
        String query = "SELECT * FROM " + DATABASE_TABLE + " WHERE " + MOVIEID + "=" + id + "";
        Log.e("DATA_QUERY", query);
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getCount();
        }
        return 0;
    }
}
