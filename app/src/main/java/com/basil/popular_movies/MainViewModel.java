package com.basil.popular_movies;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basil.popular_movies.ThemoviedbAPI.getService;

public class MainViewModel extends AndroidViewModel {


    private MutableLiveData<List<MovieDetielsModel>> popularMovies = new MutableLiveData<>();
    private MutableLiveData<List<MovieDetielsModel>> topRatedMovies = new MutableLiveData<>();
    private LiveData<List<MovieDetielsModel>> favoriteMovies = new MutableLiveData<>();

    private Application application;

    FavoriteDatabase favoriteDatabase;

    public MainViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.favoriteDatabase = FavoriteDatabase.getfavoriteDatabase(application);

    }


    private  MutableLiveData<List<MovieDetielsModel>> getMutableLiveData(String type , boolean fav){

        if (fav) {

            getFavoritesMovies();

        }else{


        if (type.equals("Popular")) {
            Call<ApiResponse<MovieDetielsModel>>  popularmMvieList = getService().getMoviePopular();


            popularmMvieList.enqueue(new Callback<ApiResponse<MovieDetielsModel>>() {


                @Override
                public void onResponse(Call<ApiResponse<MovieDetielsModel>> call, Response<ApiResponse<MovieDetielsModel>> response) {
                    List<MovieDetielsModel> list = response.body().results;

                    if (list != null) {

                        popularMovies.setValue(list);
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<MovieDetielsModel>> call, Throwable t) {
                    Context context = application.getApplicationContext();

                    Toast.makeText(context, "Fail to get the data !", Toast.LENGTH_LONG).show();

                }
            });
            return popularMovies;

        } else if (type.equals("Rated")) {
            Call<ApiResponse<MovieDetielsModel>>  topRatedMvieList = getService().getMovieTopRated();

            topRatedMvieList.enqueue(new Callback<ApiResponse<MovieDetielsModel>>() {


                @Override
                public void onResponse(Call<ApiResponse<MovieDetielsModel>> call, Response<ApiResponse<MovieDetielsModel>> response) {
                    List<MovieDetielsModel> list = response.body().results;

                    if (list != null) {

                        topRatedMovies.setValue(list);

                    }
                }

                @Override
                public void onFailure(Call<ApiResponse<MovieDetielsModel>> call, Throwable t) {
                    Context context = application.getApplicationContext();

                    Toast.makeText(context, "Fail to get the data !", Toast.LENGTH_LONG).show();

                }
            });

            return topRatedMovies;

        }



    }
        return popularMovies;

    }

    public LiveData<List<MovieDetielsModel>> getAllPopularMovies() {

            return popularMovies = this.getMutableLiveData("Popular",false);

    }

    public LiveData<List<MovieDetielsModel>> getAllRatedMovies() {

        return this.getMutableLiveData("Rated",false);
    }

    public LiveData<List<MovieDetielsModel>> getAllFavoriteMovies()
    {
        getFavoritesMovies();
        return favoriteMovies;
    }

    private void getFavoritesMovies()  {
            favoriteMovies = favoriteDatabase.favoriteDAO().getFavoriteMovies();

    }
}
