package com.android.example.movietest_1.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by charlotte on 10/18/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "movies";
    private static int DB_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDb = "CREATE TABLE " + Contract.MovieEntry.MOVIE_TABLE_NAME + "(" +
                Contract.MovieEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Contract.MovieEntry.MOVIE_TITLE + " TEXT NOT NULL, " +
                Contract.MovieEntry.MOVIE_DATE + " TEXT NOT NULL, " +
                Contract.MovieEntry.MOVIE_POSTER+ " TEXT NOT NULL, " +
                Contract.MovieEntry.MOVIE_RATING + " INTEGER NOT NULL, " +
                Contract.MovieEntry.MOVIE_SOURCE_ID + " INTEGER NOT NULL, " +
                " UNIQUE (" + Contract.MovieEntry.MOVIE_SOURCE_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(createDb);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE " + Contract.MovieEntry.MOVIE_TABLE_NAME + ";");
        onCreate(db);
    }
}
