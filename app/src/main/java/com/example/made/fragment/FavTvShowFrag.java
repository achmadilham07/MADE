package com.example.made.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.made.ItemClickSupport;
import com.example.made.MainView;
import com.example.made.R;
import com.example.made.activity.DetailActivity;
import com.example.made.activity.HomeActivity;
import com.example.made.adapter.FavTvAdapter;
import com.example.made.data.TvShow;
import com.example.made.db.TvShowHelper;
import com.google.android.material.snackbar.Snackbar;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.example.made.db.DatabaseContract.TvShowCol.CONTENT_URI;
import static com.example.made.helper.MappingHelper.mapCursorTv;

public class FavTvShowFrag extends Fragment implements MainView.LoadTvShowCallback {

    private RecyclerView recyclerView;
    private static HandlerThread handlerThread;
    private ArrayList<TvShow> tvShows = new ArrayList<>();
    private ProgressBar progressBar;
    private String KEY_MOVIES = "tvshow_fav";
    private TvShowHelper tvShowHelper;
    private SwipeRefreshLayout swipeRefresh;
    private FavTvAdapter favTvAdapter;
    private DataObserver myObserver;

    public FavTvShowFrag() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_movie, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        save_reload_state(savedInstanceState);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new LoadNoteAsync(getContext(), FavTvShowFrag.this).execute();
                swipeRefresh.setRefreshing(false);
                if (savedInstanceState != null) {
                    savedInstanceState.putParcelableArrayList(KEY_MOVIES, favTvAdapter.getItems());
                }
            }
        });

        showRecyclerCardView();
    }

    private void save_reload_state(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            new LoadNoteAsync(getContext(), this).execute();
            Toast.makeText(getContext(), "TvShow Fav Ngulang lagi", Toast.LENGTH_SHORT).show();
        } else {
            tvShows = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            favTvAdapter.refill(tvShows);
        }
    }

    private void init(View view) {
        favTvAdapter = new FavTvAdapter(tvShows);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light
        );

        recyclerView = view.findViewById(R.id.rv_movie);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(favTvAdapter);

        handlerThread = new HandlerThread("DataObserver");
        handlerThread.start();
        Handler handler = new Handler(handlerThread.getLooper());
        myObserver = new DataObserver(handler, getContext());
        getContext().getContentResolver().registerContentObserver(CONTENT_URI, true, myObserver);

        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.read();
    }

    private void showRecyclerCardView() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedPresident(favTvAdapter.getItems().get(position).getId());
            }
        });
    }

    private void showSelectedPresident(int movie) {
        Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
        Uri uri = Uri.parse(CONTENT_URI + "/" + movie);
        moveWithDataIntent.setData(uri);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_INT, 2);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_TVSHOW, movie);
        startActivity(moveWithDataIntent);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, favTvAdapter.getItems());
        super.onSaveInstanceState(outState);
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(Cursor notes) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<TvShow> listNotes = mapCursorTv(notes);
        if (listNotes.size() > 0) {
            favTvAdapter.refill(listNotes);
        } else {
            favTvAdapter.refill(new ArrayList<TvShow>());
            favTvAdapter.emptyItem();
            showSnackbarMessage("Tidak ada data saat ini");
        }
    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(recyclerView, message, Snackbar.LENGTH_SHORT).show();
    }

    private static class LoadNoteAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<MainView.LoadTvShowCallback> weakCallback;

        private LoadNoteAsync(Context context, MainView.LoadTvShowCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor notes) {
            super.onPostExecute(notes);
            weakCallback.get().postExecute(notes);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadNoteAsync(context, (HomeActivity) context).execute();

        }
    }
}