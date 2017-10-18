package com.android.example.movietest_1.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import com.android.example.movietest_1.BuildConfig;
import com.android.example.movietest_1.R;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by charlotte on 10/18/17.
 */

public final class NetworkUtils {

    private static RequestQueue sRequestQueue;

    public static void AddToRequestQueue(Context context, StringRequest stringRequest) {
        if(sRequestQueue == null) sRequestQueue = Volley.newRequestQueue(context);
        sRequestQueue.add(stringRequest);
    }

    public static String buildURL(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String sort = preferences.getString(context.getString(R.string.pref_sort_key), context.getString(R.string.pref_sort_default));

        Uri uriBuild = Uri.parse(context.getResources().getString(R.string.query_base_url) + sort + "?").buildUpon()
                .appendQueryParameter(context.getResources().getString(R.string.api_code_key),
                        BuildConfig.MovieApiKey)
                .build();

        return uriBuild.toString();
    }
}
