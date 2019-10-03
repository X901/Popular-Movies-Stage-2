package com.basil.popular_movies;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TrailersRecyclerViewAdapter extends RecyclerView.Adapter<TrailersRecyclerViewAdapter.TrailersViewHolder>{

    private List<TrailerVideosModel.Result> mTrailersData;
    private Context mContect;

    public TrailersRecyclerViewAdapter(Context mContect, List<TrailerVideosModel.Result> mTrailersData) {
        this.mContect = mContect;
        this.mTrailersData = mTrailersData;
    }


    @Override
    public TrailersViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContect);
        int layoutIdForListItem = R.layout.trailers_recyclerview_row;


        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new TrailersRecyclerViewAdapter.TrailersViewHolder(view);

    }

    @Override
    public void onBindViewHolder( TrailersViewHolder holder, final int position) {

    holder.tv_trailer_title.setText(mTrailersData.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String trailerYoutubeId = mTrailersData.get(position).getKey();

                trailerYoutubeId(trailerYoutubeId);

            }
        });

    }



        @Override
    public int getItemCount() {
        return mTrailersData == null ? 0 : mTrailersData.size();

    }

    public static class TrailersViewHolder extends RecyclerView.ViewHolder {

        TextView tv_trailer_title;

        public TrailersViewHolder( View itemView) {
            super(itemView);

            tv_trailer_title = (TextView) itemView.findViewById(R.id.trailertextView);

        }

        }

    private void trailerYoutubeId(String youtubeVideoId){

        Context activity = mContect.getApplicationContext();

        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeVideoId));
            activity.startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+youtubeVideoId));
            activity.startActivity(intent);
        }
    }

}

