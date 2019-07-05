package com.example.made;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POKEMON = "extra_pokemon";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        Movie pokemon = intent.getParcelableExtra(EXTRA_POKEMON);

        TextView tvName, tvInfo;
        ImageView tvImg;
        tvName = findViewById(R.id.item_name);
        tvImg = findViewById(R.id.img_item_photo);
        tvInfo = findViewById(R.id.txt_description);

        tvName.setText(pokemon.getName());
        tvInfo.setText(pokemon.getOverview());

        Glide.with(getApplicationContext())
                .load(pokemon.getImage())
                .apply(new RequestOptions().override(350, 350))
                .into(tvImg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
