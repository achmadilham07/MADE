package com.example.made.activity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.ArrayMap;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.made.MainView;
import com.example.made.R;
import com.example.made.db.MovieHelper;
import com.example.made.fragment.FavoriteFrag;
import com.example.made.fragment.MovieFrag;
import com.example.made.fragment.TvShowFrag;

public class HomeActivity extends AppCompatActivity implements MainView.LoadMovieCallback, MainView.LoadTvShowCallback {

    private final Fragment fragment1 = new MovieFrag();
    private final Fragment fragment2 = new TvShowFrag();
    private final Fragment fragment3 = new FavoriteFrag();
    private final FragmentManager fm = getSupportFragmentManager();
    private Fragment active = new Fragment();
    private String STATE_HELPER = "helper";
    private String STATE_ITEM = "item";
    private MovieHelper movieHelper;
    private BottomNavigationView navView;
    private ArrayMap<Integer, Fragment> fragmentMap = new ArrayMap<>();
    private FragmentStateHelper stateHelper2;
    private int itemid = 0;

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
            Log.e("DATA_ERROR", String.format("%d - %s", item.getItemId(), active));
            fragmentMap.put(item.getItemId(), active);

            if (navView.getSelectedItemId() != 0) {
                saveCurrentState(itemid);
//                stateHelper.restoreState(active, item.getItemId());
                stateHelper2.restoreState(active, item.getItemId());
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
        navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        stateHelper2 = new FragmentStateHelper(fm);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getResources().getString(R.string.app_name));

//        movieHelper = MovieHelper.getInstance(getApplicationContext());
//        movieHelper.open();

        save_reload_state(savedInstanceState);
    }

    private void save_reload_state(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            addFragment(fragment1);
        } else {
            Bundle helperState = savedInstanceState.getBundle(STATE_HELPER);
            itemid = savedInstanceState.getInt(STATE_ITEM);
            if (helperState != null) {
                stateHelper2.restoreHelperState(helperState);
            }
        }

    }

    private void saveCurrentState(int item) {
        showAllArray();
        Fragment oldFragment = fragmentMap.get(item);
        Log.e("DATA_OLDFrag", String.valueOf(item));
        if (oldFragment != null) {
            stateHelper2.saveState(oldFragment, item);
        }
    }

    private void showAllArray() {
        for (int i = 0; i < fragmentMap.size(); i++) {
            Log.e("SEMUA_DATA", String.format("%d - %s", fragmentMap.keyAt(i), fragmentMap.valueAt(i)));
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
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        saveCurrentState(itemid);
        outState.putBundle(STATE_HELPER, stateHelper2.saveHelperState());
        outState.putInt(STATE_ITEM, itemid);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute(Cursor notes) {

    }
}
