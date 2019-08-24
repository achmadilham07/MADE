package com.example.made.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    MovieHelper(Context context) {
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
                movie = new Movie(cursor);
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
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
        Cursor cursor = database.rawQuery(query, null);
        if (cursor != null) {
            cursor.moveToFirst();
            return cursor.getCount();
        }
        return 0;
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, MOVIEID + " = ?", new String[]{id});
    }
}
