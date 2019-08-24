package com.example.made.adapter;

import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.made.R;
import com.example.made.data.TvShow;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class TvShowViewAdapter extends RecyclerView.Adapter<TvShowViewAdapter.TvShowHolder> {

    private ArrayList<TvShow> items;
    private View view;

    public TvShowViewAdapter(ArrayList<TvShow> items) {
        this.items = items;
    }

    public ArrayList<TvShow> getListNotes() {
        return items;
    }

    public void refill(ArrayList<TvShow> items) {
        if (items.size() > 0) {
            this.items = new ArrayList<>();
        }
        this.items.addAll(items);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvShowHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = viewGroup;
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
            tvInfo = itemView.findViewById(R.id.runtime);
        }

        void onBind(TvShow item) {
            if (item.getImage() != null && !item.getImage().isEmpty()) {
                Picasso.get().load(item.getImage()).transform(new CropCircleTransformation()).into(ivPoster);
            }

            String title = checkTextIfNull(item.getName());
            int orientation = view.getResources().getConfiguration().orientation;

            if (title.length() > 30 && orientation == Configuration.ORIENTATION_PORTRAIT) {
                tvTitle.setText(String.format("%s...", title.substring(0, 29)));
            } else if (title.length() < 30 || orientation == Configuration.ORIENTATION_LANDSCAPE) {
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