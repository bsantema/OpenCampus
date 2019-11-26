package com.ss4.opencampus.mainViews.reviewMessage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.buildings.Building;

import java.util.List;

/**
 * @author Axel Zumwalt
 *
 * Formats ReviewMessage data for Recycler View layout
 **/

public class ReviewMessageAdapter extends RecyclerView.Adapter<ReviewMessageAdapter.ViewHolder> {

    private Context context;
    private List<ReviewMessage> list;

    /**
     * ReviewMessageAdapter constructor, passes in context and the list of messages to display
     *
     * @param context Context given
     * @param list List of ReviewMessage Objects to put in adapter
     */
    public ReviewMessageAdapter(Context context, List<ReviewMessage> list) {
        this.context = context;
        this.list = list;
    }
    
    /**
     * Sets the xml properties for the list of messages
     *
     * @param parent Group the view type is in
     * @param viewType Type of view
     * @return a new ViewHolder to hold the list
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.main_fragment_review_message, parent, false);
        return new ViewHolder(v);
    }
    
    /**
     * Binds the correct message from the list to the adapter
     *
     * @param holder ViewHolder to bind
     * @param position position of message to find
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ReviewMessage message = list.get(position);

        holder.textName.setText("Someone commented on your USpot: " + message.getUSpotName());
        holder.itemView.setTag(message);
    }
    
    /**
     * Returns the number of messages in the adapter
     *
     * @return number of messages in the list
     */
    @Override
        public int getItemCount() {
            return list.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textName;

            ViewHolder(View itemView) {
                super(itemView);

                textName = itemView.findViewById(R.id.review_message_name);
            }
        }
    }
