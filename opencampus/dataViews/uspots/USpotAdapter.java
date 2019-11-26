package com.ss4.opencampus.dataViews.uspots;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ss4.opencampus.R;

import java.util.List;

/**
 * @author Morgan Smith
 * Formats USpot object data for Recycler View layout
 **/

public class USpotAdapter extends RecyclerView.Adapter<USpotAdapter.ViewHolder> {

    private Context context;
    private List<USpot> list;
    
    /**
     * Constructs a USpot with a given Context and List of USpot objects
     * @param context given context
     * @param list List of USpot objects to use
     */
    public USpotAdapter(Context context, List<USpot> list) {
        this.context = context;
        this.list = list;
    }
    
    /**
     * Sets the xml properties for the list of USpots
     * @param parent Group the view type is in
     * @param viewType Type of view
     * @return a new ViewHolder to hold the list
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.data_fragment_uspots, parent, false);
        return new ViewHolder(v);
    }
    
    /**
     * Binds the correct Building from the USpotList to the adapter
     * @param holder ViewHolder to bind
     * @param position position of USpot to find
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        USpot uSpot = list.get(position);

        holder.textName.setText(uSpot.getUsName());

        //Clean USpot List
      /*  holder.textRating.setText(uSpot.getRatingString());
        holder.textLat.setText(uSpot.getLatString());
        holder.textLong.setText(uSpot.getLongString());*/
        holder.textCategory.setText(uSpot.getUsCategory());
        holder.imagePicBytes.setImageBitmap(uSpot.setBitmap());
        holder.itemView.setTag(uSpot);
    }
    
    /**
     * Returns the list of the USpot List
     * @return Number of USpots in the List
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName, /* textRating, textLat, textLong,*/ textCategory;
        ImageView imagePicBytes;
    
        /**
         * Sets xml properties for each item in the USpot list to be displayed in the adapter
         * @param itemView View to get information from
         */
        ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.uspot_list_name);

            // Clean up USpot List
            /*textRating = itemView.findViewById(R.id.uspot_list_rating);
            textLat = itemView.findViewById(R.id.uspot_list_latitude);
            textLong = itemView.findViewById(R.id.uspot_list_longitude);*/
            textCategory = itemView.findViewById(R.id.uspot_list_category);
            imagePicBytes = itemView.findViewById(R.id.uspot_list_image);
        }
    }
}