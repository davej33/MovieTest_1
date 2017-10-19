package com.android.example.movietest_1.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.android.example.movietest_1.data.Contract;
import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import java.net.URL;
import java.sql.SQLException;

/**
 * Created by charlotte on 10/18/17.
 */

public final class SyncUtils {

    private static final String LOG_TAG = JsonUtils.class.getSimpleName();
    private static boolean sIsInit = false;


    public static boolean initialized(final Context context) {
        if (sIsInit) return true;
        sIsInit = true;


        Thread check = new Thread(new Runnable() {
            Cursor cursor;

            @Override
            public void run() {
                cursor = context.getContentResolver().query(Contract.MovieEntry.MOVIE_URI, new String[]{Contract.MovieEntry._ID}, null, null, null);
                if (cursor == null || cursor.getCount() < 1) {
                    sendDataRequest(context);
                } else {
                    cursor.close();
                }
            }
        });
        check.run();
        return false;
    }

    public static void sendDataRequest(final Context context) {
        String url = NetworkUtils.buildURL(context);
        StringRequest stringRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ContentValues[] values;
                try {
                    values = JsonUtils.parseData(response);
                    Log.i(LOG_TAG, "Response: " + values.length);
                    insertIntoDb(values, context);

                } catch (Exception e) {
                    Log.e(LOG_TAG, "JSON parse error: " + e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkVolleyError(error, context);
            }
        });
        NetworkUtils.AddToRequestQueue(context, stringRequest);
    }

    private static void insertIntoDb(ContentValues[] values, Context context) {
        int check = -1;
        try {
            check = context.getContentResolver().bulkInsert(Contract.MovieEntry.MOVIE_URI, values);
            Log.i(LOG_TAG, "Init bulk insert check: " + check);
        } catch (Exception e) {
            Log.e(LOG_TAG, "Init bulk insert error: " + e);
        }
    }

    private static void checkVolleyError(VolleyError error, Context context) {
        if (error instanceof TimeoutError || error instanceof NoConnectionError) {
            Toast.makeText(context, "No Network Connection", Toast.LENGTH_SHORT).show();
        } else if (error instanceof AuthFailureError) {
            Toast.makeText(context, "Authentication Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ServerError) {
            Toast.makeText(context, "Server Side Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof NetworkError) {
            Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show();
        } else if (error instanceof ParseError) {
            Toast.makeText(context, "Parse Error!", Toast.LENGTH_SHORT).show();
        }
    }
}
