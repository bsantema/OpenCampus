package com.ss4.opencampus.dataViews.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;
import com.ss4.opencampus.dataViews.buildings.Building;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Morgan Smith
 *
 * Class that provides functionality for the Create Review.
 *
 */
public class CreateReviewActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    private EditText reviewDetails;

    private TextView emptyError;

    private TextView success;

    private Context context;

    private RequestQueue queue;

    int usID;

    /**
     * On create method for the CreateReviewActivity. Initilizes instance variables
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent =  getIntent();
        usID = intent.getExtras().getInt("USpotID", 0);

        setContentView(R.layout.data_activity_review_create);

        context = this;

        reviewDetails = (EditText)findViewById(R.id.editText_reviewDetails);

        emptyError = (TextView)findViewById(R.id.textView_empty_error);
        emptyError.setVisibility(View.INVISIBLE);

        success = (TextView)findViewById(R.id.txt_view_success);
        success.setVisibility(View.INVISIBLE);
    }

    /**
     * Routine ran when the create review button is pressed. If all fields are valid, add a new review to the database
     *
     * @param view
     */
    public void createReview(View view) {

        if (validateFields()) {
            JSONObject newReview = new JSONObject();
            try {
                newReview.put("text", reviewDetails.getText());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            queue = Volley.newRequestQueue(this);
            String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/" + usID + "/reviews";

            /* Request a JSON response from the provided URL. If response is true the review was added to the database */
            JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, url, newReview, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        if (response.get("response").equals(true)) {
                            success.setVisibility(View.VISIBLE);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            /* Set the tag on the request */
            jsonRequest.setTag(TAG);

            /* Add the request to the RequestQueue. */
            queue.add(jsonRequest);
        }
    }

    /**
     * Checks if the data entered into the datafields is valid.
     * Does checks for data lengths, email validity, and duplicate username or email.
     *
     * @return True if all fields are valid, False otherwise.
     */
    private boolean validateFields() {
        emptyError.setVisibility(View.INVISIBLE);

        /* Show emptyError message if the editText box for reviewDetails is empty */
        if (reviewDetails.getText().toString().equals("")) {
            emptyError.setVisibility(View.VISIBLE);
            return false;
        }
        else {
            return true;
        }
    }

    public void viewReviewListActivity(View view)
    {
        Intent intent = new Intent(this, ReviewListActivity.class);
        intent.putExtra("USpotID", usID);
        startActivity(intent);
    }

    public void cancel(View view)
    {
        Intent intent = new Intent(this, SingleUSpotActivity.class);
        intent.putExtra("USpotID", usID);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}


