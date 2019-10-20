package com.ndroid.mybroadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MyBootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        new MyNotificationUtil(context,
                "Boot Notification",
                "Just got Rooted",
                "Just got Rooted Successfully").show();
    }
}
