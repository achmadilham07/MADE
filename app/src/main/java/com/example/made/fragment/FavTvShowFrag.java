package com.example.made.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.example.made.R;
import com.example.made.activity.DetailActivity;
import com.example.made.adapter.TvShowViewAdapter;
import com.example.made.data.TvShow;
import com.example.made.db.TvShowHelper;

import java.util.ArrayList;

public class FavTvShowFrag extends Fragment {

    private RecyclerView recyclerView;
    private TvShowViewAdapter tvShowViewAdapter;
    private ProgressBar progressBar;
    private ArrayList<TvShow> tvshow = new ArrayList<>();
    private String KEY_MOVIES = "tvshow_fav";
    private TvShowHelper tvShowHelper;
    private SwipeRefreshLayout swipeRefresh;

    public FavTvShowFrag() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setRetainInstance(true);
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
                save_reload_state(savedInstanceState);
                swipeRefresh.setRefreshing(false);
            }
        });

        showRecyclerCardView();
    }

    private void save_reload_state(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            Toast.makeText(getContext(), "TvShow Fav Ngulang lagi", Toast.LENGTH_SHORT).show();
            showLoading();
            onSuccessMovie();
        } else {
            tvshow = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            tvShowViewAdapter.refill(tvshow);
        }
    }

    private void init(View view) {
        tvShowViewAdapter = new TvShowViewAdapter(tvshow);

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
        recyclerView.setAdapter(tvShowViewAdapter);

        tvShowHelper = TvShowHelper.getInstance(getContext());
        tvShowHelper.read();
    }

    private void showRecyclerCardView() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedPresident(tvshow.get(position).getId());
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
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelableArrayList(KEY_MOVIES, tvshow);
        super.onSaveInstanceState(outState);
    }

    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    public void onSuccessMovie() {
        tvshow = tvShowHelper.getAllNotes();
        tvShowViewAdapter.refill(tvshow);
        progressBar.setVisibility(View.GONE);
    }
}