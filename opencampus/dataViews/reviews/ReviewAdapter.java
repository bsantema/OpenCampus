package com.ss4.opencampus.dataViews.reviews;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ss4.opencampus.R;

import java.util.List;

/**
 * @author Morgan Smith
 * Formats Review object data for Recycler View layout
 **/

public class ReviewAdapter extends RecyclerView.Adapter<com.ss4.opencampus.dataViews.reviews.ReviewAdapter.ViewHolder> {

    private Context context;
    private List<Review> list;

    /**
     * Constructs a Review with a given Context and List of Review objects
     *
     * @param context given context
     * @param list List of Review objects to use
     */
    public ReviewAdapter(Context context, List<Review> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * Sets the xml properties for the list of Reviews
     * @param parent Group the view type is in
     * @param viewType Type of view
     * @return a new ViewHolder to hold the list
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.data_fragment_reviews, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Binds the correct Building from the ReviewList to the adapter
     * @param holder ViewHolder to bind
     * @param position position of Review to find
     */
    @Override
    public void onBindViewHolder(com.ss4.opencampus.dataViews.reviews.ReviewAdapter.ViewHolder holder, int position) {
        Review review = list.get(position);

        holder.textDetails.setText(review.getReviewDetails());
    }

    /**
     * Returns the list of the Review List
     * @return Number of Reviews in the List
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textDetails;

        /**
         * Sets xml properties for each item in the Review list to be displayed in the adapter
         * @param itemView View to get information from
         */
        ViewHolder(View itemView) {
            super(itemView);

            textDetails = itemView.findViewById(R.id.review_list_details);
        }
    }
}
