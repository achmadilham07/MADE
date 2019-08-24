package com.example.made.adapter;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.made.R;
import com.example.made.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MovieViewAdapter extends RecyclerView.Adapter<MovieViewAdapter.MovieHolder> {

    private ArrayList<Movie> items;
    private View view;

    public MovieViewAdapter(ArrayList<Movie> items) {
        this.items = items;
    }

    public ArrayList<Movie> getListNotes() {
        return items;
    }

    public void refill(ArrayList<Movie> items) {
        if (items.size() > 0) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = viewGroup;
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_cardview_president, viewGroup, false);
        return new MovieHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder movieHolder, int i) {
        movieHolder.cvNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        movieHolder.onBind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvInfo, tvVote;
        private ImageView ivPoster, ivBackdrop;
        private CardView cvNote;

        MovieHolder(@NonNull View itemView) {
            super(itemView);

            cvNote = itemView.findViewById(R.id.cardview);
            tvTitle = itemView.findViewById(R.id.item_name);
            ivPoster = itemView.findViewById(R.id.img_item_photo);
            tvInfo = itemView.findViewById(R.id.runtime);
        }

        void onBind(Movie item) {
            if (item.getImage() != null && !item.getImage().isEmpty()) {
                Picasso.get().load(item.getImage()).transform(new CropCircleTransformation()).into(ivPoster);
            }

            String title = checkTextIfNull(item.getName());
            int orientation = view.getResources().getConfiguration().orientation;

            if (title.length() > 30 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                tvTitle.setText(String.format("%s...", title.substring(0, 29)));
            } else if (title.length() <= 30 || orientation == Configuration.ORIENTATION_LANDSCAPE) {
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