package com.ss4.opencampus.mainViews.reviewMessage;

import android.content.Context;
import android.content.Intent;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.dataViews.reviews.Review;
import com.ss4.opencampus.dataViews.uspots.SingleUSpotActivity;
import com.ss4.opencampus.dataViews.uspots.USpot;
import com.ss4.opencampus.dataViews.uspots.USpotListActivity;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.LoginActivity;
import com.ss4.opencampus.mainViews.PreferenceUtils;
import com.ss4.opencampus.mapViews.MapsActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

/**
 * @author Axel Zumwalt
 *
 * Web socket for recieveing notifications for USpot comments.
 */
public class WebSocket {
    public static final String TAG = "tag";

    private Button btnSend;
    private Button btnDash;
    private TextView txtChat;
    private EditText editMsg;

    private static WebSocketClient cc;

    /**
     * Opens the websocket, and sets up the functions that will be executed when a message is received.
     *
     * @param userId
     *  User ID of the student logged into the app. Adds the student to a map in the backend.
     * @param context
     *  App context
     */
    public static void openWebSocket(int userId, final Context context) {

        Draft[] draft = {new Draft_6455()};
        String url = "ws://coms-309-ss-4.misc.iastate.edu:8080/websocket/" + Integer.toString(userId);

        try {
            cc = new WebSocketClient(new URI(url), (Draft) draft[0]) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("THING HAPPEN", "opened");
                }

                @Override
                public void onMessage(String message) {
                    Log.d("TO NOTIFY USER :", message);
                    int USpotId = Integer.parseInt(message);

                    RequestQueue queue = Volley.newRequestQueue(context);
                    String url = "http://coms-309-ss-4.misc.iastate.edu:8080/uspots/search/id/" + Integer.toString(USpotId);

                    JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {    // Reads in JSON data for the uspots from the server
                                /**
                                 * Makes a GET Request to Backend to get the USpot with the given ID in the database.
                                 * @param response JSON format of information from Backend
                                 */
                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        int usID = response.getInt("usID");
                                        String usName = response.getString("usName");

                                        ReviewMessage reviewMessage = new ReviewMessage(usID, usName, false);

                                        ArrayList<ReviewMessage> messageList = (ArrayList<ReviewMessage>) PreferenceUtils.getReviewMessageList(context);
                                        if (messageList == null) {
                                            messageList = new ArrayList<ReviewMessage>();
                                        }
                                        messageList.add(reviewMessage);

                                        PreferenceUtils.addReviewMessageList(messageList, context);

                                        Intent intent = new Intent(context, DashboardActivity.class);
                                        context.startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        /**
                         * Prints an the error if something goes wrong
                         * @param error Type of error that occurred
                         */
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
                    //Set the tag on the request
                    jsonRequest.setTag(TAG);
                    // Add the request to the RequestQueue.
                    queue.add(jsonRequest);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("THING HAPPEN", "closed" + reason);
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        cc.connect();
    }

    public static void closeWebSocket() {
        cc.close();
    }
}
