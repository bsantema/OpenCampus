package com.ss4.opencampus.webSocket;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ss4.opencampus.R;
import com.ss4.opencampus.mainViews.DashboardActivity;
import com.ss4.opencampus.mainViews.PreferenceUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class SocketTestActivity extends AppCompatActivity {

    private Button btnSend;
    private Button btnDash;
    private TextView txtChat;
    private EditText editMsg;

    private WebSocketClient cc;

    /**
     * OnCreate method for the DashboardActivity.
     * Initilizes button instance variables.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.socket_activity_test);

        /* Init Objects */
        btnDash = (Button)findViewById(R.id.button_Dash);
        btnSend = (Button)findViewById(R.id.button_SocketSend);
        txtChat = (TextView)findViewById(R.id.textView_SocketChat);
        editMsg = (EditText)findViewById(R.id.editText_SocketMsg);

        Draft[] draft = {new Draft_6455()};
        String url = "ws://coms-309-ss-4.misc.iastate.edu:8080/websocket/"+ Integer.toString(PreferenceUtils.getUserId(this));

        try {
            cc = new WebSocketClient(new URI(url), (Draft) draft[0]) {
                @Override
                public void onOpen(ServerHandshake handshakedata) {
                    Log.d("THING HAPPEN","opened");
                }

                @Override
                public void onMessage(String message) {
                    String str = txtChat.getText().toString();
                    txtChat.setText(str + '\n' + message);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("THING HAPPEN","closed" + reason);
                }

                @Override
                public void onError(Exception ex) {
                    ex.printStackTrace();
                }
            };
        }
        catch (URISyntaxException e) {
            e. printStackTrace();
        }
        cc.connect();

        /* Init Listeners */
        btnDash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cc.close();
                Intent intent = new Intent(SocketTestActivity.this, DashboardActivity.class);
                startActivity(intent);
            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cc.send(editMsg.getText().toString());
            }
        });
    }
}
