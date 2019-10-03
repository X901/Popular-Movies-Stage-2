package com.basil.popular_movies;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


@Dao
public interface FavoriteDAO {
    @Insert
    public void insertMovie(MovieDetielsModel ... movie);

    @Query("DELETE FROM MovieDetielsModel WHERE id = :id")
    public void deleteMovie(int id);

    @Delete
    void delete(MovieDetielsModel movie);

    @Query("SELECT * FROM MovieDetielsModel")
    public LiveData<List<MovieDetielsModel>> getFavoriteMovies();

    @Query("SELECT * FROM movieDetielsModel WHERE id = :id ")
    public MovieDetielsModel getFavoriteMovieWithId(Integer id);





}
