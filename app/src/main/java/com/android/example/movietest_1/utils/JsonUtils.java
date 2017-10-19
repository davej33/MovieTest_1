package com.android.example.movietest_1.utils;

import android.content.ContentValues;
import android.util.Log;

import com.android.example.movietest_1.data.Contract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by charlotte on 10/18/17.
 */

public final class JsonUtils {

    public static ContentValues[] parseData(String response) throws JSONException {

        final String TITLE = "title";
        final String DATE = "release_date";
        final String POPULARITY = "popularity";
        final String RATING = "vote_average";
        final String TMDB_ID = "id";
        final String POSTER_PATH = "poster_path";

        JSONObject root = new JSONObject(response);
        JSONArray movieArray = root.getJSONArray("results");
        ContentValues[] cvMovies = new ContentValues[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            JSONObject element = movieArray.getJSONObject(i);

            String title = element.getString(TITLE);
            String date = element.getString(DATE);
            int popularity = element.getInt(POPULARITY);
            double rating = element.getDouble(RATING);
            int id = element.getInt(TMDB_ID);
            String poster_path = element.getString(POSTER_PATH);
            Log.i("JsonUtils", "Poster Path: " + poster_path);

            ContentValues cv = new ContentValues();
            cv.put(Contract.MovieEntry.MOVIE_TITLE,title);
            cv.put(Contract.MovieEntry.MOVIE_DATE,date);
            cv.put(Contract.MovieEntry.MOVIE_POPULARITY, popularity);
            cv.put(Contract.MovieEntry.MOVIE_RATING, rating);
            cv.put(Contract.MovieEntry.MOVIE_POSTER, poster_path);
            cv.put(Contract.MovieEntry.MOVIE_SOURCE_ID, id);

            cvMovies[i] = cv;
        }


        return cvMovies;
    }
}
