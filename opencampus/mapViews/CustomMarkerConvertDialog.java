package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ss4.opencampus.R;

/**
 *  Dialog appears when clicking convert from the CustomMarkerDialog.
 */
public class CustomMarkerConvertDialog extends DialogFragment{

    /**
     * Clickable textview for cancelling or continuing.
     */
    private TextView mActionCancel, mActionContinue;

    /**
     * Radio buttons for selecting which map feature to convert marker to.
     */
    private RadioButton uspot, building, feature;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the dialog_custom_marker_convert XML.
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
        View view = inflater.inflate(R.layout.dialog_custom_marker_convert, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionContinue = view.findViewById(R.id.action_continue);
        uspot = view.findViewById(R.id.radio_uspot);
        building = view.findViewById(R.id.radio_building);
        feature = view.findViewById(R.id.radio_feature);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open corresponding submission dialog
                USpotSubmissionDialog uspotsubmit = new USpotSubmissionDialog();
                uspotsubmit.show(getFragmentManager(), "USpotSubmissionDialog");
                dismiss();
            }
        });

        return view;
    }

}