package com.basil.popular_movies;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MovieDetielsModel.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDAO favoriteDAO();

    private static FavoriteDatabase favoriteDatabase;

    public static FavoriteDatabase getfavoriteDatabase (Context context) {
        if (favoriteDatabase == null){
            favoriteDatabase = Room.databaseBuilder(context, FavoriteDatabase.class, "Favorite_Movie.db").fallbackToDestructiveMigration().build();
        }

        return favoriteDatabase;
    }
}
