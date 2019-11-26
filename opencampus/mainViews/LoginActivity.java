package com.ss4.opencampus.mainViews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.reviewMessage.WebSocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Axel Zumwalt
 *
 * Class that provides functionality for the LoginActvity
 * Validates inputed email and password and login user into the app.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "tag";

    private RequestQueue queue;

    private EditText email;

    private EditText password;

    private TextView signInError;

    /**
     * On create method for the login activity. Inits text instance variables.
     * If a user is already logged in, immediately opens the Dashboard.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_login);

        email = (EditText)findViewById(R.id.editText_Email);
        password = (EditText)findViewById(R.id.editText_Password);
        signInError = (TextView)findViewById(R.id.textView_signin_error);
        signInError.setVisibility(View.INVISIBLE);

        /* Checks Shared Preferences to see if a user is already logged in */
        if (PreferenceUtils.getUserId(this) != -1 ){
            int studentId = PreferenceUtils.getUserId(this);
            WebSocket.openWebSocket(studentId, this);
            Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Opens create account activity on button click.
     * @param view
     */
    public void viewCreateAccountActivity(View view)
    {
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }

    /**
     * Validates the information put into the editText fields and attempts to login in user on button click.
     * @param view
     */
    public void attemptSignIn(View view) {
        signInError.setVisibility(View.INVISIBLE);

        queue = Volley.newRequestQueue(this);
        String url = String.format("http://coms-309-ss-4.misc.iastate.edu:8080/students/search/email?param1=%1$s", email.getText().toString());

        /* Search for a user with the given email */
        JsonArrayRequest jsonRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        /* If a student with email doesn't exist display error */
                        if (response.isNull(0)) {
                            signInError.setVisibility(View.VISIBLE);
                        }
                        /* Otherwise check if the password matches the encypted password on the database */
                        else {
                            try {
                                JSONObject student = response.getJSONObject(0);
                                validatePassword(student.getString("password"), student.getInt("id"));
                            }
                            catch (JSONException e) {
                                e.printStackTrace();
                            }
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

    /**
     * Compares with the password in the editText fields with the encrypted password on the database.
     * If passwords match log the user into the app.
     *
     * @param password
     *  Password that the user entered into the field. Compares this with the database entry.
     * @param studentId
     *  Id of the the student that is trying to login. Saved to SharedPreferences to login user.
     */
    private void validatePassword(String password, int studentId) {
        try {
            if (Crypto.decodeAndDecrypt(password).equals(this.password.getText().toString())) {
                PreferenceUtils.saveUserId(studentId, this);
                WebSocket.openWebSocket(studentId, this);
                Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
            else {
                signInError.setVisibility(View.VISIBLE);
            }
        }
        catch (Exception e) {

        }
    }

    public static void openDashboard() {

    }

    @Override
    protected void onStop () {
        super.onStop();
        if (queue != null) {
            queue.cancelAll(TAG);
        }
    }
}
