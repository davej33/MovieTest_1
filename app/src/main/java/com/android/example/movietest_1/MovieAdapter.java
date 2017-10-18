package com.android.example.movietest_1;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

/**
 * Created by charlotte on 10/18/17.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private static Cursor sCursor;
    private static Context sContext;
    private static int sItemWidth;
    private static int sItemHeight;

    public MovieAdapter(Context context) {
        sContext = context;
    }

    public static void setSize(int size) {
        sItemWidth = size;
        sItemHeight = (int) (size * 1.5);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {

        if(sCursor == null) {
            return 0;
        } else {
            return sCursor.getCount();
        }
    }

    public void swapCursor(Cursor cursor) {
        sCursor = cursor;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
