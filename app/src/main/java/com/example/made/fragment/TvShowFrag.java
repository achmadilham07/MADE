package com.example.made.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.made.DetailActivity;
import com.example.made.ItemClickSupport;
import com.example.made.R;
import com.example.made.adapter.TvShowViewAdapter;
import com.example.made.api.DBApi;
import com.example.made.data.TvShow;
import com.example.made.data.TvShowData;
import com.example.made.presenter.MainView;

import java.util.ArrayList;


public class TvShowFrag extends Fragment implements MainView.TvShowDataSourcesCallback {

    RecyclerView rvCategory;
    private ArrayList<TvShow> list = new ArrayList<>();
    TvShowViewAdapter movieAdapter;
    private String KEY_MOVIES = "tvshows";
    ProgressBar progressBar;

    public TvShowFrag() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.layout_movie, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieAdapter = new TvShowViewAdapter(list);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        rvCategory = view.findViewById(R.id.rv_movie);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            DBApi.doReqTvShows(this);
        } else {
            list = savedInstanceState.getParcelableArrayList(KEY_MOVIES);
            movieAdapter.refill(list);
        }

        showRecyclerCardView();
    }

    private void showRecyclerCardView() {
        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedPresident(list.get(position));
            }
        });
    }

    private void showSelectedPresident(TvShow movie) {
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
    public void onSuccess(TvShowData movieResponse) {
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