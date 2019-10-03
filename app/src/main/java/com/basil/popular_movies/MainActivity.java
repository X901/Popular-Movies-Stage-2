package com.basil.popular_movies;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        if (isOnline()) {
            getPopularMovies();

        }else {
            displayErrorMEssageWhenNoInternet();
        }
    }




    public void getPopularMovies() {
        mainViewModel.getAllPopularMovies().observe(this, new Observer<List<MovieDetielsModel>>() {

            @Override
            public void onChanged(@Nullable List<MovieDetielsModel> listMovie){

                    updateUI(listMovie);

            }

        });
    }

    public void getRatedMovies() {
        mainViewModel.getAllRatedMovies().observe(this, new Observer<List<MovieDetielsModel>>() {

            @Override
            public void onChanged(@Nullable List<MovieDetielsModel> listMovie){
                updateUI(listMovie);
            }

        });
    }

    public void getFavorite(){
        mainViewModel.getAllFavoriteMovies().observe(this, new Observer<List<MovieDetielsModel>>() {

            @Override
            public void onChanged(@Nullable List<MovieDetielsModel> movieModels) {
                updateUI(movieModels);
            }
        });
    }


    public void displayErrorMEssageWhenNoInternet(){
        Context context = getApplicationContext();
        CharSequence text = "Check your internet and try again!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
            switch (item.getItemId()) {
                case R.id.action_sortByPopular:

                    if (isOnline()) {
                        getPopularMovies();

                    }else {
                        displayErrorMEssageWhenNoInternet();
                    }

                    return true;

                case R.id.sortByRated:

                    if (isOnline()) {
                        getRatedMovies();

                    }else {
                        displayErrorMEssageWhenNoInternet();
                    }
                    return true;

                case R.id.sortByFavorite:
                    getFavorite();
                    return true;

        }

        return super.onOptionsItemSelected(item);
    }




    private void updateUI(List<MovieDetielsModel> listMovie) {

        RecyclerView movieRecyclerView = (RecyclerView) findViewById(R.id.movie_recyclerviet_id);

        MovieRecyclerViewAdapter movieRecyclerViewAdapter = new MovieRecyclerViewAdapter(this, listMovie);

        movieRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));


        movieRecyclerView.setAdapter(movieRecyclerViewAdapter);


        movieRecyclerViewAdapter.notifyDataSetChanged();
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }



}
