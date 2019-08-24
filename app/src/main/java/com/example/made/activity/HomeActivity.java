package com.example.made.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.util.ArrayMap;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.made.MainView;
import com.example.made.R;
import com.example.made.fragment.FavoriteFrag;
import com.example.made.fragment.MovieFrag;
import com.example.made.fragment.TvShowFrag;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity implements MainView.LoadMovieCallback, MainView.LoadTvShowCallback {

    private final Fragment fragment1 = new MovieFrag();
    private final Fragment fragment2 = new TvShowFrag();
    private final Fragment fragment3 = new FavoriteFrag();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = new Fragment();
    private String STATE_HELPER = "helper";
    private String STATE_ITEM = "item";
    private BottomNavigationView navView;
    private ArrayMap<Integer, Fragment> fragmentMap = new ArrayMap<>();
    private int itemid = 0;
    private ProgressBar progressBar;
    private FragmentStateHelper stateHelper;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            itemid = item.getItemId();
            switch (itemid) {
                case R.id.navigation_home:
                    active = fragment1;
                    break;
                case R.id.navigation_dashboard:
                    active = fragment2;
                    break;
                case R.id.navigation_notifications:
                    active = fragment3;
                    break;
            }
            fragmentMap.put(item.getItemId(), active);

            if (navView.getSelectedItemId() != 0) {
                saveCurrentState(itemid);
                stateHelper.restoreState(active, item.getItemId());
            }

            addFragment(active);

            return true;
        }
    };

    private void addFragment(Fragment fragment) {
        fm.beginTransaction()
                .setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
                .replace(R.id.content, fragment)
                .commitNowAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        save_reload_state(savedInstanceState);
    }

    private void init() {
        navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);

        stateHelper = new FragmentStateHelper(fm);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));
    }

    private void save_reload_state(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(fragment1);
        } else {
            Bundle helperState = savedInstanceState.getBundle(STATE_HELPER);
            itemid = savedInstanceState.getInt(STATE_ITEM);
            if (helperState != null) {
                stateHelper.restoreHelperState(helperState);
            }
        }
    }

    private void saveCurrentState(int item) {
        Fragment oldFragment = fragmentMap.get(item);
        if (oldFragment != null) {
            stateHelper.saveState(oldFragment, item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        } else if (item.getItemId() == R.id.notification_setting) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveCurrentState(itemid);
        outState.putBundle(STATE_HELPER, stateHelper.saveHelperState());
        outState.putInt(STATE_ITEM, itemid);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(Cursor notes) {

    }
}
