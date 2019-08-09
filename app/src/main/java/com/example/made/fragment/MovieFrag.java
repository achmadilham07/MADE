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
import com.example.made.adapter.MovieViewAdapter;
import com.example.made.api.DBApi;
import com.example.made.data.Movie;
import com.example.made.data.MovieData;
import com.example.made.presenter.MainView;

import java.util.ArrayList;


public class MovieFrag extends Fragment implements MainView.MovieDataSourcesCallback {

    private RecyclerView rvCategory;
    private ArrayList<Movie> list = new ArrayList<>();
    private MovieViewAdapter movieAdapter;
    private String KEY_MOVIES = "movies";
    private ProgressBar progressBar;

    public MovieFrag() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.layout_movie, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        movieAdapter = new MovieViewAdapter(list);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        rvCategory = view.findViewById(R.id.rv_movie);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategory.setAdapter(movieAdapter);

        if (savedInstanceState == null) {
            DBApi.doReqMovies(this);
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

    private void showSelectedPresident(Movie movie) {
        Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_INT, 1);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithDataIntent);
    }

    @Override
    public void showLoading() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onSuccess(MovieData movieResponse) {
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