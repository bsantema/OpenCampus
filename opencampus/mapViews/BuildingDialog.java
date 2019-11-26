package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ss4.opencampus.R;

/**
 *  This dialog appears when clicking a building marker. Has a title and description, with a button for showing floorplan.
 */
public class BuildingDialog extends DialogFragment {

    /**
     *  When clicked, exits the dialog.
     */
    private TextView mActionCancel;

    /**
     * Heading which shows the title of the building.
     */
    private TextView heading;

    /**
     * Shows the description for the building.
     */
    private TextView desc;

    /**
     * When clicked, shows the floorplan for the building.
     */
    private Button viewFloorplan;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the building dialog XML.
     *
     * @param container
     *  ViewGroup passed to inflater.inflate
     *
     * @param savedInstanceState
     *  Bundle used for persistent storage.
     *
     * @return view returned by inflater.inflate
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_building, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mActionCancel = view.findViewById(R.id.action_cancel);
        viewFloorplan = view.findViewById(R.id.floorplan_button);
        heading = view.findViewById(R.id.heading);
        desc = view.findViewById(R.id.desc);
        MapsActivity mapsActivity = (MapsActivity)getActivity();
        heading.setText(mapsActivity.getMarkerShowingInfoWindow().getTitle());

        if(heading.getText().toString().length() > 15)
            heading.setTextSize(26);

        if(heading.getText().toString().length() > 30)
            heading.setTextSize(18);
        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        viewFloorplan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).showFloorplan();
                getDialog().dismiss();
            }
        });

        return view;
    }

}
