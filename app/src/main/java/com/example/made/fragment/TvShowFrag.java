package com.example.made.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.made.ItemClickSupport;
import com.example.made.MainView;
import com.example.made.R;
import com.example.made.activity.DetailActivity;
import com.example.made.adapter.TvShowViewAdapter;
import com.example.made.api.DBApi;
import com.example.made.data.TvShow;
import com.example.made.data.TvShowData;

import java.util.ArrayList;

import static com.example.made.db.DatabaseContract.TvShowCol.CONTENT_URI;

public class TvShowFrag extends Fragment implements MainView.TvShowDataArrayList, MainView.TvShowDataSearchList {

    private RecyclerView rvCategory;
    private ArrayList<TvShow> tvShows = new ArrayList<>();
    private ArrayList<TvShow> tvShow = new ArrayList<>();
    private TvShowViewAdapter movieAdapter;
    private ProgressBar progressBar;
    private String KEY_MOVIES = "tvshows";
    private SwipeRefreshLayout swipeRefresh;
    private SearchView searchView;
    private SearchView.OnQueryTextListener searchQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String s) {
            DBApi.doReqSearchTv(s, TvShowFrag.this);
            searchView.clearFocus();
            return false;
        }

        @Override
        public boolean onQueryTextChange(String s) {
            loadQuery(s);
            return false;
        }
    };

    public TvShowFrag() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.layout_movie, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        init(view);

        save_reload_state(savedInstanceState);

        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                save_reload_state(savedInstanceState);
                swipeRefresh.setRefreshing(false);
            }
        });

        showRecyclerCardView();
    }

    private void save_reload_state(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Toast.makeText(getContext(), "Tv Ngulang lagi", Toast.LENGTH_SHORT).show();
            DBApi.doReqTvShows(this);
        } else {
            tvShows = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            movieAdapter.refill(tvShows);
        }
    }

    private void init(View view) {
        movieAdapter = new TvShowViewAdapter(tvShows);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        swipeRefresh = view.findViewById(R.id.swipeRefresh);
        swipeRefresh.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_green_light,
                android.R.color.holo_red_light,
                android.R.color.holo_orange_light
        );

        rvCategory = view.findViewById(R.id.rv_movie);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setAdapter(movieAdapter);

        setHasOptionsMenu(true);
    }

    private void showRecyclerCardView() {
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedPresident(tvShows.get(position).getId());
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
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.id_search);
        searchView = (SearchView) menuItem.getActionView();
        searchView.setIconifiedByDefault(true);
        searchView.setQueryHint("looking something...");
        searchView.setFocusable(false);
        searchView.setIconified(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(searchQueryListener);
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                movieAdapter.refill(tvShow);
                tvShows = movieAdapter.getListNotes();
                searchView.onActionViewCollapsed();
                return false;
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    private void loadQuery(String s) {
        ArrayList<TvShow> filterdata = new ArrayList<>();
        String nextText = s.toLowerCase();
        for (TvShow data : tvShow) {
            String judul = data.getName().toLowerCase();
            if (judul.contains(nextText))
                filterdata.add(data);
        }
        movieAdapter.refill(filterdata);
        tvShows = movieAdapter.getListNotes();
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessSearchList(TvShowData movieResponse) {
        tvShows = movieResponse.getResults();
        movieAdapter.refill(tvShows);
    }

    @Override
    public void onSuccessTvShow(TvShowData movieResponse) {
        tvShows = movieResponse.getResults();
        tvShow = new ArrayList<>();
        tvShow.addAll(tvShows);
        movieAdapter.refill(tvShows);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, tvShows);
        super.onSaveInstanceState(outState);
    }
}