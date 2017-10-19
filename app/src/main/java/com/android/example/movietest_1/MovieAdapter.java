package com.android.example.movietest_1;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ToggleButton;

import com.android.example.movietest_1.data.Contract;
import com.android.example.movietest_1.utils.FavoriteUtils;
import com.android.example.movietest_1.utils.SyncUtils;
import com.squareup.picasso.Picasso;

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
        View view = LayoutInflater.from(sContext).inflate(R.layout.movie_item_layout, parent, false);
        view.getLayoutParams().height = sItemHeight;
        view.getLayoutParams().width = sItemWidth;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        sCursor.moveToPosition(position);

        String poster_path_id = sCursor.getString(sCursor.getColumnIndex(Contract.MovieEntry.MOVIE_POSTER));
        String full_poster_path = sContext.getResources().getString(R.string.image_base_url) + poster_path_id;
        Picasso.with(sContext)
                .load(full_poster_path)
                .resize(sItemWidth, sItemHeight)
                .centerCrop()
                .into((ImageView) holder.itemView.findViewById(R.id.poster_image_container));

        holder.favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.favButton.isChecked()){
                    sCursor.moveToPosition(holder.getAdapterPosition());
                    FavoriteUtils.addFavorites(sCursor.getString(sCursor.getColumnIndex(Contract.MovieEntry.MOVIE_TITLE)), sContext);
                } else {
                    try {
                        FavoriteUtils.removeFavorites(sCursor.getString(sCursor.getColumnIndex(Contract.MovieEntry.MOVIE_TITLE)), sContext);
                    } catch (Exception e) {
                        Log.e("Adapter","FavRemove Error: " + e);

                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {

        if (sCursor == null) {
            return 0;
        } else {
            return sCursor.getCount();
        }
    }

    public void swapCursor(Cursor cursor) {
        sCursor = cursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView view;
        ToggleButton favButton;

        public ViewHolder(View itemView) {
            super(itemView);

            view = itemView.findViewById(R.id.poster_image_container);
            favButton = itemView.findViewById(R.id.fav_button);
        }
    }
}
