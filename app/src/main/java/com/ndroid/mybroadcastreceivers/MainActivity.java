package com.ndroid.mybroadcastreceivers;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends MyPermissionManagerActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        if(checkAndRequestPermissions())
            onAllPermissionGrated();
    }

    @Override
    protected void onAllPermissionGrated() {
        setContentView(R.layout.activity_main);
    }

    public void onClick_showNotification(View v)
    {
        new MyNotificationUtil(getApplicationContext(),
                "What is Android?",
                "Android is an Software stack which contains OS, Middleware and Key applications",
                "Android is a mobile operating system based on a modified version of the Linux kernel and other open source software, designed primarily for touchscreen mobile devices such as smart phones and tablets.").show();
    }

    public void onClick_startRegisterReceiver(View view) {
        startActivity(new Intent(this, MyRegisterReceiverActivity.class));
    }
}