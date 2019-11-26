package com.ss4.opencampus.dataViews.buildings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan Smith
 * Main class for the Building List Activity
 * Reads in JSON data and outputs to recycler viewer
 **/

public class BuildingListActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private RequestQueue queue;
    private List<Building> buildingList;
    private RecyclerView.Adapter adapter;
    private static Building buildingToBeShown;
    
    /**
     * Creates the ListView page. Loads all Buildings from database
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_building_list);

        RecyclerView bList;
        bList = findViewById(R.id.building_list);

        bList.addOnItemTouchListener(new RecyclerItemClickListener(this, bList ,new RecyclerItemClickListener.OnItemClickListener() {
            /**
             * Left over code that is not used anymore. Switched to .selectedItem() for next sprint
             * @param view view
             * @param position position of Building
             */
                    @Override public void onItemClick(View view, int position) {
                        view.getId();
                        Building singleBuilding = (Building)view.getTag();
                        System.out.println(singleBuilding.toString());
                        Intent intent = new Intent(view.getContext(), SingleBuildingActivity.class);
                        BuildingListActivity.setBuildingToBeShown(singleBuilding);
                        startActivity(intent);
                    }

            /**
             * Left over code that is not used anymore. Switched to .selectedItem() for next sprint
             * @param view view
             * @param position position of Building
             */
            @Override public void onLongItemClick(View view, int position) {
                // do whatever
            }
        }));

        buildingList = new ArrayList<>();
        adapter = new BuildingAdapter(getApplicationContext(),buildingList);

        LinearLayoutManager linearLayoutManager;
        DividerItemDecoration dividerItemDecoration;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(bList.getContext(), linearLayoutManager.getOrientation());

        bList.setHasFixedSize(true);
        bList.setLayoutManager(linearLayoutManager);
        bList.addItemDecoration(dividerItemDecoration);
        bList.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/search/all";

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {    // Reads in JSON data for the buildings from the server
                    /**
                     * Makes a GET Request to Backend to get all Buildings in the database and stores the
                     * information into Building objects
                     * @param response JSON format of information from Backend
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);  // Makes JSONObject
                                Building buildingInfo = new Building();             // Makes Building object from the JSONObject

                                buildingInfo.setBuildingID(jsonObject.getString("id"));
                                buildingInfo.setBuildingName(jsonObject.getString("buildingName"));
                                buildingInfo.setAbbrev(jsonObject.getString("abbreviation"));
                                buildingInfo.setAddress(jsonObject.getString("address"));
                                buildingInfo.setLatitude(jsonObject.getDouble("latit"));
                                buildingInfo.setLongitude(jsonObject.getDouble("longit"));

                                buildingList.add(buildingInfo);
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            /**
             * Prints an the error if something goes wrong
             * @param error Type of error that occurred
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        //Set the tag on the request
        jsonRequest.setTag(TAG);
        // Add the request to the RequestQueue.
        queue.add(jsonRequest);
    }
    
    /**
     * Returns the app to the dashboard screen
     * @param view given view
     */
    public void viewDashboard(View view)
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
    
    /**
     * Stops displaying the ListView page
     */
    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }

    /**
     * not used code. switching to .selectedItem() needs to be deleted
     * @return a single Building to show in in SingleBuildingActivity
     */
    public static Building getBuildingToBeShown()
    {
        return buildingToBeShown;
    }

    /**
     * not used code. switching to .selectedItem() needs to be deleted
     * @param bld new Building to be shown in SingleBuildingActivity
     */
    public static void setBuildingToBeShown(Building bld)
    {
        buildingToBeShown = bld;
    }
}
