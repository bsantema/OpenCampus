package com.ss4.opencampus.dataViews.uspots;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ss4.opencampus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan Smith
 * Main class for the USpot List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class USpotListActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private RequestQueue queue;
    private List<USpot> uspotList;
    private RecyclerView.Adapter adapter;
    private static USpot uspotToBeShown;
    
    /**
     * Creates the ListView page. Loads all USpots from database
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_uspot_list);

        RecyclerView uList;
        uList = findViewById(R.id.uspot_list);

        uList.addOnItemTouchListener(new RecyclerItemClickListener(this, uList ,new RecyclerItemClickListener.OnItemClickListener() {
            /**
             * Left over code that is not used anymore. Switched to .selectedItem() for next sprint
             * @param view view
             * @param position position of USpot
             */
                    @Override public void onItemClick(View view, int position) {
                        view.getId();
                        USpot singleUSpot = (USpot)view.getTag();
                        System.out.println(singleUSpot.toString());
                        Intent intent = new Intent(view.getContext(), SingleUSpotActivity.class);
                        USpotListActivity.setUspotToBeShown(singleUSpot);
                        startActivity(intent);
                    }
    
            /**
             * Left over code that is not used anymore. Switched to .selectedItem() for next sprint
             * @param view view
             * @param position position of USpot
             */
                    @Override public void onLongItemClick(View view, int position) {
                        // do whatever
                    }
                }));

        uspotList = new ArrayList<>();
        adapter = new USpotAdapter(getApplicationContext(),uspotList);

        LinearLayoutManager linearLayoutManager;
        DividerItemDecoration dividerItemDecoration;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(uList.getContext(), linearLayoutManager.getOrientation());

        uList.setHasFixedSize(true);
        uList.setLayoutManager(linearLayoutManager);
        uList.addItemDecoration(dividerItemDecoration);
        uList.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/search/all";

        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {    // Reads in JSON data for the uspots from the server
                    /**
                     * Makes a GET Request to Backend to get all USpots in the database and stores the
                     * information into USpot objects
                     * @param response JSON format of information from Backend
                     */
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);  // Makes JSONObject
                                USpot uspotInfo = new USpot();                 // Makes USpot object from the JSONObject

                                uspotInfo.setUsID(jsonObject.getInt("usID"));
                                uspotInfo.setUsName(jsonObject.getString("usName"));
                                uspotInfo.setUsRating(jsonObject.getDouble("usRating"));
                                uspotInfo.setUsLatit(jsonObject.getDouble("usLatit"));
                                uspotInfo.setUsLongit(jsonObject.getDouble("usLongit"));
                                uspotInfo.setUspotCategory(jsonObject.getString("usCategory"));
                                uspotInfo.setPicBytes(Base64.decode(jsonObject.getString("picBytes"), Base64.DEFAULT));

                                uspotList.add(uspotInfo);
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
     * @return a single USpot to show in in SingleUSpotActivity
     */
    public static USpot getUspotToBeShown()
    {
        return uspotToBeShown;
    }
    
    /**
     * not used code. switching to .selectedItem() needs to be deleted
     * @param us new USpot to be shown in SingleUSpotActivity
     */
    public static void setUspotToBeShown(USpot us)
    {
        uspotToBeShown = us;
    }

}