package com.ndroid.mybroadcastreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyRegisterReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case Intent.ACTION_POWER_CONNECTED:
                //Toast.makeText(context, "Connected", Toast.LENGTH_LONG).show();
                new MyNotificationUtil(context, "Charging Alert", "Connected", "Connected").show();
                break;
            case Intent.ACTION_POWER_DISCONNECTED:
                //Toast.makeText(context, "Disconnect", Toast.LENGTH_LONG).show();
                new MyNotificationUtil(context, "Charging Alert", "Disconnected", "Disconnected").show();
                break;
            default:
                //Toast.makeText(context, "Default Broadcast", Toast.LENGTH_LONG).show();
                new MyNotificationUtil(context, "Broadcast Alert", "Received Default alert", "Received Default alert").show();
                break;
        }
    }
}