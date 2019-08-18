package com.example.made.data;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.made.db.DatabaseContract;
import com.google.gson.annotations.SerializedName;

import static com.example.made.db.DatabaseContract.getColumnInt;
import static com.example.made.db.DatabaseContract.getColumnIntArray;
import static com.example.made.db.DatabaseContract.getColumnString;

public class TvShow implements Parcelable {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("poster_path")
    private String image;
    @SerializedName("backdrop_path")
    private String thumbnail;
    @SerializedName("overview")
    private String overview;
    @SerializedName("first_air_date")
    private String release_date;
    @SerializedName("episode_run_time")
    private int[] runtime;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int[] getRuntime() {
        return runtime;
    }

    public void setRuntime(int[] runtime) {
        this.runtime = runtime;
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
        dest.writeIntArray(this.runtime);
        dest.writeString(this.status);
    }

    public static final Creator<TvShow> CREATOR = new Creator<TvShow>() {
        @Override
        public TvShow createFromParcel(Parcel source) {
            return new TvShow(source);
        }

        @Override
        public TvShow[] newArray(int size) {
            return new TvShow[size];
        }
    };

    public TvShow() {
    }

    public TvShow(int id, String name, String image, String thumbnail, String overview, String release_date, int[] runtime, String status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.thumbnail = thumbnail;
        this.overview = overview;
        this.release_date = release_date;
        this.runtime = runtime;
        this.status = status;
    }

    public TvShow(Cursor cursor) {
        this.id = getColumnInt(cursor, DatabaseContract.TvShowCol.TVID);
        this.name = getColumnString(cursor, DatabaseContract.TvShowCol.NAME);
        this.image = getColumnString(cursor, DatabaseContract.TvShowCol.IMAGE);
        this.thumbnail = getColumnString(cursor, DatabaseContract.TvShowCol.THUMBNAIL);
        this.overview = getColumnString(cursor, DatabaseContract.TvShowCol.OVERVIEW);
        this.release_date = getColumnString(cursor, DatabaseContract.TvShowCol.DATE);
        this.runtime = getColumnIntArray(cursor, DatabaseContract.TvShowCol.RUNTIME);
        this.status = getColumnString(cursor, DatabaseContract.TvShowCol.STATUS);
    }

    protected TvShow(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.thumbnail = in.readString();
        this.overview = in.readString();
        this.release_date = in.readString();
        this.runtime = in.createIntArray();
        this.status = in.readString();
    }
}