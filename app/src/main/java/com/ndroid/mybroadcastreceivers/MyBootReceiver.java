package com.ndroid.mybroadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new MyNotificationUtil(context,
                "Boot Notification",
                "Just got Booted",
                "Just got Booted Successfully. " +
                        "Android apps can send or receive broadcast messages from the Android system " +
                        "and other Android apps, similar to the publish-subscribe design pattern. " +
                        "Currently we have receive RECEIVE_BOOT_COMPLETED broadcast to this application.").show();
    }
}