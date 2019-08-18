package com.example.made.helper;

import android.database.Cursor;

import com.example.made.data.Movie;
import com.example.made.data.TvShow;

import java.util.ArrayList;


public class MappingHelper {

    public static ArrayList<Movie> mapCursorMovie(Cursor notesCursor) {
        ArrayList<Movie> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            notesList.add(new Movie(notesCursor));
        }

        return notesList;
    }

    public static ArrayList<TvShow> mapCursorTv(Cursor notesCursor) {
        ArrayList<TvShow> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            notesList.add(new TvShow(notesCursor));
        }

        return notesList;
    }
}
