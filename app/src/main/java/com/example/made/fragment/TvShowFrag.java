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
import com.example.made.R;
import com.example.made.adapter.MovieViewAdapter;
import com.example.made.adapter.TvShowViewAdapter;
import com.example.made.data.TvShow;
import com.example.made.data.TvShowData;

import java.util.ArrayList;


public class TvShowFrag extends Fragment{

    RecyclerView rvCategory;
    private ArrayList<TvShow> list;

    public TvShowFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.layout_tvshow, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Setup any handles to view objects here
        rvCategory = (RecyclerView) getActivity().findViewById(R.id.rv_tvshow);
        rvCategory.setHasFixedSize(true);

        list = new ArrayList<>();
        list.addAll(TvShowData.getListData());
        showRecyclerCardView();
    }

    private void showRecyclerCardView() {
        rvCategory.setLayoutManager(new LinearLayoutManager(getContext()));
        TvShowViewAdapter cardViewTvShowAdapter = new TvShowViewAdapter(getContext());
        cardViewTvShowAdapter.setListMovie(list);
        rvCategory.setAdapter(cardViewTvShowAdapter);

        ItemClickSupport.addTo(rvCategory).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedPresident(list.get(position));
            }
        });
    }

    private void showSelectedPresident(TvShow tvShow) {
        Intent moveWithDataIntent = new Intent(getActivity(), DetailActivity.class);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_INT, 2);
        moveWithDataIntent.putExtra(DetailActivity.EXTRA_TVSHOW, tvShow);
        startActivity(moveWithDataIntent);
    }
}