package com.example.made.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.made.db.DatabaseContract;
import com.google.gson.annotations.SerializedName;

import static com.example.made.db.DatabaseContract.getColumnInt;
import static com.example.made.db.DatabaseContract.getColumnString;

public class Movie implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String name;
    @SerializedName("poster_path")
    private String image;
    @SerializedName("backdrop_path")
    private String thumbnail;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String release_date;
    @SerializedName("runtime")
    private int runtime;
    @SerializedName("status")
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return "https://image.tmdb.org/t/p/w185/" + image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumbnail() {
        return "https://image.tmdb.org/t/p/w185/" + thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeString(this.thumbnail);
        dest.writeString(this.overview);
        dest.writeString(this.release_date);
        dest.writeInt(this.runtime);
        dest.writeString(this.status);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie() {
    }

    public Movie(int id, String name, String image, String thumbnail, String overview, String release_date, int runtime, String status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.thumbnail = thumbnail;
        this.overview = overview;
        this.release_date = release_date;
        this.runtime = runtime;
        this.status = status;
    }

    public Movie(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.MovieCol.MOVIEID);
        this.name = getColumnString(cursor, DatabaseContract.MovieCol.NAME);
        this.image = getColumnString(cursor, DatabaseContract.MovieCol.IMAGE);
        this.thumbnail = getColumnString(cursor, DatabaseContract.MovieCol.THUMBNAIL);
        this.overview = getColumnString(cursor, DatabaseContract.MovieCol.OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.MovieCol.DATE);
        this.runtime = getColumnInt(cursor, DatabaseContract.MovieCol.RUNTIME);
        this.status = getColumnString(cursor, DatabaseContract.MovieCol.STATUS);
    }

    protected Movie(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.thumbnail = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.runtime = in.readInt();
        this.status = in.readString();
    }
}