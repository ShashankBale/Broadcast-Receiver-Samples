package com.ndroid.mybroadcastreceivers;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends MyPermissionManagerActivity {

    private final String[] requirePermissions = new String[]
            {
                    Manifest.permission.RECEIVE_SMS,
                    Manifest.permission.READ_SMS,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
            };

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        if (checkAndRequestPermissions(requirePermissions))
            onAllPermissionGranted();
    }

    @Override
    protected void onAllPermissionGranted() {
        setContentView(R.layout.activity_main);
    }

    public void onClick_showNotification(View v) {
        new MyNotificationUtil(getApplicationContext(),
                "What is Android?",
                "Android is an Software stack which contains OS, Middleware and Key applications",
                "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smart phones and tablets.").show();
    }

    public void onClick_startRegisterReceiver(View view) {
        startActivity(new Intent(this, MyRegisterReceiverActivity.class));
    }
}