package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;

/**
 *  This dialog appears when clicking a USpot marker. Has a title and description, with a button for showing details.
 */
public class USpotDialog extends DialogFragment {

    /**
     *  When clicked, exits the dialog.
     */
    private TextView mActionCancel;

    /**
     * Heading which shows the title of the USpot.
     */
    private TextView heading;

    /**
     * Shows the description for the USpot.
     */
    private TextView desc;

    /**
     * When clicked, shows the details for the USpot. Opens SingleUspotActivity.
     */
    private Button viewDetails;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the USpot dialog XML.
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
        View view = inflater.inflate(R.layout.dialog_uspot, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        mActionCancel = view.findViewById(R.id.action_cancel);
        viewDetails = view.findViewById(R.id.details_button);
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

        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                USpot toShow;
                if(!((MapsActivity) getActivity()).getFloorplanVisible())
                    toShow = ((MapsActivity)getActivity()).getUSpot();
                else
                    toShow = ((MapsActivity)getActivity()).getTempUSpot();

                Intent intent = new Intent(v.getContext(), SingleUSpotActivity.class);
                USpotListActivity.setUspotToBeShown(toShow);
                startActivity(intent);
            }
        });

        return view;
    }

}