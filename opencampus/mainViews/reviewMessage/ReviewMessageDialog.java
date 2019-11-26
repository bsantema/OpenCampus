package com.ss4.opencampus.mainViews.reviewMessage;

import android.app.DialogFragment;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ss4.opencampus.R;
import com.ss4.opencampus.mapViews.MapsActivity;
import com.ss4.opencampus.mapViews.USpotSubmissionDialog;

/**
 * @author Axel Zumwalt
 *
 * Dialog, to choose to delete a message or go to USpot page. Not currently used in app
 */
public class ReviewMessageDialog extends DialogFragment{

    /**
     * Clickable TextViews for cancel and ok.
     */
    private TextView mActionCancel, mActionContinue;

    /**
     * Radio buttons to select option
     */
    private RadioButton deleteMsg, openUspot;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the dialog XML.
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
        View view = inflater.inflate(R.layout.main_dialog_review_message, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionContinue = view.findViewById(R.id.action_continue);
        deleteMsg = view.findViewById(R.id.radio_delete);
        openUspot = view.findViewById(R.id.radio_open);


        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
                // Open corresponding submission dialog
                USpotSubmissionDialog uspotsubmit = new USpotSubmissionDialog();
                uspotsubmit.show(getFragmentManager(), "USpotSubmissionDialog");
                dismiss();
/*
                ((MapsActivity)getActivity()).setFilters(new boolean[] {
                        checkBuildings.isChecked(),
                        checkFeatures.isChecked(),
                        checkUSpots.isChecked(),
                        checkCustom.isChecked()});
                getDialog().dismiss();

 */
            }
        });

        return view;
    }

}
