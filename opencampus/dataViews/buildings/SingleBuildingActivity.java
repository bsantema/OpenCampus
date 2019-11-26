package com.ss4.opencampus.dataViews.buildings;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.ss4.opencampus.dataViews.floorPlans.FloorPlanListActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;

import com.ss4.opencampus.R;

/**
 * @author Morgan Smith
 * Main class for the Building List
 * Reads in JSON data and outputs to recycler viewer
 **/

public class SingleBuildingActivity extends AppCompatActivity {

    public static final String TAG = "tag";
    private Building buildingItem;

    public TextView buildingName;
    public TextView buildingAbbrev;
    public TextView buildingAddress;
    public TextView buildingLongit;
    public TextView buildingLatit;

    /**
     * Grabs all of the Information of a Single Building that was selected and displays it
     * @param savedInstanceState state of app before this Activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) { // Start when page opens
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_activity_single_building);

        buildingItem = new Building();

        buildingName = findViewById(R.id.building_single_name);
        buildingAbbrev = findViewById(R.id.building_single_abbrev);
        buildingAddress = findViewById(R.id.building_single_address);
        buildingLongit = findViewById(R.id.building_single_longitude);
        buildingLatit = findViewById(R.id.building_single_latitude);

        buildingItem = BuildingListActivity.getBuildingToBeShown();
        buildingName.setText(buildingItem.getBuildingName());
        buildingAbbrev.setText(buildingItem.getAbbrev());
        buildingAddress.setText(buildingItem.getAddress());
        buildingLongit.setText(buildingItem.getLongString());
        buildingLatit.setText(buildingItem.getLatString());
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
     * Switches app to the List of ALL Buildings
     * @param view given view
     */
    public void viewBuildingListActivity(View view)
    {
        Intent intent = new Intent(this, BuildingListActivity.class);
        startActivity(intent);
    }

    /**
     * Switches app to the list of all floor plans for a given Building
     * @param view given view
     */
    public void viewFloorPlanListActivity(View view)
    {
        Intent intent = new Intent(this, FloorPlanListActivity.class);
        buildingItem = BuildingListActivity.getBuildingToBeShown();
        intent.putExtra("BuildingID", buildingItem.getBuildingID());
        startActivity(intent);
    }
}