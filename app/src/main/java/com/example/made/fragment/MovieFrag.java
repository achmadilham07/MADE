package com.example.made.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.made.DetailActivity;
import com.example.made.ItemClickSupport;
import com.example.made.data.Movie;
import com.example.made.data.MovieData;
import com.example.made.R;
import com.example.made.adapter.MovieViewAdapter;
import com.example.made.data.TvShow;

import java.util.ArrayList;


public class MovieFrag extends Fragment{

    RecyclerView rvCategory;
    private ArrayList<Movie> list;

    public MovieFrag() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.layout_movie, parent, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        rvCategory = (RecyclerView) getActivity().findViewById(R.id.rv_movie);
        rvCategory.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(MovieData.getListData());
        showRecyclerCardView();
    }

    private void showRecyclerCardView() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieViewAdapter cardViewMovieAdapter = new MovieViewAdapter(getContext());
        cardViewMovieAdapter.setListMovie(list);
        rvCategory.setAdapter(cardViewMovieAdapter);
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
}