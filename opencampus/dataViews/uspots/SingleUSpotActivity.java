package com.ss4.opencampus.dataViews.uspots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.ss4.opencampus.dataViews.reviews.CreateReviewActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.dataViews.reviews.ReviewListActivity;

import com.ss4.opencampus.R;

/**
 * @author Morgan Smith
 * Main class for the USpot List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class SingleUSpotActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private USpot uspotItem;

    private TextView usName;
    private TextView usRating;
    private TextView usLatit;
    private TextView usLongit;
    private TextView usCategories;
    private ImageView usPicBytes;
    
    /**
     * Grabs all of the Information of a Single USpot that was selected and displays it
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_single_uspot);

        uspotItem = new USpot();

        usName = findViewById(R.id.uspot_single_name);
        usRating = findViewById(R.id.uspot_single_rating);
        usLatit = findViewById(R.id.uspot_single_latitude);
        usLongit = findViewById(R.id.uspot_single_longitude);
        usCategories = findViewById(R.id.uspot_single_category);
        usPicBytes = findViewById(R.id.uspot_single_image);

        uspotItem = USpotListActivity.getUspotToBeShown();
        usName.setText(uspotItem.getUsName());
        usRating.setText(uspotItem.getRatingString());
        usLatit.setText(uspotItem.getLatString());
        usLongit.setText(uspotItem.getLongString());
        usCategories.setText(uspotItem.getUsCategory());
        usPicBytes.setImageBitmap(uspotItem.setBitmap());
    }
    
    /**
     * Switches app to Dashboard screen
     * @param view given view
     */
    public void viewDashboard(View view)
    {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
    
    /**
     * Switches app to the List of ALL USpots
     * @param view given view
     */
    public void viewUSpotListActivity(View view)
    {
        Intent intent = new Intent(this, USpotListActivity.class);
        startActivity(intent);
    }

    /**
     * Switches app to the list of all reviews for a given USpot
     * @param view given view
     */
    public void viewReviewListActivity(View view)
    {
        Intent intent = new Intent(this, ReviewListActivity.class);
        uspotItem = USpotListActivity.getUspotToBeShown();
        intent.putExtra("USpotID", uspotItem.getUsID());
        startActivity(intent);
    }

    /**
     * Switches app to the list of all reviews for a given USpot
     * @param view given view
     */
    public void createReview(View view)
    {
        Intent intent = new Intent(this, CreateReviewActivity.class);
        intent.putExtra("USpotID", uspotItem.getUsID());
        startActivity(intent);
    }
}