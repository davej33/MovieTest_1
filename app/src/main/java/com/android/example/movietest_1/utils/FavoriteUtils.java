package com.android.example.movietest_1.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.example.movietest_1.R;
import com.android.example.movietest_1.data.Contract;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.ListIterator;
import java.util.Set;

/**
 * Created by charlotte on 10/19/17.
 */

public final class FavoriteUtils {

    private static SharedPreferences sPrefs;
    private static SharedPreferences.Editor sEditor;
    private static Set<String> sFavList;

    public static void addFavorites(String title, Context context){

        // add favorite to Set in SharedPreferences
        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        sEditor = sPrefs.edit();
        sFavList = sPrefs.getStringSet(context.getResources().getString(R.string.pref_fav_key), null);
        if(sFavList == null) sFavList = new HashSet<>();
        sFavList.add(title);
        sEditor.putStringSet(context.getResources().getString(R.string.pref_fav_key),sFavList);
        sEditor.apply();

        // update db to save movie as favorite
        ContentValues cv = new ContentValues();
        cv.put(Contract.MovieEntry.MOVIE_FAVORITES, 1);
        int check = context.getContentResolver().update(Contract.MovieEntry.MOVIE_URI,cv, Contract.MovieEntry.MOVIE_TITLE + "=?", new String[]{title});
        Log.w("FavUtils", "Update check add: " + check);


        printFav();
    }

    public static void removeFavorites(String title, Context context) throws Exception {
        sPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        sEditor = sPrefs.edit();
        sFavList = sPrefs.getStringSet(context.getResources().getString(R.string.pref_fav_key), null);
        if(sFavList == null) throw new Exception("No favorites saved");
        sFavList.remove(title);
        sEditor.putStringSet(context.getResources().getString(R.string.pref_fav_key),sFavList);
        sEditor.apply();

        // update db to save movie as favorite
        ContentValues cv = new ContentValues();
        cv.put(Contract.MovieEntry.MOVIE_FAVORITES, 0);
        int check = context.getContentResolver().update(Contract.MovieEntry.MOVIE_URI,cv, Contract.MovieEntry.MOVIE_TITLE + "=?", new String[]{title});
        Log.w("FavUtils", "Update check remove: " + check);
        printFav();
    }

    public static void clearFavorites(Context context){

    }

    private static void printFav(){
        Log.d("t","********************** Begin Fav List *******************");
        if(sFavList == null) Log.w("FavUtils", "Favorite NULL ");
        for (String s: sFavList) {
            Log.w("FavUtils", "Favorite: " + s);
        }
        Log.d("t","********************** End Fav List *******************");
    }
}
