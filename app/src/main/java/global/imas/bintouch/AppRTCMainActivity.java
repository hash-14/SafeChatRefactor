/*
 *  Copyright 2014 The WebRTC Project Authors. All rights reserved.
 *
 *  Use of this source code is governed by a BSD-style license
 *  that can be found in the LICENSE file in the root of the source
 *  tree. An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */

package global.imas.bintouch;


import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;



/**
 * Handles the initial setup where the user selects which room to join.
 */
public class AppRTCMainActivity extends AppCompatActivity {
    private static final String LOG_TAG = "AppRTCMainActivity";
    private static final int CONNECTION_REQUEST = 1;
    private static final int RC_CALL = 111;
    Button connectButton;
    EditText E;

     String user_id ;
     String user_image ;
     String user_name ;
    String roomfire ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        setContentView(R.layout.activity_main);

          user_id = getIntent().getStringExtra("user_id");
          user_image = getIntent().getStringExtra("user_image");
          user_name =getIntent().getStringExtra("user_name");

                roomfire =getIntent().getStringExtra("roomfire");



        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        connectButton=(Button)findViewById(R.id.babab) ;
        connectButton.setOnClickListener(v -> connect());

        E=(EditText)findViewById(R.id.eaeae) ;
        E.requestFocus();
        connectButton.performClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @AfterPermissionGranted(RC_CALL)
    private void connect() {
        String[] perms = { android.Manifest.permission.CAMERA,  Manifest.permission.RECORD_AUDIO};
        if (EasyPermissions.hasPermissions(this, perms)) {
            connectToRoom(roomfire);
        } else {
            EasyPermissions.requestPermissions(this, "Need some permissions", RC_CALL, perms);
        }
    }

    private void connectToRoom(String roomId) {


        Intent intent = new Intent(this, CallActivity.class);

        intent.putExtra("Room", roomId);
        intent.putExtra("user_id", user_id);
        intent.putExtra("user_image", user_image);
        intent.putExtra("user_name", user_name);

        startActivityForResult(intent, CONNECTION_REQUEST);
    }
}
