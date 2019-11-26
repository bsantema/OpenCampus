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
 *  This dialog appears when the user chooses to delete a marker from the CustomMarkerDetailsDialog.
 */
public class CustomMarkerDeleteDialog extends DialogFragment{

    /**
     *  Clickable textviews for cancelling, or hitting ok.
     */
    private TextView mActionCancel, mActionOK;

    /**
     *  Checkboxes to delete from device or database
     */
    private CheckBox checkDevice, checkAccount;

    /**
     * true if this marker has been deleted. Turns true when you hit OK.
     */
    private boolean deleted;

    private CustomMarkerDetailsDialog cmdd;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the dialog_custom_marker_delete XML.
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
        View view = inflater.inflate(R.layout.dialog_custom_marker_delete, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionOK = view.findViewById(R.id.action_ok);
        checkDevice = view.findViewById(R.id.checkDeleteDevice);
        checkAccount = view.findViewById(R.id.checkDeleteAccount);

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        mActionOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).deleteCustomMarkers();
                deleted = true;
                getDialog().dismiss();
            }
        });

        return view;
    }

    public boolean getDeleted()
    {
        return deleted;
    }

    public void setParent(CustomMarkerDetailsDialog cmdd)
    {
        this.cmdd = cmdd;
    }


}
