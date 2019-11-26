package com.ss4.opencampus.mapViews;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.maps.model.Marker;
import com.ss4.opencampus.R;

/**
 * This class appears when the user selects "details" from the CustomMarkerDialog.
 */
public class CustomMarkerDetailsDialog extends DialogFragment {

    /**
     * Clickable TextViews for done, save, delete, rename, edit description. TextViews for title and description.
     */
    private TextView mActionDone, mActionSave, mActionDelete;

    private EditText markerTitle, markerDesc;
    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the dialog_custom_marker_details XML.
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
        View view = inflater.inflate(R.layout.dialog_custom_marker_details, container, false);

        mActionDone = view.findViewById(R.id.action_done);
        mActionSave = view.findViewById(R.id.action_save);
        mActionDelete = view.findViewById(R.id.action_delete);

        markerTitle = view.findViewById(R.id.marker_title);
        markerDesc = view.findViewById(R.id.marker_desc);

        Marker m = ((MapsActivity)getActivity()).getMarkerShowingInfoWindow();
        markerTitle.setText(m.getTitle());
        markerDesc.setText(((MapsActivity)getActivity()).getCustomMarkerDescription(m));

        mActionDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MapsActivity)getActivity()).getMarkerShowingInfoWindow().setTitle(markerTitle.getText().toString());
                ((MapsActivity)getActivity()).setCustomMarkerDescription(markerDesc.getText().toString());
                ((MapsActivity)getActivity()).setCustomMarkerTitle(markerTitle.getText().toString());
                getDialog().dismiss();
            }
        });

        mActionSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomMarkerSaveDialog cmSave = new CustomMarkerSaveDialog();
                cmSave.show(getFragmentManager(), "CustomMarkerSaveDialog");
            }
        });

        mActionDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomMarkerDeleteDialog cmDelete = new CustomMarkerDeleteDialog();
                cmDelete.show(getFragmentManager(), "CustomMarkerDeleteDialog");
                getDialog().dismiss();
            }
        });

        return view;
    }

    /**
     * Sets variable which indicates whether or not the dialog is visible to the user.
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser)
    {
        super.setUserVisibleHint(isVisibleToUser);
        updateTextViews();
    }

    /**
     * Updates TextViews for title and custom marker description so that they update properly when editing title or desc.
     */
    public void updateTextViews()
    {
        Marker m = ((MapsActivity)getActivity()).getMarkerShowingInfoWindow();
        markerTitle.setText(m.getTitle());
        markerDesc.setText(((MapsActivity)getActivity()).getCustomMarkerDescription(m));
    }

}