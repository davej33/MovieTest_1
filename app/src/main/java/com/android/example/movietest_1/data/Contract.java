package com.android.example.movietest_1.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by charlotte on 10/18/17.
 */

public final class Contract {

    public static final String CONTENT_AUTHORITY = "com.android.example.movietest_1";
    public static final String PATH = "movie";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static class MovieEntry implements BaseColumns{

        public static final Uri MOVIE_URI = Uri.parse(BASE_CONTENT_URI.toString()).buildUpon()
                .appendPath(PATH)
                .build();
        public static final String MOVIE_TABLE_NAME = "movie";
        public static final String _ID = BaseColumns._ID;
        public static final String MOVIE_TITLE = "title";
        public static final String MOVIE_DATE = "date";
        public static final String MOVIE_POSTER = "poster";
        public static final String MOVIE_RATING = "rating";
        public static final String MOVIE_POPULARITY = "popularity";
        public static final String MOVIE_SOURCE_ID = "source_id";
    }
}
