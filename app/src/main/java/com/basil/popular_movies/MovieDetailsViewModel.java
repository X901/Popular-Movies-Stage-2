package com.basil.popular_movies;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.basil.popular_movies.ThemoviedbAPI.getService;

public class MovieDetailsViewModel extends AndroidViewModel {

    private MutableLiveData<MovieDetielsModel> objectMovieDetielsMutableLiveData = new MutableLiveData<>();

    private ArrayList<TrailerVideosModel.Result> listTrailerVideos = new ArrayList<>();
    private MutableLiveData<List<TrailerVideosModel.Result>> listTrailerVideosMutableLiveData = new MutableLiveData<>();

    private ArrayList<ReviewsModel.Result> listReviews = new ArrayList<>();
    private MutableLiveData<List<ReviewsModel.Result>> listReviewsMutableLiveData = new MutableLiveData<>();

    private Application application;
    FavoriteDatabase favoriteDatabase;


    public MovieDetailsViewModel(@NonNull Application application) {
        super(application);
        this.application = application;
        this.favoriteDatabase = FavoriteDatabase.getfavoriteDatabase(application);

    }

    private MutableLiveData<MovieDetielsModel> getMovieDetielsMutableLiveData(int id) {

        Call<MovieDetielsModel> movieDetiels = null;

        movieDetiels = getService().getMovieDetails(id);


        movieDetiels.enqueue(new Callback<MovieDetielsModel>() {
            @Override
            public void onResponse(Call<MovieDetielsModel> call, Response<MovieDetielsModel> response) {
                MovieDetielsModel movieDetiels = response.body();


                objectMovieDetielsMutableLiveData.setValue(movieDetiels);
            }

            @Override
            public void onFailure(Call<MovieDetielsModel> call, Throwable t) {
                Context context = application.getApplicationContext();

                Toast.makeText(context, "Fail to get the data !", Toast.LENGTH_LONG).show();

            }
        });

        return objectMovieDetielsMutableLiveData;

    }


    public LiveData<MovieDetielsModel> getMovieDetiels(int id) {
        return this.getMovieDetielsMutableLiveData(id);
    }



    private  MutableLiveData<List<TrailerVideosModel.Result>> getTrailerVideosMutableLiveData(int id){

        Call<TrailerVideosModel> TrailerVideosList = null;

        TrailerVideosList = getService().getTrailerVideos(id);

        TrailerVideosList.enqueue(new Callback<TrailerVideosModel>() {
            @Override
            public void onResponse(Call<TrailerVideosModel> call, Response<TrailerVideosModel> response) {
                TrailerVideosModel list = response.body();

                if (list.getResults() != null) {
                    listTrailerVideos = (ArrayList<TrailerVideosModel.Result>) list.getResults();

                    listTrailerVideosMutableLiveData.setValue(listTrailerVideos);

                }
            }

            @Override
            public void onFailure(Call<TrailerVideosModel> call, Throwable t) {
                Context context = application.getApplicationContext();

                Toast.makeText(context,"Fail to get the data !",Toast.LENGTH_LONG).show();

            }
        });

        return listTrailerVideosMutableLiveData;

    }

    public LiveData<List<TrailerVideosModel.Result>> getTrailerVideos(int id) {
        return this.getTrailerVideosMutableLiveData(id);
    }



    private  MutableLiveData<List<ReviewsModel.Result>> getReviewsMutableLiveData(int id){

        Call<ReviewsModel> ReviewsList = null;

        ReviewsList = getService().getMovieReviews(id);

        ReviewsList.enqueue(new Callback<ReviewsModel>() {
            @Override
            public void onResponse(Call<ReviewsModel> call, Response<ReviewsModel> response) {
                ReviewsModel list = response.body();

                if (list.getResults() != null) {
                    listReviews = (ArrayList<ReviewsModel.Result>) list.getResults();

                    listReviewsMutableLiveData.setValue(listReviews);

                }
            }

            @Override
            public void onFailure(Call<ReviewsModel> call, Throwable t) {
                Context context = application.getApplicationContext();

                Toast.makeText(context,"Fail to get the data !",Toast.LENGTH_LONG).show();

            }
        });

        return listReviewsMutableLiveData;

    }

    public LiveData<List<ReviewsModel.Result>> getReviews(int id) {
        return this.getReviewsMutableLiveData(id);
    }

}
