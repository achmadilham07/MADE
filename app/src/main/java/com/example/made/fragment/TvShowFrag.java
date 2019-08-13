package com.example.made.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.made.ItemClickSupport;
import com.example.made.MainView;
import com.example.made.R;
import com.example.made.activity.DetailActivity;
import com.example.made.adapter.TvShowViewAdapter;
import com.example.made.api.DBApi;
import com.example.made.data.TvShow;
import com.example.made.data.TvShowData;

import java.util.ArrayList;

public class TvShowFrag extends Fragment implements MainView.TvShowDataArrayList {

    private RecyclerView rvCategory;
    private TvShowViewAdapter movieAdapter;
    private ProgressBar progressBar;
    private ArrayList<TvShow> list = new ArrayList<>();
    private String KEY_MOVIES = "tvshows";
    private SwipeRefreshLayout swipeRefresh;

    public TvShowFrag() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        setRetainInstance(true);
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
            list = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            movieAdapter.refill(list);
        }
    }

    private void init(View view) {
        movieAdapter = new TvShowViewAdapter(list);

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
    }

    private void showRecyclerCardView() {
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedPresident(list.get(position).getId());
            }
        });
    }

    private void showSelectedPresident(int movie) {
        Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_INT, 2);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_TVSHOW, movie);
        startActivity(moveWithDataIntent);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccessTvShow(TvShowData movieResponse) {
        progressBar.setVisibility(View.GONE);
        list = movieResponse.getResults();
        movieAdapter.refill(list);
    }

    @Override
    public void onFailed(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, list);
        super.onSaveInstanceState(outState);
    }
}