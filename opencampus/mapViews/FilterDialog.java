package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ss4.opencampus.R;

/**
 * Dialog appears when selecting the filters button from the map screen.
 */
public class FilterDialog extends DialogFragment{

    /**
     * Clickable TextViews for cancel and ok.
     */
    private TextView mActionCancel, mActionOK;

    /**
     * Checkboxes for each category of marker.
     */
    private CheckBox checkBuildings, checkFeatures, checkUSpots, checkCustom;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the map_dialog_filters XML.
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
        View view = inflater.inflate(R.layout.map_dialog_filters, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOK = view.findViewById(R.id.action_ok);
        checkBuildings = view.findViewById(R.id.checkBuildings);
        checkFeatures = view.findViewById(R.id.checkFeatures);
        checkUSpots = view.findViewById(R.id.checkUSpots);
        checkCustom = view.findViewById(R.id.checkCustom);

        checkBuildings.setChecked(((MapsActivity)getActivity()).getBuildingFilter());
        checkFeatures.setChecked(((MapsActivity)getActivity()).getFeatureFilter());
        checkUSpots.setChecked(((MapsActivity)getActivity()).getUSpotFilter());
        checkCustom.setChecked(((MapsActivity)getActivity()).getCustomFilter());

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).setFilters(new boolean[] {
                        checkBuildings.isChecked(),
                        checkFeatures.isChecked(),
                        checkUSpots.isChecked(),
                        checkCustom.isChecked()});
                getDialog().dismiss();
            }
        });

        return view;
    }

}
