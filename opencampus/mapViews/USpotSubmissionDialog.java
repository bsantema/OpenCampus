package com.ss4.opencampus.mapViews;

import android.Manifest;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.PreferenceUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Dialog appears when advancing from the CustomMarkerConvertDialog
 */
public class USpotSubmissionDialog extends DialogFragment{

    /**
     * Clickable TextViews for cancel, submit.
     */
    private TextView mActionCancel, mActionSubmit;

    /**
     *  EditText for editing the title for the USpot.
     */
    private EditText title;

    /**
     *  Dropdown menu that allows the user to select a category for the USpot.
     */
    private Spinner category;

    /**
     * A rating bar which allows the user to select an initial rating for the USpot.
     */
    private RatingBar rating;

    /**
     *  The button that allows the user to take a photo for the USpot.
     */
    private Button photoButton;

    /**
     * The ImageView which gets filled with the new photo when a user takes a photo.
     */
    private ImageView imgView, imgViewDefaultPhoto;

    /**
     * Image URI for the photo that the user takes.
     */
    private Uri photo_uri;

    /**
     * RequestQueue to be used for JSON requests.
     */
    private RequestQueue queue;

    /**
     * Tag to be used for JSON requests.
     */
    private static final String TAG = "tag";

    /**
     * Permission code for requesting external storage permission.
     */
    private static final int PERMISSION_CODE = 1000;

    /**
     * Permission code for requesting camera permission.
     */
    private static final int IMAGE_CAPTURE_CODE = 1001;

    boolean cameraOpened=false;

    /**
     * Building ID corresponding to the building that the USpot is in. -1 if not in a building.
     */
    private int buildingId;

    /**
     * Method is called when the fragment is created.
     * @param inflater
     *  Inflater which inflates the dialog_uspot_submission XML.
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
        View view = inflater.inflate(R.layout.dialog_uspot_submission, container, false);

        mActionCancel = view.findViewById(R.id.action_cancel);
        mActionSubmit = view.findViewById(R.id.action_submit);
        title = view.findViewById(R.id.name_text);
        category = view.findViewById(R.id.category_dropdown);
        rating = view.findViewById(R.id.ratingbar);
        photoButton = view.findViewById(R.id.photo_button);
        imgView = view.findViewById(R.id.image_view);
        imgViewDefaultPhoto = view.findViewById(R.id.image_view_default);
        buildingId = getBuildingId(((MapsActivity)getActivity()).getMarkerShowingInfoWindow());
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(), R.array.category_array, R.layout.support_simple_spinner_dropdown_item);
        category.setAdapter(adapter);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                {
                    if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED)
                    {
                        // permission not enabled, so we have to request it.
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else
                    {
                        openCamera();
                    }
                }
                else
                {
                    openCamera();
                }
            }
        });

        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        mActionSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double lat = ((MapsActivity)getActivity()).getMarkerShowingInfoWindow().getPosition().latitude;
                double lng = ((MapsActivity)getActivity()).getMarkerShowingInfoWindow().getPosition().longitude;

                Bitmap bitmap = null;

                if(cameraOpened)
                    bitmap = ((BitmapDrawable) imgView.getDrawable()).getBitmap();
                else
                {
                    try {
                        Drawable drawable = imgViewDefaultPhoto.getDrawable();
                        bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                        Canvas canvas = new Canvas(bitmap);
                        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                        drawable.draw(canvas);
                    } catch (OutOfMemoryError e) {
                        // Handle the error
                    }
                    //bitmap = ((BitmapDrawable) imgViewDefaultPhoto.getDrawable()).getBitmap();
                }


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageInByte = baos.toByteArray();
                String byteString = "";
                byteString = Base64.encodeToString(imageInByte, Base64.DEFAULT);

                JSONObject newUSpot = new JSONObject();
                try {
                    newUSpot.put("usName", title.getText().toString());
                    newUSpot.put("usRating", (double)rating.getRating());
                    newUSpot.put("usLatit", lat);
                    newUSpot.put("usLongit", lng);
                    newUSpot.put("usCategory", category.getSelectedItem().toString());
                    newUSpot.put("studentId", PreferenceUtils.getUserId(getActivity()));
                    newUSpot.put("picBytes", byteString);
                    newUSpot.put("studentId", PreferenceUtils.getUserId(getActivity()));

                    boolean floorplan = ((MapsActivity)getActivity()).getFloorplanVisible();
                    if(floorplan)
                    {
                        newUSpot.put("buildingId", ((MapsActivity)getActivity()).getCurrentBuildingId());
                        newUSpot.put("floor", ((MapsActivity)getActivity()).getCurrentFloorIndex());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                queue = Volley.newRequestQueue(getActivity());
                String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/add";
                JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, newUSpot, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            title.setText(response.get("response").toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<String, String>();
                        headers.put("Content-Type", "application/json; charset=utf-8");
                        return headers;
                    }
                };
                jsonRequest.setTag(TAG);
                queue.add(jsonRequest);
                getDialog().dismiss();
            }
        });
        return view;
    }

    /**
     *  Opens the camera, prompting the user to take a photo for the USpot.
     */
    private void openCamera()
    {
        cameraOpened=true;
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "USpot Photo");
        values.put(MediaStore.Images.Media.DESCRIPTION, "USpot photo taken from camera");
        photo_uri = getActivity().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photo_uri);
        startActivityForResult(cameraIntent, IMAGE_CAPTURE_CODE);
    }

    /**
     * Method is called when results are received after requesting permissions.
     * @param requestCode
     *  The request code passed in requestPermissions
     * @param permissions
     * The requested permissions. Never null.
     * @param grantResults
     * The grant results for the corresponding permissions which is either PERMISSION_GRANTED or PERMISSION_DENIED. Never null.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * Sets the ImageView to the user-taken photo if resultCode is correct.
     * @param requestCode
     *  requestCode given from the camera
     * @param resultCode
     *  result from the camera
     * @param data
     *  data from the camera
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == -1)
        {
            imgView.setImageURI(photo_uri);
        }
    }

    /**
     * Used to access a buildingId from the database, using a Marker's name.
     * @param building
     *  The marker for the building.
     * @return
     *  The id from the database corresponding to the building.
     */
    public int getBuildingId(Marker building)
    {
        if(!building.getTag().equals("Building"))
            return -1;

        String url = "http://coms-309-ss-4.misc.iastate.edu:8080/buildings/search/nameStartsWith?param1=" + building.getTitle();

        // Request a JSONObject response from the provided URL.
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                                JSONObject building = response.getJSONObject(0);
                            ((MapsActivity)getActivity()).setCurrentBuildingId(building.getInt("id"));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                System.out.println("failed");
                ((MapsActivity)getActivity()).setCurrentBuildingId(-1);
            }
        });

        //Set the tag on the request
        jsonRequest.setTag(TAG);

        // Add the request to the RequestQueue.
        queue.add(jsonRequest);

        return ((MapsActivity)getActivity()).getCurrentBuildingId();
    }
}