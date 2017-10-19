package com.android.example.movietest_1;


import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainFragment.OnFragmentInteractionListener{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getSupportActionBar();
        if(bar != null) bar.setDisplayHomeAsUpEnabled(false);
        
        getSupportFragmentManager().beginTransaction().add(R.id.mainFragContainer, MainFragment.newInstance()).commit();

        setPosterSize();
    }

    public void setPosterSize() {

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int posterSize;

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            posterSize = metrics.widthPixels / 2;
        } else {
            posterSize = metrics.widthPixels / 3;
        }

        MovieAdapter.setSize(posterSize);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            default:
                Log.e(LOG_TAG, "No menu match");
                return false;
        }
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
