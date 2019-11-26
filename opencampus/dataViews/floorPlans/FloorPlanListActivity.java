package com.ss4.opencampus.dataViews.floorPlans;

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
 * Main class for the FloorPlan List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class FloorPlanListActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private RequestQueue queue;
    private List<FloorPlan> floorPlanList;
    private RecyclerView.Adapter adapter;

    String buildingID;

    /**
     * Creates the ListView page. Loads all FloorPlans from database
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_floor_plan_list);

        Intent intent =  getIntent();
        buildingID = intent.getExtras().getString("BuildingID", "");

        RecyclerView fList;
        fList = findViewById(R.id.floor_plan_list);

        floorPlanList = new ArrayList<>();
        adapter = new FloorPlanAdapter(getApplicationContext(),floorPlanList);

        LinearLayoutManager linearLayoutManager;
        DividerItemDecoration dividerItemDecoration;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(fList.getContext(), linearLayoutManager.getOrientation());

        fList.setHasFixedSize(true);
        fList.setLayoutManager(linearLayoutManager);
        fList.addItemDecoration(dividerItemDecoration);
        fList.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/" + buildingID + "/floorPlans/all";

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
                                FloorPlan floorPlanInfo = new FloorPlan();                 // Makes FloorPlan object from the JSONObject

                                floorPlanInfo.setFloorPlanID(jsonObject.getInt("fpId"));
                                floorPlanInfo.setFloorPlanName(jsonObject.getString("name"));
                                floorPlanInfo.setFloorPlanLevel(jsonObject.getString("level"));
                                floorPlanInfo.setFloorPlanImagePath(jsonObject.getString("fpImagePath"));
                                floorPlanInfo.setFloorPlanPicBytes(Base64.decode(jsonObject.getString("fpBytes"), Base64.DEFAULT));

                                floorPlanList.add(floorPlanInfo);
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
}