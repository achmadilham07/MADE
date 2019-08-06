package com.example.made.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.made.R;
import com.example.made.data.Movie;
import com.example.made.data.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.BlurTransformation;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class TvShowViewAdapter extends RecyclerView.Adapter<TvShowViewAdapter.TvShowHolder> {

    private ArrayList<TvShow> items;

    public TvShowViewAdapter(ArrayList<TvShow> items) {
        this.items = items;
    }

    public void refill(ArrayList<TvShow> items) {
        this.items = new ArrayList<>();
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_president, viewGroup, false);
        return new TvShowHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TvShowHolder tvShowHolder, int i) {
        tvShowHolder.onBind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class TvShowHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvInfo, tvVote;
        private ImageView ivPoster, ivBackdrop;

        TvShowHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_name);
            ivPoster = itemView.findViewById(R.id.img_item_photo);
            tvInfo = itemView.findViewById(R.id.txt_description);
        }

        void onBind(TvShow item) {
            if (item.getImage() != null && !item.getImage().isEmpty()) {
                Picasso.get().load(item.getImage()).transform(new CropCircleTransformation()).into(ivPoster);
            }

            String title = checkTextIfNull(item.getName());
            if (title.length() > 30) {
                tvTitle.setText(String.format("%s...", title.substring(0, 29)));
            } else {
                tvTitle.setText(checkTextIfNull(item.getName()));
            }
            tvInfo.setText(checkTextIfNull(item.getOverview()));
        }

        String checkTextIfNull(String text) {
            if (text != null && !text.isEmpty()) {
                return text;
            } else {
                return "-";
            }
        }
    }
}