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

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ReviewsViewHolder> {

    private List<ReviewsModel.Result> mReviewsData;
    private Context mContect;

    public ReviewsRecyclerViewAdapter(Context mContect, List<ReviewsModel.Result> mTrailersData) {
        this.mContect = mContect;
        this.mReviewsData = mTrailersData;
    }


    @Override
    public ReviewsRecyclerViewAdapter.ReviewsViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContect);
        int layoutIdForListItem = R.layout.reviews_recyclerview_row;


        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);

        return new ReviewsRecyclerViewAdapter.ReviewsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ReviewsRecyclerViewAdapter.ReviewsViewHolder holder, final int position) {

        holder.tv_review_text.setText(mReviewsData.get(position).getContent());
        holder.tv_review_author_name.setText(mReviewsData.get(position).getAuthor() + ":");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

    }



    @Override
    public int getItemCount() {
        return mReviewsData == null ? 0 : mReviewsData.size();

    }

    public static class ReviewsViewHolder extends RecyclerView.ViewHolder {

        TextView tv_review_text;
        TextView tv_review_author_name;

        public ReviewsViewHolder( View itemView) {
            super(itemView);

            tv_review_text = (TextView) itemView.findViewById(R.id.reviewsTextViewId);
            tv_review_author_name = (TextView) itemView.findViewById(R.id.AuthorTextView);
        }

    }


}
