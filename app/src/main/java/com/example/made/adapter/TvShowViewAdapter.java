package com.example.made.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.made.R;
import com.example.made.data.Movie;
import com.example.made.data.TvShow;

import java.util.ArrayList;

public class TvShowViewAdapter extends RecyclerView.Adapter<TvShowViewAdapter.CardViewViewHolder> {
    private Context context;
    private ArrayList<TvShow> listMovie;

    private ArrayList<TvShow> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<TvShow> listMovie) {
        this.listMovie = listMovie;
    }

    public TvShowViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_president, viewGroup, false);
        return new CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewViewHolder cardViewViewHolder, int i) {
        TvShow p = getListMovie().get(i);
        Glide.with(context)
                .load(p.getThumbnail())
                .apply(new RequestOptions().override(350, 350))
                .into(cardViewViewHolder.imgPhoto);
        cardViewViewHolder.tvName.setText(p.getName());
        cardViewViewHolder.tvInfo.setText(p.getOverview());
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPhoto;
        TextView tvName, tvInfo;

        CardViewViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo);
            tvName = itemView.findViewById(R.id.item_name);
            tvInfo = itemView.findViewById(R.id.txt_description);
        }
    }
}