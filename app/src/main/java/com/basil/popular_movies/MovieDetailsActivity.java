package com.basil.popular_movies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import static com.basil.popular_movies.MovieRecyclerViewAdapter.urlPostPathToUrl;

public class MovieDetailsActivity extends AppCompatActivity {

    MovieDetielsModel retrievedMoviefromIntent;

    private MovieDetailsViewModel movieDetailsViewModel;
    FavoriteMoviesWorker worker = new FavoriteMoviesWorker(this);
    ImageView star;
    Boolean fromClick=false;

   // Integer movieId = null;


    TextView tv_movie_title;
    TextView tv_movie_release_date;
    TextView tv_movie_vote_average;
    TextView tv_movie_overview;
    ImageView tv_movie_poster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        Context context = getApplicationContext();

        Intent movieIntent = getIntent();

      //   movieId = movieIntent.getExtras().getInt(movieIdKey);

        retrievedMoviefromIntent = getIntent().getParcelableExtra(context.getString(R.string.movie));

        movieDetailsViewModel = ViewModelProviders.of(this).get(MovieDetailsViewModel.class);


        star = findViewById(R.id.star);


            getMovieDetiels(retrievedMoviefromIntent.getId());
            getTrailers(retrievedMoviefromIntent.getId());
            getReviewsData(retrievedMoviefromIntent.getId());


        new SingleMovieAsyncTask().execute(retrievedMoviefromIntent);



        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new SingleMovieAsyncTask().execute(retrievedMoviefromIntent);
                fromClick=true;
            }
        });
    }

    private class SingleMovieAsyncTask extends AsyncTask<MovieDetielsModel, Void, MovieDetielsModel> {

        @Override
        protected MovieDetielsModel doInBackground(MovieDetielsModel... movie) {

            FavoriteDatabase favoriteDatabase = FavoriteDatabase.getfavoriteDatabase(MovieDetailsActivity.this);
            MovieDetielsModel singleMovie = favoriteDatabase.favoriteDAO().getFavoriteMovieWithId(movie[0].getId());


            return singleMovie;
        }

        @Override
        protected void onPostExecute(MovieDetielsModel list) {
            super.onPostExecute(list);
            if(fromClick){
                if (list != null) {

                    unfavoriteMovie(retrievedMoviefromIntent);
                    star.setImageResource(R.drawable.unfav_star);
                } else {
                    markAsFavorite(retrievedMoviefromIntent);
                    star.setImageResource(R.drawable.fav_star);
                }
            }
            else {
                if (list != null) {
                    star.setImageResource(R.drawable.fav_star);
                } else {
                    star.setImageResource(R.drawable.unfav_star);
                }
            }
        }
    }

    private void markAsFavorite(MovieDetielsModel movie) {
        worker.insertFavMovie(movie);
    }

    private void unfavoriteMovie(MovieDetielsModel movie) {
        worker.deleteFavMovie(movie);
    }

    public void getMovieDetiels(int id) {

        movieDetailsViewModel.getMovieDetiels(id).observe(this, new Observer<MovieDetielsModel>() {


            @Override
            public void onChanged(MovieDetielsModel movieDetielsModel) {
                updateMovieDetielsUI(movieDetielsModel);
                retrievedMoviefromIntent = movieDetielsModel;
            }
        });
    }

    private void updateMovieDetielsUI(MovieDetielsModel movieDetielsObject) {

        tv_movie_title = (TextView) findViewById(R.id.movie_title_id);
        tv_movie_release_date = (TextView) findViewById(R.id.release_date_id);
        tv_movie_vote_average = (TextView) findViewById(R.id.vote_average_id);
        tv_movie_overview = (TextView) findViewById(R.id.overview_id);
        tv_movie_poster = (ImageView) findViewById(R.id.poster_image_id);


        tv_movie_title.setText(movieDetielsObject.getTitle());
        tv_movie_release_date.setText(movieDetielsObject.getReleaseDate());
        tv_movie_vote_average.setText(Double.toString(movieDetielsObject.getVoteAverage()));
        tv_movie_overview.setText(movieDetielsObject.getOverview());

        String urlImage = urlPostPathToUrl(movieDetielsObject.getPosterPath(),"w342");
        Picasso.get().load(urlImage).into(tv_movie_poster);


    }

    public void getTrailers(int id) {
        movieDetailsViewModel.getTrailerVideos(id).observe(this, new Observer<List<TrailerVideosModel.Result>>() {

            @Override
            public void onChanged(List<TrailerVideosModel.Result> listTrailerVideos) {
                updateTrailersRecyclerViewUI(listTrailerVideos);
            }

        });
    }

    private void updateTrailersRecyclerViewUI(List<TrailerVideosModel.Result> listTrailersVideos) {

        RecyclerView tv_trailersRecyclerView = (RecyclerView) findViewById(R.id.trailersRecyclerView);

        TrailersRecyclerViewAdapter trailersRecyclerViewAdapter = new TrailersRecyclerViewAdapter(this, listTrailersVideos);

        tv_trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv_trailersRecyclerView.setAdapter(trailersRecyclerViewAdapter);
    }


    private void getReviewsData(int id){

        movieDetailsViewModel.getReviews(id).observe(this, new Observer<List<ReviewsModel.Result>>() {

            @Override
            public void onChanged(List<ReviewsModel.Result> listReviews) {
                updateReviewsRecyclerViewUI(listReviews);
            }

        });
    }

    private void updateReviewsRecyclerViewUI(List<ReviewsModel.Result> listReviewsModel) {

        RecyclerView tv_reviewsRecyclerView = (RecyclerView) findViewById(R.id.reviewsRecyclerView);

        ReviewsRecyclerViewAdapter reviewsRecyclerViewAdapter = new ReviewsRecyclerViewAdapter(this, listReviewsModel);

        tv_reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        tv_reviewsRecyclerView.setAdapter(reviewsRecyclerViewAdapter);
    }



    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
