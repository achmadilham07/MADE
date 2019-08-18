package com.example.made.adapter;

import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.made.R;
import com.example.made.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class FavMovAdapter extends RecyclerView.Adapter<FavMovAdapter.FavMovHolder> {

    private ArrayList<Movie> items;
    private View view;

    public FavMovAdapter(ArrayList<Movie> items) {
        this.items = items;
    }

    public ArrayList<Movie> getItems() {
        return items;
    }

    public void refill(ArrayList<Movie> items) {
        if (items.size() > 0) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    public void emptyItem() {
        this.items.removeAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavMovHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = viewGroup;
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_president, viewGroup, false);
        return new FavMovHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FavMovHolder favMovHoldet, int i) {
        favMovHoldet.onBind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class FavMovHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvInfo;
        private ImageView ivPoster;

        FavMovHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.item_name);
            ivPoster = itemView.findViewById(R.id.img_item_photo);
            tvInfo = itemView.findViewById(R.id.runtime);
        }

        void onBind(Movie movie) {
            if (movie.getImage() != null && !movie.getImage().isEmpty()) {
                Picasso.get().load(movie.getImage()).transform(new CropCircleTransformation()).into(ivPoster);
            }

            String title = checkTextIfNull(movie.getName());
            int orientation = view.getResources().getConfiguration().orientation;

            if (title.length() > 30 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                tvTitle.setText(String.format("%s...", title.substring(0, 29)));
            } else if (title.length() <= 30 || orientation == Configuration.ORIENTATION_LANDSCAPE) {
                tvTitle.setText(checkTextIfNull(movie.getName()));
            }
            tvInfo.setText(checkTextIfNull(movie.getOverview()));
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
