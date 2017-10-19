package com.android.example.movietest_1.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.sql.SQLException;

/**
 * Created by charlotte on 10/18/17.
 */

public final class DbContentProvider extends ContentProvider {

    private static final String LOG_TAG = DbContentProvider.class.getSimpleName();
    private static final int TABLE_CODE = 100;
    private static final int ITEM_CODE = 101;
    private static DbHelper sDbHelper;

    private UriMatcher mUriMatcher = getUriMatcher();

    private UriMatcher getUriMatcher() {
        UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH, TABLE_CODE);
        matcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH + "/#", ITEM_CODE);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        sDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        SQLiteDatabase db = sDbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            switch (mUriMatcher.match(uri)) {
                case TABLE_CODE:
                    cursor = db.query(Contract.MovieEntry.MOVIE_TABLE_NAME, strings, s, strings1, null, null, s1);
                    break;
                case ITEM_CODE:
                    cursor = db.query(Contract.MovieEntry.MOVIE_TABLE_NAME, strings, s + "=?", strings1, null, null, s1);
                    break;
            }

            if (cursor == null) {
                Log.e(LOG_TAG, "Error querying DB: Cursor null");
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error querying DB: Try/Catch error");
        }
        return cursor;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        SQLiteDatabase db = sDbHelper.getWritableDatabase();
        int count = 0;
        try {
            long check = -1;
            db.beginTransaction();
            for (ContentValues cv : values) {
                check = db.insert(Contract.MovieEntry.MOVIE_TABLE_NAME, null, cv);
                Log.i(LOG_TAG, "Bulk insert check: " + check);
                if (check < 1) {
                    Log.e(LOG_TAG, "Bulk insert error: insert fail");
                    break;
                }
                count++;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(LOG_TAG, "Bulk insert error: Try/Catch error" + e);
        } finally {
            db.endTransaction();
        }
        return count;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        SQLiteDatabase db = sDbHelper.getWritableDatabase();
        int count = 0;
//        String[] movieId = {uri.getLastPathSegment()};

        try{
            count = db.update(Contract.MovieEntry.MOVIE_TABLE_NAME, contentValues, s , strings);
            if (count < 1) {
                Log.e(LOG_TAG, "Update error: update fail");
            }
        } catch (Exception e){
            Log.e(LOG_TAG, "Update error: Try/Catch error");
        }

        return count;
    }
}
