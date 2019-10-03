package com.basil.popular_movies;

import android.content.Context;
import android.os.AsyncTask;

public class FavoriteMoviesWorker {

    private boolean setAsFavorite ;
    private FavoriteDatabase favoriteDatabase;

    public FavoriteMoviesWorker (Context context) {
        favoriteDatabase = FavoriteDatabase.getfavoriteDatabase(context);
    }

    public void insertFavMovie (MovieDetielsModel movie){
        setAsFavorite = true;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }

    public void deleteFavMovie (MovieDetielsModel movie){
        setAsFavorite = false;
        new FavoriteMoviesAsyncTask ().execute(movie);
    }


    private class FavoriteMoviesAsyncTask extends AsyncTask<MovieDetielsModel, Void, Void> {

        @Override
        protected Void doInBackground(MovieDetielsModel... movie) {
            if (setAsFavorite){
                favoriteDatabase.favoriteDAO().insertMovie(movie[0]);
            } else {
                favoriteDatabase.favoriteDAO().delete(movie[0]);

            }
            return null;
        }
    }
}
