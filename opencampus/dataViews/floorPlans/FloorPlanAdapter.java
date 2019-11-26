package com.ss4.opencampus.dataViews.floorPlans;

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
 * Formats FloorPlan object data for Recycler View layout
 **/

public class FloorPlanAdapter extends RecyclerView.Adapter<com.ss4.opencampus.dataViews.floorPlans.FloorPlanAdapter.ViewHolder> {

    private Context context;
    private List<FloorPlan> list;

    /**
     * Constructs a Floor Plan with a given Context and List of Floor Plan objects
     *
     * @param context given context
     * @param list List of Floor Plan objects to use
     */
    public FloorPlanAdapter(Context context, List<FloorPlan> list) {
        this.context = context;
        this.list = list;
    }

    /**
     * Sets the xml properties for the list of FloorPlans
     * @param parent Group the view type is in
     * @param viewType Type of view
     * @return a new ViewHolder to hold the list
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.data_fragment_floor_plan, parent, false);
        return new ViewHolder(v);
    }

    /**
     * Binds the correct Building from the FloorPlanList to the adapter
     * @param holder ViewHolder to bind
     * @param position position of FloorPlan to find
     */
    @Override
    public void onBindViewHolder(com.ss4.opencampus.dataViews.floorPlans.FloorPlanAdapter.ViewHolder holder, int position) {
        FloorPlan floorPlan = list.get(position);

        holder.textName.setText(floorPlan.getFloorPlanName());
        holder.picBytes.setImageBitmap(floorPlan.setBitmap());
        holder.itemView.setTag(floorPlan);
    }

    /**
     * Returns the list of the FloorPlan List
     * @return Number of FloorPlans in the List
     */
    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        ImageView picBytes;

        /**
         * Sets xml properties for each item in the FloorPlan list to be displayed in the adapter
         * @param itemView View to get information from
         */
        ViewHolder(View itemView) {
            super(itemView);

            textName = itemView.findViewById(R.id.floor_plan_single_name);
            picBytes = itemView.findViewById(R.id.floor_plan_single_image);
        }
    }
}
