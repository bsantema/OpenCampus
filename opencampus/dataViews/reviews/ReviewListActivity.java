package com.ss4.opencampus.dataViews.reviews;

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
import com.ss4.opencampus.mainViews.DashboardActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ss4.opencampus.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Morgan Smith
 * Main class for the Review List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class ReviewListActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private RequestQueue queue;
    private List<Review> reviewList;
    private RecyclerView.Adapter adapter;

    int usID;

    /**
     * Creates the ListView page. Loads all Reviews from database
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_review_list);

        Intent intent =  getIntent();
        usID = intent.getExtras().getInt("USpotID", 0);

        RecyclerView rList;
        rList = findViewById(R.id.review_list);

        reviewList = new ArrayList<>();
        adapter = new ReviewAdapter(getApplicationContext(),reviewList);

        LinearLayoutManager linearLayoutManager;
        DividerItemDecoration dividerItemDecoration;

        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(rList.getContext(), linearLayoutManager.getOrientation());

        rList.setHasFixedSize(true);
        rList.setLayoutManager(linearLayoutManager);
        rList.addItemDecoration(dividerItemDecoration);
        rList.setAdapter(adapter);

        queue = Volley.newRequestQueue(this);
        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/" + usID + "/reviews/all";

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
                                Review reviewInfo = new Review();                 // Makes Review object from the JSONObject

                                reviewInfo.setReviewDetails(jsonObject.getString("text"));

                                reviewList.add(reviewInfo);
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

    public void createReview(View view)
    {
        Intent intent = new Intent(this, CreateReviewActivity.class);
        intent.putExtra("USpotID", usID);
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